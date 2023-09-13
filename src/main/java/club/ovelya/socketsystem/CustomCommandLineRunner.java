package club.ovelya.socketsystem;

import club.ovelya.socketsystem.dao.user.SysRoleRepository;
import club.ovelya.socketsystem.dao.user.UserInfoRepository;
import club.ovelya.socketsystem.entity.user.SysRole;
import club.ovelya.socketsystem.pojo.dto.user.RegisterDTO;
import club.ovelya.socketsystem.service.user.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CustomCommandLineRunner implements CommandLineRunner {

  @Autowired
  private SysRoleRepository sysRoleRepository;
  @Autowired
  private UserInfoRepository userInfoRepository;
  @Autowired
  private UserInfoService userInfoService;

  /**
   * 初始化数据库
   *
   * @param args 命令行参数
   */
  @Override
  public void run(String... args) {
    //初始化角色
    if (sysRoleRepository.findByRole("user") == null) {
      SysRole userRole = new SysRole("user", "普通用户");
      sysRoleRepository.save(userRole);
    }
    if (sysRoleRepository.findByRole("admin") == null) {
      SysRole adminRole = new SysRole("admin", "管理员");
      sysRoleRepository.save(adminRole);
    }
    if (sysRoleRepository.findByRole("superAdmin") == null) {
      SysRole superAdminRole = new SysRole("superAdmin", "超级管理员");
      sysRoleRepository.save(superAdminRole);
    }
    //超级管理员账号
    if (userInfoRepository.findByUsername("ovelya") == null) {
      userInfoService.registerUser(
          RegisterDTO.builder()
              .username("ovelya")
              .name("章鱼")
              .email("ovelya@qq.com")
              .phoneNumber("18676804083")
              .password("ovelya")
              .build());
      userInfoService.addRoleToUser("ovelya", "user");
      userInfoService.addRoleToUser("ovelya", "admin");
      userInfoService.addRoleToUser("ovelya", "superAdmin");
    }
  }
}
