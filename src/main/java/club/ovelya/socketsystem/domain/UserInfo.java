package club.ovelya.socketsystem.domain;

import club.ovelya.socketsystem.domain.parent.AbstractBaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import java.io.Serializable;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * 用户表
 */
@Entity
@Getter
@Setter
public class UserInfo extends AbstractBaseTimeEntity implements Serializable {

  @Id
  @GeneratedValue
  private Integer uid;
  @Column(unique = true)
  private String username;
  private String name;
  private String password;
  private byte state = 0;//0：未认证 1：正常 2：锁定
  @ManyToMany(fetch = FetchType.EAGER, targetEntity = SysRole.class)
  @JoinTable(name = "sys_user_role", joinColumns = {
      @JoinColumn(name = "uid")}, inverseJoinColumns = {@JoinColumn(name = "role_id")})
  private List<SysRole> roleList;

  public UserInfo(String username, String name, String password) {
    this.username = username;
    this.name = name;
    this.password = password;
  }

  public UserInfo() {

  }
}
