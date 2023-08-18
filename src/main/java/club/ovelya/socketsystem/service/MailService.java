package club.ovelya.socketsystem.service;

public interface MailService {

  void sendSimpleMail(String to, String subject, String content);

  void sendHtmlMail(String to, String subject, String content);

  void sendVerifyMail(String to, String hash);
}
