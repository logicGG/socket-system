package club.ovelya.socketsystem.utils;


import club.ovelya.socketsystem.dao.user.UserInfoRepository;
import club.ovelya.socketsystem.entity.user.UserInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SubjectUtil {

  private static UserInfoRepository userInfoRepository;

  public static UserInfo getCurrentUserInfo() {
    Subject subject = SecurityUtils.getSubject();
    String username = (String) subject.getPrincipal();
    return userInfoRepository.findByUsername(username);
  }

  @Autowired
  public void setUserInfoRepository(
      UserInfoRepository userInfoRepository) {
    SubjectUtil.userInfoRepository = userInfoRepository;
  }
}
