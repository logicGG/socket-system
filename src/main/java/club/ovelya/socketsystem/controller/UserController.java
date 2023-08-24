package club.ovelya.socketsystem.controller;

import club.ovelya.socketsystem.entity.UserInfo;
import club.ovelya.socketsystem.service.UserInfoService;
import club.ovelya.socketsystem.utils.HttpStatusUtils;
import club.ovelya.socketsystem.utils.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@Tag(name = "用户接口")
@RequestMapping("/user")
public class UserController {

  @Autowired
  private UserInfoService userInfoService;

  @Operation(summary = "用户登录")
  @PostMapping("/login")
  public R<?> login(@RequestBody UsernamePasswordToken usernamePasswordToken) {
    try {
      String token = userInfoService.loginUser(usernamePasswordToken);
      return R.custom(200, null, token);
    } catch (IncorrectCredentialsException e) {
      return R.custom(HttpStatusUtils.UNAUTHORIZED, "密码错误", null);
    } catch (Exception e) {
      return R.failMsg(e.getMessage());
    }
  }

  @Operation(summary = "退出登录")
  @GetMapping("/logout")
  public R<?> logout() {
    Subject subject = SecurityUtils.getSubject();
    if (subject.isAuthenticated()) {
      subject.logout();
    }
    return R.success();
  }

  @Operation(summary = "用户注册")
  @PostMapping("/register")
  public R<?> tregister(@Valid @RequestBody UserInfo userInfo) {
    try {
      userInfoService.registerUser(userInfo);
      return R.success();
    } catch (Exception e) {
      return R.failMsg(e.getMessage());
    }
  }

  @Operation(summary = "请求发送验证邮件")
  @GetMapping("/verify")
  public R<?> sendVerifyMail() {
    try {
      userInfoService.sendVerifyMail();
      return R.success();
    } catch (Exception e) {
      return R.failMsg(e.getMessage());
    }
  }

  @Operation(summary = "验证邮箱")
  @GetMapping("/verify/{encodeURL}")
  public R<?> verify(@PathVariable String encodeURL) {
    try {
      userInfoService.verifyUser(encodeURL);
      return R.success();
    } catch (Exception e) {
      return R.failMsg(e.getMessage());
    }
  }

  @Operation(summary = "验证邮箱")
  @GetMapping("/has/user")
  public boolean hasUser() {
    Subject subject = SecurityUtils.getSubject();
    return subject.hasRole("user");
  }
}
