package club.ovelya.socketsystem.controller;

import club.ovelya.socketsystem.utils.R;
import club.ovelya.socketsystem.webSocket.WebSocket;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiresRoles("admin")
@Tag(name = "websocket接口")
@RequestMapping("/api/websocket")
public class WebSocketController {

  @GetMapping("/list")
  public R<?> getList() {
    return R.custom(200, "ok", WebSocket.getActiveUser());
  }
}
