package club.ovelya.socketsystem.controller;

import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

  @GetMapping("/hello")
  @Async
  public void hello() throws InterruptedException {

  }

  @Async
  public void testHello() throws InterruptedException {
    Thread.sleep(10000);
    throw new RuntimeException("test");
  }
}
