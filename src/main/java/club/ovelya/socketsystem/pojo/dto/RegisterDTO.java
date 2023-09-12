package club.ovelya.socketsystem.pojo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 注册信息
 */
@AllArgsConstructor
@Builder
@Getter
@Setter
public class RegisterDTO implements Serializable {

  @NotBlank
  @Pattern(regexp = "^[a-zA-Z0-9_]{4,16}$", message = "账号长度必须在4~16之间，只能包含字母、数字和下划线")
  private String username;

  @NotBlank(message = "用户昵称不能为空")
  private String name;

  @NotBlank
  @Email(regexp = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$", message = "邮箱格式不正确")
  private String email;

  @NotBlank
  @Pattern(regexp = "^1[3-9](\\d{9})$", message = "手机号格式不正确")
  private String phoneNumber;

  @NotBlank
  @Size(min = 6, max = 80, message = "密码长度至少6位以上")
  private String password;
}
