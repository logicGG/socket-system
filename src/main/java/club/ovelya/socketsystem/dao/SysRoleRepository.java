package club.ovelya.socketsystem.dao;

import club.ovelya.socketsystem.entity.SysRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SysRoleRepository extends JpaRepository<SysRole, Integer> {
  SysRole findByRole(String role);
}
