package club.ovelya.socketsystem.dao.user;

import club.ovelya.socketsystem.entity.user.SysRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SysRoleRepository extends JpaRepository<SysRole, Integer> {

    SysRole findByRole(String role);
}
