package com.cabin.common.util.mail;

import com.cabin.common.util.MarkdownUtils;
import com.cabin.common.util.mail.Vo.MailVo;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileUrlResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

@Service
public class MailServiceImpl {


    @Autowired
    private JavaMailSender mailSender;
    // 注入常量用户名
    @Value("${spring.mail.username}")
    private String from;

    public void sendAlarmToMail(MailVo vo) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            String html = MarkdownUtils.markdownToHtml(vo.getMarkdown().getText());
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(vo.getTo().split(","));//，分割是当需要向多个邮件发送时
            helper.setSubject(vo.getSub());
            helper.setText(html, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送带附件的邮件
     * 将来堆栈爆了的时候可以把打印的hprof文件附带过去
     */
    public Boolean sendAttachmentsMail(List<MailVo> vos) {
        MimeMessage message = mailSender.createMimeMessage();
        vos.forEach(vo -> {
            try {
                MimeMessageHelper helper = new MimeMessageHelper(message, true);
                helper.setFrom(from);
                helper.setTo(vo.getTo().split(","));
                helper.setSubject(vo.getSub());
                helper.setText(vo.getText());

                FileUrlResource file = new FileUrlResource(new URL(vo.getFilePath()));
                String fileName = vo.getFilePath().substring(vo.getFilePath().lastIndexOf("/") + 1);
                helper.addAttachment(fileName, file);

                mailSender.send(message);
            } catch (MessagingException | MalformedURLException e) {
                e.printStackTrace();
            }
        });

        return true;
    }

    public Boolean sendSbTakeOrderMail(MailVo vo) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(vo.getTo().split(","));
            helper.setSubject(vo.getSub());
            String[] split = vo.getText().split(",");
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("<h2>" + vo.getSub() + "</h2>");
            for (int i = 0; i < split.length; i++) {
                stringBuilder.append(split[i] + "<br>");
            }
            helper.setText(stringBuilder.toString(), true);
            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return true;
    }

}
