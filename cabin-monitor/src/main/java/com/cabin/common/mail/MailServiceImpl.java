package com.cabin.common.mail;

import com.cabin.common.mail.Vo.MailVo;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl {


    @Autowired
    private JavaMailSender mailSender;
    // 注入常量用户名
    @Value("${spring.mail.username}")
    private String from;

    public void sendCodeToMail(MailVo vo) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(vo.getTo().split(","));//，分割是当需要向多个邮件发送时
            helper.setSubject(vo.getSub());
            String[] split = vo.getText().split(",");
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("<h2>").append(vo.getSub()).append("</h2>");
            for (int i = 0; i < split.length; i++) {
                stringBuilder.append(split[i]).append("<br>");
            }
            helper.setText(stringBuilder.toString(), true);
            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
