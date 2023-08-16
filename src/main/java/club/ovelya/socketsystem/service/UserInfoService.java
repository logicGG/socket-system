package club.ovelya.socketsystem.service;

public interface UserInfoService {

  /**
   * 用户注册
   *
   * @param username 账号
   * @param name     用户名
   * @param password 明文密码
   */
  void registerUser(String username, String name, String password);

  /**
   * 用户登录
   *
   * @param username 账号
   * @param password 密码
   */
  void loginUser(String username, String password);

}
