package club.ovelya.socketsystem.domain;

import club.ovelya.socketsystem.domain.parent.AbstractBaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
public class SysRole extends AbstractBaseTimeEntity implements Serializable {
    @Id
    @GeneratedValue
    private Integer id;
    private String role;
    private String description;//描述
    private Boolean available = Boolean.FALSE;//是否可用

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "sys_role_permission", joinColumns = {@JoinColumn(name = "role_id")}, inverseJoinColumns = {@JoinColumn(name = "permission_id")})
    private List<SysPermission> permissions;

    @ManyToMany(targetEntity = UserInfo.class)
    @JoinTable(name = "sys_user_role", joinColumns = {@JoinColumn(name = "role_id")}, inverseJoinColumns = {@JoinColumn(name = "uid")})
    private List<UserInfo> userInfoList;

}
