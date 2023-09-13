package club.ovelya.socketsystem.entity.user;

import club.ovelya.socketsystem.entity.parent.AbstractBaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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
  private String username;

  private String name;

  @Column(unique = true, nullable = false)
  private String email;

  private String phoneNumber;

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
