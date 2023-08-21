package club.ovelya.socketsystem;

import club.ovelya.socketsystem.dao.SysRoleRepository;
import club.ovelya.socketsystem.dao.UserInfoRepository;
import club.ovelya.socketsystem.entity.SysRole;
import club.ovelya.socketsystem.entity.UserInfo;
import club.ovelya.socketsystem.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CustomCommandLineRunner implements CommandLineRunner {

  @Autowired
  private SysRoleRepository sysRoleRepository;
  @Autowired
  private UserInfoRepository userInfoRepository;
  @Autowired
  private UserInfoService userInfoService;

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
      userInfoService.registerUser(new UserInfo("ovelya", "章鱼", "ovelya@qq.com", "ovelya"));
      userInfoService.addRoleToUser("ovelya", "user");
      userInfoService.addRoleToUser("ovelya", "admin");
      userInfoService.addRoleToUser("ovelya", "superAdmin");
    }
  }
}
