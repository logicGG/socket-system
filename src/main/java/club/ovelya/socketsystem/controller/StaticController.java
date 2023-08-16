package club.ovelya.socketsystem.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "静态页面接口")
public class StaticController {

  @Operation(summary = "401", description = "UnauthorizedUrl")
  @RequestMapping("/401")
  public String to401() {
    return "401 UnauthorizedUrl";
  }

}
