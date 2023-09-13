package club.ovelya.socketsystem.controller.meeting;

import club.ovelya.socketsystem.pojo.dto.meeting.AttendMeetingDTO;
import club.ovelya.socketsystem.service.meeting.MeetingUserService;
import club.ovelya.socketsystem.utils.HttpStatusUtil;
import club.ovelya.socketsystem.utils.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiresRoles(value = "user")
@RequestMapping("/meeting/user")
@Tag(name = "Meeting用户接口")
public class MeetingUserController {

  @Autowired
  private MeetingUserService meetingUserService;

  @Operation(summary = "参加聚会")
  @PutMapping("/attend")
  public R<?> attendMeeting(@RequestBody AttendMeetingDTO attendMeetingDTO) {
    meetingUserService.attendMeeting(attendMeetingDTO);
    return R.success();
  }

  @Operation(summary = "获取聚会列表")
  @GetMapping("/meeting")
  public R<?> getMeeting() {
    return R.custom(HttpStatusUtil.SUCCESS, null, meetingUserService.getMeeting());
  }
}
