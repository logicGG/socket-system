package club.ovelya.socketsystem.service.meeting;

import club.ovelya.socketsystem.dao.meeting.BedTypeRepository;
import club.ovelya.socketsystem.dao.meeting.MeetingInfoRepository;
import club.ovelya.socketsystem.dao.meeting.MeetingOrderRepository;
import club.ovelya.socketsystem.dao.user.UserInfoRepository;
import club.ovelya.socketsystem.entity.meeting.BedType;
import club.ovelya.socketsystem.entity.meeting.MeetingInfo;
import club.ovelya.socketsystem.entity.meeting.MeetingOrder;
import club.ovelya.socketsystem.entity.user.UserInfo;
import club.ovelya.socketsystem.pojo.dto.meeting.AttendMeetingDTO;
import club.ovelya.socketsystem.pojo.vo.GetMeetingVo;
import club.ovelya.socketsystem.pojo.vo.GetUserInfoVo;
import club.ovelya.socketsystem.utils.SubjectUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MeetingUserService {

  @Autowired
  private MeetingInfoRepository meetingInfoRepository;
  @Autowired
  private MeetingOrderRepository meetingOrderRepository;
  @Autowired
  private UserInfoRepository userInfoRepository;
  @Autowired
  private BedTypeRepository bedTypeRepository;

  /**
   * 参加聚会
   *
   * @param attendMeetingDTO 聚会ID和床位id
   */
  public void attendMeeting(AttendMeetingDTO attendMeetingDTO) {
    //获取用户对象
    UserInfo userInfo = SubjectUtil.getCurrentUserInfo();
    //获取聚会对象
    Integer meetingId = attendMeetingDTO.getMeetingId();
    Optional<MeetingInfo> meetingInfoOptional = meetingInfoRepository.findById(meetingId);
    if (meetingInfoOptional.isEmpty()) {
      throw new RuntimeException("找不到对应的聚会");
    }
    MeetingInfo meetingInfo = meetingInfoOptional.get();
    //不能重复参加聚会
    Stream<UserInfo> userInfoStream = meetingInfo.getParticipants().stream();
    if (userInfoStream.anyMatch(item -> Objects.equals(item.getUid(), userInfo.getUid()))) {
      throw new RuntimeException("不能重复参加");
    }
    //添加到聚会里
    List<UserInfo> participants = meetingInfo.getParticipants();
    participants.add(userInfo);

    //开订单
    MeetingOrder meetingOrder = new MeetingOrder();
    Integer typeId = attendMeetingDTO.getTypeId();
    Stream<BedType> bedTypeStream = meetingInfo.getTypeList().stream();
    if (bedTypeStream.noneMatch(item -> Objects.equals(item.getId(), typeId))) {
      throw new RuntimeException("床位类型不在此聚会里");
    }
    //获取bedType对象
    Optional<BedType> typeOptional = bedTypeRepository.findById(typeId);
    if (typeOptional.isEmpty()) {
      throw new RuntimeException("找不到此类型床位");
    }
    //设置订单对象属性
    meetingOrder.setBedType(typeId)
        .setMeetingInfo(meetingInfo)
        .setDescription(attendMeetingDTO.getDescription())
        .setUserInfo(userInfo)
        .setBaseFee(typeOptional.get().getBaseFee());

    //保存到数据库
    meetingInfoRepository.save(meetingInfo);
    meetingOrderRepository.save(meetingOrder);
  }

  /**
   * 获取聚会列表
   *
   * @return 聚会列表
   */
  public List<GetMeetingVo> getMeeting() {
    //找到所有未删除的聚会
    List<MeetingInfo> meetingInfoList = meetingInfoRepository.findByDelType((byte) 0);
    //Vo列表
    List<GetMeetingVo> meetingVoList = new ArrayList<>();
    for (MeetingInfo meetingInfo : meetingInfoList) {
      GetMeetingVo getMeetingVo = new GetMeetingVo();
      BeanUtils.copyProperties(meetingInfo, getMeetingVo);
      //设置主办
      if (meetingInfo.getHostInfo() != null) {
        GetUserInfoVo getUserInfoVo = new GetUserInfoVo();
        BeanUtils.copyProperties(meetingInfo.getHostInfo(), getUserInfoVo);
        getMeetingVo.setHostInfoVo(getUserInfoVo);
      }
      //设置参加人员
      List<GetUserInfoVo> getUserInfoVoList = new ArrayList<>();
      for (UserInfo userInfo : meetingInfo.getParticipants()) {
        GetUserInfoVo userInfoVo = new GetUserInfoVo();
        BeanUtils.copyProperties(userInfo, userInfoVo);
        getUserInfoVoList.add(userInfoVo);
      }
      getMeetingVo.setParticipantsInfo(getUserInfoVoList);
      meetingVoList.add(getMeetingVo);
    }
    return meetingVoList;
  }
}
