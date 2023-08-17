package club.ovelya.socketsystem.domain;

import club.ovelya.socketsystem.domain.parent.AbstractBaseTimeEntity;
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
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class SysRole extends AbstractBaseTimeEntity implements Serializable {

  @Id
  @GeneratedValue
  private Integer id;
  private String role;
  private String description;//描述
  private Boolean available = Boolean.TRUE;//是否可用

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "sys_role_permission", joinColumns = {
      @JoinColumn(name = "role_id")}, inverseJoinColumns = {@JoinColumn(name = "permission_id")})
  private List<SysPermission> permissions;

  @ManyToMany(targetEntity = UserInfo.class)
  @JoinTable(name = "sys_user_role", joinColumns = {
      @JoinColumn(name = "role_id")}, inverseJoinColumns = {@JoinColumn(name = "uid")})
  private List<UserInfo> userInfoList;

  public SysRole(String role, String description, Boolean available) {
    this.role = role;
    this.description = description;
    this.available = available;
  }
}
