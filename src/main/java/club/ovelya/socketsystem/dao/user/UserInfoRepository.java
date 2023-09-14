package club.ovelya.socketsystem.dao.user;

import club.ovelya.socketsystem.entity.user.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {


  UserInfo findByUsername(String username);

  UserInfo findByEmail(String email);

  @Query(value = "FROM UserInfo WHERE username=?1 or email=?1 or phoneNumber=?1")
  UserInfo findByUsernameOrEmailOrPhoneNumber(String username);

}
