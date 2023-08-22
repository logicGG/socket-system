package club.ovelya.socketsystem.entity;

import club.ovelya.socketsystem.entity.parent.AbstractBaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

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
    private Boolean available = true;//是否可用
    @ManyToMany(targetEntity = UserInfo.class)
    @JoinTable(name = "sys_user_role", joinColumns = {
            @JoinColumn(name = "role_id")}, inverseJoinColumns = {@JoinColumn(name = "uid")})
    private List<UserInfo> userInfoList;

    public SysRole(String role, String description) {
        this.role = role;
        this.description = description;
    }
}
