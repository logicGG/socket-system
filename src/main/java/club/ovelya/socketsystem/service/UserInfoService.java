package club.ovelya.socketsystem.service;

import club.ovelya.socketsystem.entity.UserInfo;
import org.apache.shiro.authc.UsernamePasswordToken;

public interface UserInfoService {


  /**
   * 用户注册
   *
   * @param userInfo 用户对象
   */
  void registerUser(UserInfo userInfo);

  /**
   * 用户登录
   *
   * @param usernamePasswordToken 账号密码
   * @return websocket的授权码
   */
  String loginUser(UsernamePasswordToken usernamePasswordToken);

  /**
   * 添加角色给用户
   *
   * @param username 账号
   * @param role     角色
   */
  void addRoleToUser(String username, String role);

  /**
   * 发送验证邮件
   */
  void sendVerifyMail();

  /**
   * 给用户认证
   *
   * @param encodeUsername 加密后的账号
   */
  void verifyUser(String encodeUsername);

}
