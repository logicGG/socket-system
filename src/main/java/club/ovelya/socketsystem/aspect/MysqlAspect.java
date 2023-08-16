package club.ovelya.socketsystem.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MysqlAspect {

  private static final Logger LOG = LoggerFactory.getLogger(MysqlAspect.class);

  @After(value = "execution(* club.ovelya.socketsystem.dao.*.*(..))")
  public void mysqlLog(JoinPoint joinPoint) {
    LOG.info("MySQL操作,切入点:" + joinPoint.getSignature().toString());
  }
}
