package club.ovelya.socketsystem.controller.advice;

import club.ovelya.socketsystem.utils.HttpStatusUtils;
import club.ovelya.socketsystem.utils.R;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class CustomGlobalExceptionHandler {

  //处理AuthorizationException认证相关异常
  @ExceptionHandler(value = AuthorizationException.class)
  public R<?> authorizationException(Exception e) {
    return R.custom(HttpStatusUtils.UNAUTHORIZED, e.getMessage(), null);
  }
}