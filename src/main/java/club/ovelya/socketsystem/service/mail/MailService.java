package club.ovelya.socketsystem.service.mail;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class MailService {

  private final String from = "ovelya@qq.com";
  @Autowired
  private JavaMailSender mailSender;
  @Autowired
  private TemplateEngine templateEngine;
  @Value("${base-url}")
  private String baseUrl;
  @Value("${server.port}")
  private String port;

  /**
   * 发送简单邮件
   *
   * @param to      接收方
   * @param subject 标题
   * @param content 正文
   */
  @Async
  public void sendSimpleMail(String to, String subject, String content) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setFrom(from);
    message.setTo(to);
    message.setSubject(subject);
    message.setText(content);
    mailSender.send(message);
  }

  /**
   * 发送html邮件
   *
   * @param to      接收方
   * @param subject 标题
   * @param content html正文
   */
  @Async
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

  /**
   * 发送简单邮件
   *
   * @param to             接收方
   * @param encodeUsername 加密后的验证码
   */
  @Async
  public void sendVerifyMail(String to, String encodeUsername) {
    Context context = new Context();
    String encodeUsernameReplace = encodeUsername.replace("/", "@");
    String urlStr = baseUrl + ":" + port + "/user/verify/" + encodeUsernameReplace;
    context.setVariable("url", urlStr);
    String emailContent = templateEngine.process("emailTemplate", context);
    sendHtmlMail(to, "【ovelya】账号验证邮件", emailContent);
  }
}
