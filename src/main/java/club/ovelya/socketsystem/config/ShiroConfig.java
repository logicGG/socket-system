package club.ovelya.socketsystem.config;


import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
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
    defaultWebSecurityManager.setCacheManager(redisCacheManager());
    //设置cookie管理器
    defaultWebSecurityManager.setRememberMeManager(rememberMeManager());
    //会话管理器
    defaultWebSecurityManager.setSessionManager(sessionManager());
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

  /**
   * 配置Redis缓存管理器
   */
  @Bean
  public RedisCacheManager redisCacheManager() {
    RedisCacheManager redisCacheManager = new RedisCacheManager();
    //设置redis管理器
    redisCacheManager.setRedisManager(redisManager());
    return redisCacheManager;
  }

  /**
   * 配置redis管理器
   */
  @Bean
  public RedisManager redisManager() {
    RedisManager redisManager = new RedisManager();
    //设置一小时超时，单位是秒
    redisManager.setExpire(36000);
    return redisManager;
  }

  /**
   * 注册RedisSessionDAO
   */
  @Bean
  public SessionDAO sessionDAO() {
    RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
    redisSessionDAO.setRedisManager(redisManager());
    return redisSessionDAO;
  }

  /**
   * 注册SessionManager会话管理器
   */
  @Bean
  public SessionManager sessionManager() {
    DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
    List<SessionListener> listeners = new ArrayList<>();
    //需要添加自己实现的会话监听器
    listeners.add(new CustomShiroSessionListener());
    //添加会话监听器给sessionManager管理
    sessionManager.setSessionListeners(listeners);
    //添加SessionDAO给sessionManager管理
    sessionManager.setSessionDAO(sessionDAO());
    //设置全局(项目)session超时单位 毫秒   -1为永不超时
    sessionManager.setGlobalSessionTimeout(360000);
    return sessionManager;
  }
//  @Bean("delegatingFilterProxy")
//  public FilterRegistrationBean<?> delegatingFilterProxy() {
//    FilterRegistrationBean<DelegatingFilterProxy> registration = new FilterRegistrationBean<>();
//    DelegatingFilterProxy proxy = new DelegatingFilterProxy();
//    proxy.setTargetBeanName("shiroFilter");
//    proxy.setTargetFilterLifecycle(true);
//    registration.setFilter(proxy);
//    registration.setAsyncSupported(true);
//    registration.setDispatcherTypes(ASYNC, REQUEST, FORWARD, INCLUDE, ERROR);
//    return registration;
//  }
}