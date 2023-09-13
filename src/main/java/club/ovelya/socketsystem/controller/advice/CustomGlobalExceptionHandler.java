package club.ovelya.socketsystem.controller.advice;

import club.ovelya.socketsystem.utils.HttpStatusUtil;
import club.ovelya.socketsystem.utils.R;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class CustomGlobalExceptionHandler {

  //处理AuthorizationException权限认证相关异常
  @ExceptionHandler(value = AuthorizationException.class)
  public R<?> authorizationException(Exception e) {
    return R.custom(HttpStatusUtil.UNAUTHORIZED, e.getMessage(), null);
  }

  //表单验证相关异常
  @ExceptionHandler(value = {MethodArgumentNotValidException.class})
  public R<?> handleValidateException(MethodArgumentNotValidException e) {
    List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
    String message = allErrors.stream().map(DefaultMessageSourceResolvable::getDefaultMessage)
        .collect(Collectors.joining(";"));
    return R.custom(HttpStatusUtil.FORBIDDEN, message, null);
  }

  @ExceptionHandler(value = UnauthenticatedException.class)
  public R<?> handleUnauthenticatedException(UnauthenticatedException e) {
    return R.custom(HttpStatusUtil.FORBIDDEN, "用户未登录", null);
  }

  @ExceptionHandler
  public R<?> exceptionHandler(Exception e) {
    return R.failMsg(e.getMessage());
  }
}