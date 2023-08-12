package com.cabin.common.schedule.email;

import cn.hutool.core.date.DateUtil;
import com.cabin.common.util.ApplicationContextUtil;
import com.sun.mail.imap.IMAPStore;
import jakarta.mail.*;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 伍六七
 * @date 2023/8/5 18:22
 */
@Component
@DependsOn("ApplicationContextUtil")
public class EmailTask implements Runnable {
    private final EmailConfig emailConfig = (EmailConfig) ApplicationContextUtil.getBean("emailConfig");


    @Override
    public void run() {
        readByEmail();
    }

    /**
     * 需要一个专门收发邮件的邮箱,
     * SMTP仅用于发送消息，而IMAP不能用于发送消息，可用于管理和检索消息。
     * 定时任务--
     */
//    @Scheduled(cron = "0/55 * *  * * ? ")   //每5秒执行一次
    public void readByEmail() {
        String protocol = "imap";
        Properties props = new Properties();
        props.put("mail.imap.ssl.enable", true);
        props.put("mail.imap.host", emailConfig.getHost());
        props.put("mail.imap.port", emailConfig.getPort());
        Session session = Session.getInstance(props);
        IMAPStore store = null;
        Folder folder = null;

        try {
            store = (IMAPStore) session.getStore(protocol);
            store.connect(emailConfig.getUsername(), emailConfig.getPassword());
            folder = store.getFolder("INBOX");//收件箱
            folder.open(Folder.READ_WRITE);
            Integer size = folder.getMessageCount();
            System.out.println("一共" + size);
            Message[] messages = folder.getMessages();
            if (messages.length == 0) {
                System.out.println("没有新邮件");
            } else {
                for (int i = 0; i < messages.length; i++) {
                    Message msg = messages[i];
//                    if(msg.isSet(Flags.Flag.SEEN)){//判断是否已读
//                        break;
//                    }
                    System.out.println("第" + i + "封");
                    StringBuffer content = new StringBuffer(30);
                    getMailTextContent(msg, content);
                    String taskId = null;
                    //先解析任务id，如果不存在，直接忽略该邮件
                    Matcher taskIdMatcher = Pattern.compile("【任务ID】: (.*?)<").matcher(content);
                    if (taskIdMatcher.find()) {
                        taskId = taskIdMatcher.group().substring(8, taskIdMatcher.group().length() - 1);
                        //TODO 根据解析的id去进行业务
                        System.out.println("任务ID" + taskId);
                    }
                    Matcher createTimeMatcher = Pattern.compile("【任务时间】: (.*?)<").matcher(content);
                    String createTime = null;

                    //解析并封装
                    if (createTimeMatcher.find()) {
                        createTime = createTimeMatcher.group().substring(8, createTimeMatcher.group().length() - 1);
//                        Date mailReceiveTime = getDateByMailContent(createTime);
                        //TODO 根据解析的id去进行业务
//                        System.out.println("任务时间1" + mailReceiveTime);
                        System.out.println("任务时间Str类型:" + createTime);
                    }
                }
            }
//            folder.setFlags(messages, new Flags(Flags.Flag.DELETED), true);
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (folder != null) {
                    folder.close(false);
                }
                if (store != null) {
                    store.close();
                }
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
        System.out.println("一次定时任务完毕！");
    }

    /**
     * 解析邮件的文本
     *
     * @param part
     * @param content
     * @throws MessagingException
     * @throws IOException
     */
    public static void getMailTextContent(Part part, StringBuffer content) throws MessagingException, IOException {
        //如果是文本类型的附件，通过getContent方法可以取到文本内容，但这不是我们需要的结果，所以在这里要做判断
        boolean isContainTextAttach = part.getContentType().indexOf("name") > 0;
        if (part.isMimeType("text/*") && !isContainTextAttach) {
            content.append(part.getContent().toString());
        } else if (part.isMimeType("message/rfc822")) {
            getMailTextContent((Part) part.getContent(), content);
        } else if (part.isMimeType("multipart/*")) {
            Multipart multipart = (Multipart) part.getContent();
            int partCount = multipart.getCount();
            for (int i = 0; i < partCount; i++) {
                BodyPart bodyPart = multipart.getBodyPart(i);
                getMailTextContent(bodyPart, content);
            }
        }
    }

    /**
     * 解析指定时间格式
     *
     * @param content
     * @throws ParseException
     */
    public static Date getDateByMailContent(String content) throws ParseException {
        SimpleDateFormat ft = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
        Matcher dateMatcher1 = Pattern.compile("(.*?) ").matcher(content);
        Matcher dateMatcher2 = Pattern.compile("午(.*?)秒").matcher(content);
        Matcher dateMatcher3 = Pattern.compile("日(.*?)午").matcher(content);

        String date1 = null;
        String date2 = null;
        if (dateMatcher1.find() && dateMatcher2.find()) {
            date1 = dateMatcher1.group();
            date2 = dateMatcher2.group().substring(1);
        }
        Date parse = ft.parse(date1 + date2);
        Date mailReceiveTime = null;
        if (dateMatcher3.find() && dateMatcher3.group().contains("下午")) {
            mailReceiveTime = DateUtil.offsetHour(parse, 12);
        }
        return mailReceiveTime;
    }

}
