package club.ovelya.socketsystem.dao.meeting;

import club.ovelya.socketsystem.entity.meeting.MeetingOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingOrderRepository extends JpaRepository<MeetingOrder, Integer> {

}
