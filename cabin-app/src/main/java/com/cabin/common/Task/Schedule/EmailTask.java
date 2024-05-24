package com.cabin.common.Task.Schedule;

import cn.hutool.core.date.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
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
public class EmailTask {
    @Autowired
    private EmailConfig emailConfig;

    /**
     * 定时任务--自动派单
     * 如果工单超时，该工单会保存在ma表，但工单表是没有数据的
     */
    public Boolean autoMaWorkOrderByEmail() {
        String protocol = "imap";
        boolean isSSL = true;
        String host = "imap.163.com";
        int port = 993;
        Properties props = new Properties();
        props.put("mail.imap.ssl.enable", isSSL);
        props.put("mail.imap.host", host);
        props.put("mail.imap.port", port);
        //这部分就是解决异常的关键所在，设置IAMP ID信息
        Session session = Session.getInstance(props);
        Store store = null;
        Folder folder = null;
        System.out.println(emailConfig.getUsername());
        try {
            store = session.getStore(protocol);
            store.connect(emailConfig.getUsername(), emailConfig.getPassword());
            folder = store.getFolder("INBOX");
            folder.open(Folder.READ_WRITE);
            int size = folder.getMessageCount();
            System.out.println("一共" + size);
            Message[] messages = folder.getMessages();
            if (messages.length == 0) {
                System.out.println("没有新邮件");
            } else {
                for (int i = 0; i < messages.length; i++) {
                    Message msg = messages[i];
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
                        Date mailReceiveTime = getDateByMailContent(createTime);
                        //TODO 根据解析的id去进行业务
                        System.out.println("任务时间1" + mailReceiveTime);
                        System.out.println("任务时间2" + mailReceiveTime);
                    }
                }
            }
            folder.setFlags(messages, new Flags(Flags.Flag.DELETED), true);
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (IOException | ParseException e) {
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
        return true;
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
