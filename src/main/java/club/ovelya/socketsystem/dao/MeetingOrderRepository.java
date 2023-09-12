package club.ovelya.socketsystem.dao;

import club.ovelya.socketsystem.entity.MeetingOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingOrderRepository extends JpaRepository<MeetingOrder, Integer> {

}
