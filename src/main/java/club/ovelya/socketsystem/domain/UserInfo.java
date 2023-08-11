package club.ovelya.socketsystem.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Entity
@Data
public class UserInfo implements Serializable {
    @Id
    @GeneratedValue
    private Integer uid;
    @Column(unique = true)
    private String username;
    private String name;
    private String password;
    private String salt;
    private byte state;//0：未认证 1：正常 2：锁定
    @ManyToMany(fetch = FetchType.EAGER, targetEntity = SysRole.class)
    @JoinTable(name = "sys_user_role", joinColumns = {@JoinColumn(name = "uid")}, inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private List<SysRole> roleList;
}
