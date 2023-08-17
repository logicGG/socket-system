package club.ovelya.socketsystem.service.impl;

import club.ovelya.socketsystem.dao.UserInfoRepository;
import club.ovelya.socketsystem.domain.UserInfo;
import club.ovelya.socketsystem.service.UserInfoService;
import jakarta.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Arrays;
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
  public void registerUser(UserInfo userInfo) {
    if (userInfoRepository.findByUsername(userInfo.getUsername()) != null) {
      throw new RuntimeException("用户名已存在！");
    }
    String hashed = BCrypt.hashpw(userInfo.getPassword(), BCrypt.gensalt());
    userInfo.setPassword(hashed);
    userInfoRepository.save(userInfo);
  }

  @Override
  public void loginUser(UsernamePasswordToken usernamePasswordToken) {
    if ("".equals(usernamePasswordToken.getUsername())) {
      throw new RuntimeException("用户名不能为空");
    }
    if ("".equals(Arrays.toString(usernamePasswordToken.getPassword()))) {
      throw new RuntimeException("密码不能为空");
    }
    Subject subject = SecurityUtils.getSubject();
    subject.login(usernamePasswordToken);
    UserInfo userInfo = userInfoRepository.findByUsername(usernamePasswordToken.getUsername());
    userInfo.setLastLoginTime(LocalDateTime.now());
    userInfo.setLastLoginIP(subject.getSession().getHost());
    userInfoRepository.save(userInfo);
  }
}
