package club.ovelya.socketsystem.controller.meeting;

import club.ovelya.socketsystem.pojo.dto.meeting.DeleteMeetingDTO;
import club.ovelya.socketsystem.pojo.dto.meeting.PostMeetingDTO;
import club.ovelya.socketsystem.pojo.dto.meeting.PutMeetingDTO;
import club.ovelya.socketsystem.service.meeting.MeetingAdminService;
import club.ovelya.socketsystem.utils.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

  @Operation(summary = "添加聚会")
  @PostMapping("/meeting")
  public R<?> addMeeting(@RequestBody PostMeetingDTO postMeetingDTO) {
    meetingAdminService.addMeeting(postMeetingDTO);
    return R.success();
  }

  @Operation(summary = "删除聚会")
  @DeleteMapping("/meeting")
  public R<?> deleteMeeting(@RequestBody DeleteMeetingDTO deleteMeetingDTO) {
    meetingAdminService.deleteMeeting(deleteMeetingDTO);
    return R.success();
  }

  @Operation(summary = "更新聚会")
  @PutMapping("/meeting")
  public R<?> putMeeting(@RequestBody PutMeetingDTO putMeetingDTO) {
    meetingAdminService.putMeeting(putMeetingDTO);
    return R.success();
  }
}
