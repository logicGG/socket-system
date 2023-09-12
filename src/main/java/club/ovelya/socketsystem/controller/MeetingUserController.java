package club.ovelya.socketsystem.controller;

import club.ovelya.socketsystem.pojo.dto.AttendMeetingDTO;
import club.ovelya.socketsystem.utils.R;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiresRoles(value = "user")
@RequestMapping("/meeting/user")
public class MeetingUserController {

  @PutMapping("/attend")
  public R<?> attendMeeting(@RequestBody AttendMeetingDTO attendMeetingDTO) {

  }
}
