package club.ovelya.socketsystem;

import club.ovelya.socketsystem.dao.UserInfoRepository;
import club.ovelya.socketsystem.service.UserInfoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DomainTest {
    @Autowired
    private UserInfoRepository userInfoRepository;
    @Autowired
    private UserInfoService userInfoService;

    @Test
    public void userInfoRepositoryTest() {
        userInfoService.registerUser("ovelya0", "jj", "123456");
    }

}
