package club.ovelya.socketsystem;

import club.ovelya.socketsystem.dao.UserInfoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DomainTest {
    @Autowired
    private UserInfoRepository userInfoRepository;

    @Test
    public void userInfoRepositoryTest() {
        System.out.println(userInfoRepository.findByName("test"));
    }
}
