package club.ovelya.socketsystem.dao.meeting;

import club.ovelya.socketsystem.entity.meeting.MeetingInfo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingInfoRepository extends JpaRepository<MeetingInfo, Integer> {

  List<MeetingInfo> findByDelType(byte delType);
}
