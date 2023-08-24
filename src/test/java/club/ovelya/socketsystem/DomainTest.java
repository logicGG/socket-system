package club.ovelya.socketsystem;

import club.ovelya.socketsystem.service.MailService;
import club.ovelya.socketsystem.service.impl.MailServiceImpl;
import club.ovelya.socketsystem.utils.AESUtil;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;


public class DomainTest {


  @Test
  public void userInfoRepositoryTest() throws URISyntaxException {
    String s1 = URLDecoder.decode("%252FelUvA%252F6Avq4f2SLa0qScw%253D%253D",
        StandardCharsets.UTF_8);
    String s2 = URLDecoder.decode(s1, StandardCharsets.UTF_8);
    System.out.println(s2);
  }

  @Test
  public void test() {
    MailService mailService = new MailServiceImpl();
    mailService.sendSimpleMail("ovelya@qq.com", "test", AESUtil.encrypt("wshjgg", "ovelya@aes"));
  }

}
