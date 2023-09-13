package club.ovelya.socketsystem.pojo.dto.user;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 账号和密码
 */
@Builder
@AllArgsConstructor
@Getter
@Setter
public class LoginDTO implements Serializable {

  private String username;
  private String password;
}
