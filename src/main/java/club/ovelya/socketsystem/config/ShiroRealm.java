package club.ovelya.socketsystem.config;

import club.ovelya.socketsystem.domain.UserInfo;
import club.ovelya.socketsystem.service.UserInfoService;
import jakarta.annotation.Resource;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

public class ShiroRealm extends AuthorizingRealm {

  @Resource
  private UserInfoService userInfoService;

  @Override
  protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
    return null;
  }

  @Override
  protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
      throws AuthenticationException {
    UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;
    String username = usernamePasswordToken.getUsername();
    UserInfo user = userInfoService.findByUsername(username);
    if (user == null) {
      throw new UnknownAccountException("用户名错误！");
    }
    String userPassword = user.getPassword();
    return new SimpleAuthenticationInfo(username, userPassword, null, getName());
  }
}
