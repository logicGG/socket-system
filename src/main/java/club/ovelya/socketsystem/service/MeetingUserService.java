package club.ovelya.socketsystem.service;

import club.ovelya.socketsystem.dao.MeetingInfoRepository;
import club.ovelya.socketsystem.dao.MeetingOrderRepository;
import club.ovelya.socketsystem.dao.UserInfoRepository;
import club.ovelya.socketsystem.entity.MeetingInfo;
import club.ovelya.socketsystem.entity.MeetingOrder;
import club.ovelya.socketsystem.entity.UserInfo;
import club.ovelya.socketsystem.pojo.dto.AttendMeetingDTO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MeetingUserService {

  @Autowired
  private MeetingInfoRepository meetingInfoRepository;
  @Autowired
  private MeetingOrderRepository meetingOrderRepository;
  @Autowired
  private UserInfoRepository userInfoRepository;

  public void attendMeeting(AttendMeetingDTO attendMeetingDTO) {
    //获取用户对象
    Subject subject = SecurityUtils.getSubject();
    String username = (String) subject.getPrincipal();
    UserInfo userInfo = userInfoRepository.findByUsername(username);
    //获取聚会对象
    Integer meetingId = attendMeetingDTO.getMeetingId();
    Optional<MeetingInfo> meetingInfoOptional = meetingInfoRepository.findById(meetingId);
    if (meetingInfoOptional.isEmpty()) {
      throw new RuntimeException("找不到对应的聚会");
    }
    MeetingInfo meetingInfo = meetingInfoOptional.get();
    //添加到聚会里
    List<UserInfo> participants = meetingInfo.getParticipants();
    participants.add(userInfo);
    meetingInfoRepository.save(meetingInfo);

    //开订单
    MeetingOrder meetingOrder = new MeetingOrder();
    byte type = attendMeetingDTO.getType();
    meetingOrder.setBaseFeeType(type)
            .setMeetingInfo(meetingInfo)
            .setDescription(attendMeetingDTO.getDescription())
            .setUserInfo(userInfo);
    switch (type) {
      case 1 -> meetingOrder.setBaseFee(meetingInfo.getBaseFee1());
      case 2 -> meetingOrder.setBaseFee(meetingInfo.getBaseFee2());
      case 3 -> meetingOrder.setBaseFee(meetingInfo.getBaseFee3());
    }
    meetingOrderRepository.save(meetingOrder);
  }
}
