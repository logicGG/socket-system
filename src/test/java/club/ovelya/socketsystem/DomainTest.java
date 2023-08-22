package club.ovelya.socketsystem;

import club.ovelya.socketsystem.dao.SysRoleRepository;
import club.ovelya.socketsystem.dao.UserInfoRepository;
import club.ovelya.socketsystem.service.MailService;
import club.ovelya.socketsystem.service.UserInfoService;
import com.corundumstudio.socketio.SocketIOServer;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;

@SpringBootTest
@Slf4j
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

  @Autowired
  private SocketIOServer socketIOServer;

  @Test
  public void userInfoRepositoryTest() {
    socketIOServer.start();
    socketIOServer.addConnectListener(socketIOClient -> {
      log.info("socket");
    });
  }

}
