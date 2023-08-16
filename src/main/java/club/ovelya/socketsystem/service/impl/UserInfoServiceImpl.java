package club.ovelya.socketsystem.service.impl;

import club.ovelya.socketsystem.dao.UserInfoRepository;
import club.ovelya.socketsystem.domain.UserInfo;
import club.ovelya.socketsystem.service.UserInfoService;
import jakarta.annotation.Resource;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class UserInfoServiceImpl implements UserInfoService {

  @Resource
  private UserInfoRepository userInfoRepository;

  @Override
  public void registerUser(String username, String name, String password) {
    if (userInfoRepository.findByUsername(username) != null) {
      throw new RuntimeException("用户名已存在！");
    }
    //加密明文密码
    String hashed = BCrypt.hashpw(password, BCrypt.gensalt());
    userInfoRepository.save(new UserInfo(username, name, hashed));
  }

  @Override
  public void loginUser(String username, String password) {
    if ("".equals(username)) {
      throw new RuntimeException("用户名不能为空");
    }
    if ("".equals(password)) {
      throw new RuntimeException("密码不能为空");
    }
    Subject subject = SecurityUtils.getSubject();
    UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, password);
    subject.login(usernamePasswordToken);
  }


}
