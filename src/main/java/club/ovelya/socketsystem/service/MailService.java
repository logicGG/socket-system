package club.ovelya.socketsystem.service;

public interface MailService {

  /**
   * 发送简单邮件
   *
   * @param to      接收方
   * @param subject 标题
   * @param content 正文
   */
  void sendSimpleMail(String to, String subject, String content);

  /**
   * 发送html邮件
   *
   * @param to      接收方
   * @param subject 标题
   * @param content html正文
   */
  void sendHtmlMail(String to, String subject, String content);

  /**
   * 发送简单邮件
   *
   * @param to   接收方
   * @param hash 加密后的验证码
   */
  void sendVerifyMail(String to, String hash);
}
