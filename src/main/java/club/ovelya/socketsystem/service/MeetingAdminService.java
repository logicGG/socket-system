package club.ovelya.socketsystem.service;

import club.ovelya.socketsystem.dao.MeetingInfoRepository;
import club.ovelya.socketsystem.entity.MeetingInfo;
import club.ovelya.socketsystem.pojo.dto.DeleteMeetingDTO;
import club.ovelya.socketsystem.pojo.dto.PostMeetingDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MeetingAdminService {

  @Autowired
  private MeetingInfoRepository meetingInfoRepository;

  /**
   * 添加一场聚会
   *
   * @param postMeetingDTO 聚会信息
   */
  public void addMeeting(PostMeetingDTO postMeetingDTO) {
    MeetingInfo meetingInfo = new MeetingInfo();
    BeanUtils.copyProperties(postMeetingDTO, meetingInfo);
    meetingInfoRepository.save(meetingInfo);
  }
  //TODO:删改查

  /**
   * 删除聚会
   *
   * @param deleteMeetingDTO 聚会ID
   */
  public void deleteMeeting(DeleteMeetingDTO deleteMeetingDTO) {
    Integer id = deleteMeetingDTO.getId();
    Optional<MeetingInfo> meetingInfoOptional = meetingInfoRepository.findById(id);
    if (meetingInfoOptional.isEmpty()) {
      throw new RuntimeException("查无此聚会");
    }
    MeetingInfo meetingInfo = meetingInfoOptional.get();
    meetingInfo.setDelType((byte) 1);
    meetingInfoRepository.save(meetingInfo);
  }

  public void putMeeting(PostMeetingDTO)
}
