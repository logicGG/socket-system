package club.ovelya.socketsystem.service.meeting;

import club.ovelya.socketsystem.dao.meeting.BedTypeRepository;
import club.ovelya.socketsystem.dao.meeting.MeetingInfoRepository;
import club.ovelya.socketsystem.entity.meeting.BedType;
import club.ovelya.socketsystem.entity.meeting.MeetingInfo;
import club.ovelya.socketsystem.entity.user.UserInfo;
import club.ovelya.socketsystem.pojo.dto.meeting.DeleteMeetingDTO;
import club.ovelya.socketsystem.pojo.dto.meeting.PostMeetingDTO;
import club.ovelya.socketsystem.pojo.dto.meeting.PutMeetingDTO;
import club.ovelya.socketsystem.utils.SubjectUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MeetingAdminService {

  @Autowired
  private MeetingInfoRepository meetingInfoRepository;
  @Autowired
  private BedTypeRepository bedTypeRepository;

  /**
   * 添加一场聚会
   *
   * @param postMeetingDTO 聚会信息
   */
  public void addMeeting(PostMeetingDTO postMeetingDTO) {
    //保存床位信息
    List<BedType> typeList = postMeetingDTO.getTypeList();
    bedTypeRepository.saveAll(typeList);
    //保存聚会信息
    MeetingInfo meetingInfo = new MeetingInfo();
    BeanUtils.copyProperties(postMeetingDTO, meetingInfo);
    UserInfo userInfo = SubjectUtil.getCurrentUserInfo();
    meetingInfo.setHostInfo(userInfo);
    meetingInfoRepository.save(meetingInfo);
  }

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

  /**
   * 更新聚会
   *
   * @param putMeetingDTO 聚会信息
   */
  public void putMeeting(PutMeetingDTO putMeetingDTO) {
    Integer id = putMeetingDTO.getId();
    Optional<MeetingInfo> meetingInfoOptional = meetingInfoRepository.findById(id);
    if (meetingInfoOptional.isEmpty()) {
      throw new RuntimeException("找不到此聚会");
    }
    MeetingInfo meetingInfo = meetingInfoOptional.get();
    BeanUtil.copyProperties(putMeetingDTO, meetingInfo,
        CopyOptions.create().setIgnoreNullValue(true));
    meetingInfoRepository.save(meetingInfo);
  }
}
