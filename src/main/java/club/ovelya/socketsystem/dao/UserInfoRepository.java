package club.ovelya.socketsystem.dao;

import club.ovelya.socketsystem.domain.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {


  UserInfo findByUsername(String username);


}
