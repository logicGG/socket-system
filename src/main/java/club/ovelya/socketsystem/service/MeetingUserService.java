package club.ovelya.socketsystem.service;

import club.ovelya.socketsystem.dao.MeetingInfoRepository;
import club.ovelya.socketsystem.dao.MeetingOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MeetingUserService {

  @Autowired
  private MeetingInfoRepository meetingInfoRepository;
  @Autowired
  private MeetingOrderRepository meetingOrderRepository;
}
