package club.ovelya.socketsystem.controller;

import club.ovelya.socketsystem.pojo.dto.AttendMeetingDTO;
import club.ovelya.socketsystem.service.MeetingUserService;
import club.ovelya.socketsystem.utils.R;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiresRoles(value = "user")
@RequestMapping("/meeting/user")
public class MeetingUserController {

  @Autowired
  private MeetingUserService meetingUserService;

  @PutMapping("/attend")
  public R<?> attendMeeting(@RequestBody AttendMeetingDTO attendMeetingDTO) {
    meetingUserService.attendMeeting(attendMeetingDTO);
    return R.success();
  }
}
