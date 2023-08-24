package club.ovelya.socketsystem;

import club.ovelya.socketsystem.service.MailService;
import club.ovelya.socketsystem.service.impl.MailServiceImpl;
import club.ovelya.socketsystem.utils.AESUtil;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;


public class DomainTest {


  @Test
  public void userInfoRepositoryTest() throws URISyntaxException {
    String encodeUsername = AESUtil.encrypt("wshjgg", "ovelya@aes");
    String encoded = URLEncoder.encode(encodeUsername, StandardCharsets.UTF_8);
    System.out.println("/elUvA/6Avq4f2SLa0qScw==".equals(encodeUsername));
    System.out.println("%2FelUvA%2F6Avq4f2SLa0qScw%3D%3D".equals(encoded));
    System.out.println(
        URLDecoder.decode("%2FelUvA%2F6Avq4f2SLa0qScw%3D%3D", StandardCharsets.UTF_8));
  }

  @Test
  public void test() {
    MailService mailService = new MailServiceImpl();
    mailService.sendSimpleMail("ovelya@qq.com", "test", AESUtil.encrypt("wshjgg", "ovelya@aes"));
  }

}
