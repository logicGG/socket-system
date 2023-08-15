package club.ovelya.socketsystem.domain;

import club.ovelya.socketsystem.domain.parent.AbstractBaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import java.io.Serializable;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class SysPermission extends AbstractBaseTimeEntity implements Serializable {

  @Id
  @GeneratedValue
  private Integer id;
  private String name;
  @Column(columnDefinition = "enum('menu','button')")
  private String resourceType;//资源类型，[menu|button]
  private String url;//资源路径.
  private String permission; //权限字符串,menu例子：role:*，button例子：role:create,role:update,role:delete,role:view
  private Long parentId; //父编号
  private String parentIds; //父编号列表
  private Boolean available = Boolean.FALSE;
  @ManyToMany
  @JoinTable(name = "sys_role_permission", joinColumns = {
      @JoinColumn(name = "permission_id")}, inverseJoinColumns = {@JoinColumn(name = "role_id")})
  private List<SysRole> roles;

}
