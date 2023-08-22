package club.ovelya.socketsystem.dao;

import club.ovelya.socketsystem.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {


  UserInfo findByUsername(String username);

  UserInfo findByEmail(String email);

  @Query(value = "FROM UserInfo WHERE username=?1 or email=?1")
  UserInfo findByUsernameOrEmail(String username);

  UserInfo findByUsernameOrEmail(String username, String email);
}
