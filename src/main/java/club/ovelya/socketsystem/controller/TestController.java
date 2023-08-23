package club.ovelya.socketsystem.controller;

import club.ovelya.socketsystem.utils.R;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/hello")
    public R<?> hello() {
        Subject subject = SecurityUtils.getSubject();
        return R.custom(200, null, subject.getPrincipal());
    }
}
