package club.ovelya.socketsystem;

import club.ovelya.socketsystem.dao.SysRoleRepository;
import club.ovelya.socketsystem.dao.UserInfoRepository;
import club.ovelya.socketsystem.domain.SysRole;
import club.ovelya.socketsystem.domain.UserInfo;
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
  @Autowired
  private SysRoleRepository roleRepository;

  @Test
  public void userInfoRepositoryTest() {
    UserInfo userInfo = userInfoRepository.findByUsername("admin");
    for (SysRole role : userInfo.getRoleList()) {
      System.out.println(role.getRole());
    }
  }

}
