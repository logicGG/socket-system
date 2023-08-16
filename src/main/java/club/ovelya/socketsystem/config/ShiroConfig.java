package club.ovelya.socketsystem.config;

import java.util.LinkedHashMap;
import java.util.Map;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShiroConfig {

  @Bean("securityManager")
  public DefaultWebSecurityManager securityManager() {
    //关联Realm
    DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager(
        shiroRealm());
    //设置redis管理器
//    defaultWebSecurityManager.setCacheManager(redisCacheManager());
    //设置cookie管理器
    defaultWebSecurityManager.setRememberMeManager(rememberMeManager());
    return defaultWebSecurityManager;
  }


  @Bean
  public ShiroRealm shiroRealm() {
    ShiroRealm shiroRealm = new ShiroRealm();
    //设置加密管理器
    shiroRealm.setCredentialsMatcher((authenticationToken, authenticationInfo) -> {
      UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;
      String plaintext = new String(usernamePasswordToken.getPassword());
      String hashed = authenticationInfo.getCredentials().toString();
      return BCrypt.checkpw(plaintext, hashed);
    });
    return shiroRealm;
  }

  @Bean
  public ShiroFilterFactoryBean shiroFilter(DefaultWebSecurityManager securityManager) {
    ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
    //设置安全管理器
    shiroFilterFactoryBean.setSecurityManager(securityManager);
    Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
//    filterChainDefinitionMap.put("/hello","roles[admin]");
//    filterChainDefinitionMap.put("/logout","logout");
//    shiroFilterFactoryBean.setUnauthorizedUrl("/401");
    shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
    return shiroFilterFactoryBean;
  }

  @Bean
  public SimpleCookie rememberMeCookie() {
    //这个参数是cookie的名称,对应前端的checkbox的name=rememberMe
    SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
    //如果httpOnly设置为true，则客户端不会暴露给客户端脚本代码，使用HttpOnly cookie有助于减少某些类型的跨站点脚本攻击；
    simpleCookie.setHttpOnly(true);
    //记住我cookie生效时间10小时(单位秒)
    simpleCookie.setMaxAge(36000);
    return simpleCookie;
  }

  @Bean
  public CookieRememberMeManager rememberMeManager() {
    CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
    cookieRememberMeManager.setCookie(rememberMeCookie());
    // rememberMe cookie加密的密钥  建议每个项目都不一样 默认AES算法 密钥长度(128 256 512 位)
    cookieRememberMeManager.setCipherKey(Base64.decode("ovelyaSocketSystem"));
    return cookieRememberMeManager;
  }

  /**
   * 开启shiro aop注解支持 使用代理方式;所以需要开启代码支持
   */
  @Bean
  public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
    AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
    authorizationAttributeSourceAdvisor.setSecurityManager(securityManager());
    return authorizationAttributeSourceAdvisor;
  }

  /**
   * 开启Shiro的注解(如@RequiresRoles,@RequiresPermissions),需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证
   */
  @Bean
  @ConditionalOnMissingBean
  public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
    DefaultAdvisorAutoProxyCreator app = new DefaultAdvisorAutoProxyCreator();
    app.setProxyTargetClass(true);
    return app;
  }

}