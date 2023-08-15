package club.ovelya.socketsystem.controller;

import club.ovelya.socketsystem.service.UserInfoService;
import club.ovelya.socketsystem.utils.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "用户接口", description = "user api")
public class UserController {

  @Autowired
  private UserInfoService userInfoService;

  @Operation(summary = "用户登录", description = "User login api")
  @Parameters({@Parameter(name = "username", description = "账号", required = true),
      @Parameter(name = "password", description = "密码", required = true)})
  @PostMapping("/login")
  public R<?> login(
      String username, String password) {
    Subject subject = SecurityUtils.getSubject();
    UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, password);
    try {
      subject.login(usernamePasswordToken);
      return R.success();
    } catch (Exception e) {
      return R.failMsg(e.getMessage());
    }
  }

  @Operation(summary = "用户注册", description = "User register api")
  @Parameters({@Parameter(name = "username", description = "账号", required = true),
      @Parameter(name = "name", description = "用户名", required = true),
      @Parameter(name = "password", description = "密码", required = true)})
  @PostMapping("/register")
  public R<?> register(String username, String name, String password) {
    try {
      userInfoService.registerUser(username, name, password);
      return R.success();
    } catch (Exception e) {
      return R.failMsg(e.getMessage());
    }
  }

  @GetMapping("/hello")
  public String hello() {
    return "hello";
  }


}
