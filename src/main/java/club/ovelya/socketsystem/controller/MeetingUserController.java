package club.ovelya.socketsystem.controller;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiresRoles(value = "user")
@RequestMapping("/meeting/user")
public class MeetingUserController {

}
