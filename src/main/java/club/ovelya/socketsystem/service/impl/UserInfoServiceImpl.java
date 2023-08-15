package club.ovelya.socketsystem.service.impl;

import club.ovelya.socketsystem.dao.UserInfoRepository;
import club.ovelya.socketsystem.domain.UserInfo;
import club.ovelya.socketsystem.service.UserInfoService;
import jakarta.annotation.Resource;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class UserInfoServiceImpl implements UserInfoService {

  @Resource
  private UserInfoRepository userInfoRepository;

  @Override
  public UserInfo findByUsername(String username) {
    return userInfoRepository.findByUsername(username);
  }

  @Override
  public void registerUser(String username, String name, String password) {
    if (userInfoRepository.findByUsername(username) != null) {
      throw new RuntimeException("用户名已存在！");
    }
    //加密明文密码
    String hashed = BCrypt.hashpw(password, BCrypt.gensalt());
    userInfoRepository.save(new UserInfo(username, name, hashed));
  }
}
