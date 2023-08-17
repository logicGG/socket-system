package club.ovelya.socketsystem;

import club.ovelya.socketsystem.dao.SysRoleRepository;
import club.ovelya.socketsystem.dao.UserInfoRepository;
import club.ovelya.socketsystem.service.UserInfoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@SpringBootTest
public class DomainTest {

  @Autowired
  private UserInfoRepository userInfoRepository;
  @Autowired
  private UserInfoService userInfoService;
  @Autowired
  private SysRoleRepository roleRepository;

  @Autowired
  private JavaMailSender mailSender;

  @Test
  public void userInfoRepositoryTest() {
    SimpleMailMessage mailMessage = new SimpleMailMessage();
    mailMessage.setFrom("ovelya@qq.com");
    mailMessage.setTo("ovelya@qq.com");
    mailMessage.setSubject("test");
    mailMessage.setText("test");
    mailSender.send(mailMessage);
  }

}
