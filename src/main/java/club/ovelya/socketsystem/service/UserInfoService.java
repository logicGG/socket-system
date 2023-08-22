package club.ovelya.socketsystem.service;

import club.ovelya.socketsystem.entity.UserInfo;
import org.apache.shiro.authc.UsernamePasswordToken;

public interface UserInfoService {


    /**
     * 用户注册
     */
    void registerUser(UserInfo userInfo);

    /**
     * 用户登录
     */
    void loginUser(UsernamePasswordToken usernamePasswordToken);

    //给用户添加角色
    void addRoleToUser(String username, String role);

    void sendVerifyMail();

    void verifyUser(String encodeUsername);

}
