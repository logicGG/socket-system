package club.ovelya.socketsystem.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class MysqlAspect {


  @After(value = "execution(* club.ovelya.socketsystem.dao.*.*(..))")
  public void mysqlLog(JoinPoint joinPoint) {
    log.info("MySQL操作,切入点:" + joinPoint.getSignature().toString());
  }
}
