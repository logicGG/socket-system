package club.ovelya.socketsystem.controller.user;

import club.ovelya.socketsystem.pojo.dto.user.LoginDTO;
import club.ovelya.socketsystem.pojo.dto.user.RegisterDTO;
import club.ovelya.socketsystem.service.user.UserInfoService;
import club.ovelya.socketsystem.utils.HttpStatusUtil;
import club.ovelya.socketsystem.utils.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
  public R<?> login(@RequestBody LoginDTO loginDTO) {
    try {
      String token = userInfoService.loginUser(loginDTO);
      return R.custom(200, null, token);
    } catch (IncorrectCredentialsException e) {
      return R.custom(HttpStatusUtil.UNAUTHORIZED, "密码错误", null);
    }
  }

  @Operation(summary = "退出登录")
  @PutMapping("/logout")
  public R<?> logout() {
    Subject subject = SecurityUtils.getSubject();
    if (subject.isAuthenticated()) {
      subject.logout();
    }
    return R.success();
  }

  @Operation(summary = "用户注册")
  @PostMapping("/register")
  public R<?> tregister(@Valid @RequestBody RegisterDTO registerDTO) {
    userInfoService.registerUser(registerDTO);
    return R.success();
  }

  @Operation(summary = "请求发送验证邮件")
  @PutMapping("/verify")
  public R<?> sendVerifyMail() {
    userInfoService.sendVerifyMail();
    return R.success();
  }

  @Operation(summary = "验证邮箱")
  @PutMapping("/verify/{encodeUsername}")
  public R<?> verify(@PathVariable String encodeUsername) {
    userInfoService.verifyUser(encodeUsername);
    return R.success();
  }
}