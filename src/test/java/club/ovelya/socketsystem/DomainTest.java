package club.ovelya.socketsystem;

import club.ovelya.socketsystem.utils.AESUtil;
import org.junit.jupiter.api.Test;


public class DomainTest {


    @Test
    public void userInfoRepositoryTest() {
      System.out.println(AESUtil.encrypt("wshjgg", "ovelya@aes"));
    }

}
