package club.ovelya.socketsystem.config;

import club.ovelya.socketsystem.dao.UserInfoRepository;
import club.ovelya.socketsystem.entity.SysRole;
import club.ovelya.socketsystem.entity.UserInfo;
import jakarta.annotation.Resource;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.List;

public class ShiroRealm extends AuthorizingRealm {

  @Resource
  private UserInfoRepository userInfoRepository;

  @Override
  protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
    String username = (String) principalCollection.getPrimaryPrincipal();
    SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
    List<SysRole> roleList = userInfoRepository.findByUsername(username).getRoleList();
    for (SysRole role : roleList) {
      simpleAuthorizationInfo.addRole(role.getRole());
    }
    return simpleAuthorizationInfo;
  }

  @Override
  protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
          throws AuthenticationException {
    UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;
    String usernameOrEmail = usernamePasswordToken.getUsername();
    UserInfo user = userInfoRepository.findByUsernameOrEmail(usernameOrEmail);
    if (user == null) {
      throw new UnknownAccountException("用户名错误！");
    }
    return new SimpleAuthenticationInfo(user.getUsername(), user.getPassword(), null, getName());
  }
}
