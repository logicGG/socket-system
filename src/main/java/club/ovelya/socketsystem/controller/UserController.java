package club.ovelya.socketsystem.controller;

import club.ovelya.socketsystem.domain.UserInfo;
import club.ovelya.socketsystem.service.UserInfoService;
import club.ovelya.socketsystem.utils.HttpStatusUtils;
import club.ovelya.socketsystem.utils.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@Tag(name = "用户接口", description = "user api")
public class UserController {

  @Autowired
  private UserInfoService userInfoService;

  @Operation(summary = "用户登录")
  @PostMapping("/login")
  public R<?> login(@RequestBody UsernamePasswordToken usernamePasswordToken) {
    try {
      userInfoService.loginUser(usernamePasswordToken);
      return R.success();
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

  @GetMapping("/hello")
  public Object hello(@Email String email) {
    return R.custom("200", "", email);
  }


}
