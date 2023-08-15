package club.ovelya.socketsystem.config;

import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.mindrot.jbcrypt.BCrypt;
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
    return shiroFilterFactoryBean;
  }


  //Redis缓存管理器
  @Bean
  public RedisCacheManager redisCacheManager() {
    RedisCacheManager redisCacheManager = new RedisCacheManager();
    redisCacheManager.setRedisManager(redisManager());
    return redisCacheManager;
  }

  @Bean
  public RedisManager redisManager() {
    RedisManager redisManager = new RedisManager();
    //设置一小时超时，单位是秒
    redisManager.setExpire(3600);
    return redisManager;
  }
}