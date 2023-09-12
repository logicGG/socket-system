package club.ovelya.socketsystem.controller;

import club.ovelya.socketsystem.pojo.dto.PostMeetingDTO;
import club.ovelya.socketsystem.service.MeetingAdminService;
import club.ovelya.socketsystem.utils.R;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiresRoles(value = "admin")
@RequestMapping("/meeting/admin")
@Tag(name = "Meeting管理员接口")
public class MeetingAdminController {

  @Autowired
  private MeetingAdminService meetingAdminService;
  ;

  @PostMapping("/meeting")
  public R<?> addMeeting(@RequestBody PostMeetingDTO postMeetingDTO) {
    meetingAdminService.addMeeting(postMeetingDTO);
    return R.success();
  }
}
