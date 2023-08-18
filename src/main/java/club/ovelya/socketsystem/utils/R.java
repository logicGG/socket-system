package club.ovelya.socketsystem.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class R<T> extends ResponseEntity<Message<?>> {

  public R(HttpStatus status) {
    super(status);
  }

  public R(String code, String msg, T data) {
    super(Message.custom(code, msg, data), Message.num2HttpStatus(code));
  }

  public R(String code, String msg) {
    super(Message.custom(code, msg), Message.num2HttpStatus(code));
  }

  public static <T> R<T> success() {
    return new R<>(HttpStatusUtils.SUCCESS, "成功");
  }

  public static <T> R<T> failMsg(String msg) {
    return new R<>(HttpStatusUtils.UNAUTHORIZED, msg);
  }

  public static <T> R<T> custom(String code, String msg, T data) {
    return new R<>(code, msg, data);
  }
}


