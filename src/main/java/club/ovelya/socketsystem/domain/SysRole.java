package club.ovelya.socketsystem.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Entity
@Data
public class SysRole implements Serializable {
    @Id
    @GeneratedValue
    private Integer id;
    private String role;
    private String description;//描述
    private Boolean available = Boolean.FALSE;//是否可用

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "SysRolePermission", joinColumns = {@JoinColumn(name = "roleId")}, inverseJoinColumns = {@JoinColumn(name = "permissionId")})
    private List<SysPermission> permissions;

    @ManyToMany(targetEntity = UserInfo.class)
    @JoinTable(name = "sys_user_role", joinColumns = {@JoinColumn(name = "role_id")}, inverseJoinColumns = {@JoinColumn(name = "uid")})
    private List<UserInfo> userInfoList;


}
