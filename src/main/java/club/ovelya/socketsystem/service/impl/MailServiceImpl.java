package club.ovelya.socketsystem.service.impl;

import club.ovelya.socketsystem.service.MailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.validation.constraints.Email;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Email
@Service
public class MailServiceImpl implements MailService {

    private final String from = "ovelya@qq.com";
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private TemplateEngine templateEngine;
    @Value("${base-url}")
    private String baseUrl;
    @Value("${server.port}")
    private String port;

    @Async
    @Override
    public void sendSimpleMail(String to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);
        mailSender.send(message);
    }

    @Async
    @Override
    public void sendHtmlMail(String to, String subject, String content) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("邮件发送失败,失败信息:" + e.getMessage());
        }
    }

    @Async
    @Override
    public void sendVerifyMail(String to, String encodeUsername) {
        Context context = new Context();
        String urlStr = baseUrl + ":" + port + "/user/verify/" + encodeUsername;
        String encodedUrl = URLEncoder.encode(urlStr, StandardCharsets.UTF_8);
        context.setVariable("url", encodedUrl);
        String emailContent = templateEngine.process("emailTemplate", context);
        sendHtmlMail(to, "【ovelya】账号验证邮件", emailContent);
    }
}
