package club.ovelya.socketsystem.entity;

import club.ovelya.socketsystem.entity.parent.AbstractBaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 用户表
 */
@Entity
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class UserInfo extends AbstractBaseTimeEntity implements Serializable {

  @Id
  @GeneratedValue
  private Integer uid;

  @Column(unique = true, nullable = false)
  @Pattern(regexp = "^[a-zA-Z0-9_]{4,16}$", message = "账号长度必须在4~16之间，只能包含字母、数字和下划线")
  private String username;

  @NotBlank(message = "用户昵称不能为空")
  private String name;

  @Email(regexp = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$", message = "邮箱格式不正确")
  @Column(unique = true, nullable = false)
  private String email;

  @Pattern(regexp = "^1[3-9](\\d{9})$", message = "手机号格式不正确")
  private String phoneNumber;

  @Size(min = 6, max = 80, message = "密码长度至少6位以上")
  private String password;

  private int state = 0;//0：未认证 1：正常 2：锁定

  private int delType = 0;//删除标记

  private LocalDateTime lastLoginTime;

  private String lastLoginIP;

  @ManyToMany(fetch = FetchType.EAGER, targetEntity = SysRole.class)
  @JoinTable(name = "sys_user_role", joinColumns = {
      @JoinColumn(name = "uid")}, inverseJoinColumns = {@JoinColumn(name = "role_id")})
  private List<SysRole> roleList;


}
