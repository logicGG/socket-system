package club.ovelya.socketsystem;

import club.ovelya.socketsystem.dao.SysRoleRepository;
import club.ovelya.socketsystem.dao.UserInfoRepository;
import club.ovelya.socketsystem.service.MailService;
import club.ovelya.socketsystem.service.UserInfoService;
import java.util.Collection;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.MemorySessionDAO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
  @Autowired
  private MailService mailService;

  @Test
  public void userInfoRepositoryTest() {
    MemorySessionDAO sessionDAO = new MemorySessionDAO();
    Collection<Session> activeSessions = sessionDAO.getActiveSessions();
    System.out.println(activeSessions.toString());
  }

}
