package club.ovelya.socketsystem.utils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@Setter
@NoArgsConstructor
class Message<T> {

  String status;
  //向前端返回的内容
  String message;
  T data;

  public Message(String status, String message) {
    this.status = status;
    this.message = message;
  }

  public Message(String status, String message, T data) {
    this.data = data;
    this.status = status;
    this.message = message;
  }

  public static <T> Message<?> custom(String status, String message, T data) {
    return new Message<>(status, message, data);
  }

  public static <T> Message<T> custom(String status, String message) {
    return new Message<>(status, message);
  }

  public static HttpStatusCode num2HttpStatus(String code) {
    HttpStatus status = HttpStatus.NOT_FOUND;
    for (HttpStatus httpStatus : HttpStatus.values()) {
      boolean b = Integer.parseInt(code) == httpStatus.value();
      if (b) {
        return httpStatus;
      }
    }
    return status;
  }

}
