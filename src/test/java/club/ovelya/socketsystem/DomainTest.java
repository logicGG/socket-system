package club.ovelya.socketsystem;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;


public class DomainTest {


    @Test
    public void userInfoRepositoryTest() {
      String urlStr = "//123";
      String encodedUrl = URLEncoder.encode(urlStr, StandardCharsets.UTF_8);
      System.out.println(encodedUrl);
    }

}
