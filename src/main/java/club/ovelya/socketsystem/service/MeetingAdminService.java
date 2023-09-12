package club.ovelya.socketsystem.service;

import club.ovelya.socketsystem.dao.MeetingInfoRepository;
import club.ovelya.socketsystem.entity.MeetingInfo;
import club.ovelya.socketsystem.pojo.dto.PostMeetingDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
