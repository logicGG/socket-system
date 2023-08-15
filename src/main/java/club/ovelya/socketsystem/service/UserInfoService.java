package club.ovelya.socketsystem.service;

import club.ovelya.socketsystem.domain.UserInfo;

public interface UserInfoService {

  /**
   * 根据账号查找
   *
   * @param username 账号
   * @return 数据库找到的对象
   */
  UserInfo findByUsername(String username);

  /**
   * 用户注册
   *
   * @param username 账号
   * @param name     用户名
   * @param password 明文密码
   */
  void registerUser(String username, String name, String password);
}
