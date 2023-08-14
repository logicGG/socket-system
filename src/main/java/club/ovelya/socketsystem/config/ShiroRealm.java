package club.ovelya.socketsystem.config;

import club.ovelya.socketsystem.domain.UserInfo;
import club.ovelya.socketsystem.service.UserInfoService;
import jakarta.annotation.Resource;
import org.apache.shiro.authc.*;
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
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;
        String username = usernamePasswordToken.getUsername();
        UserInfo user = userInfoService.findByUsername(username);
        if (user == null) {
            throw new UnknownAccountException("用户名错误！");
        }
//        if (user.getState() == (byte) 0) {
//            throw new LockedAccountException("用户未验证！");
//        }
        String userPassword = user.getPassword();
        return new SimpleAuthenticationInfo(username, userPassword, null, getName());
    }
}
