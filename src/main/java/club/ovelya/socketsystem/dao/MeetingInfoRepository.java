package club.ovelya.socketsystem.dao;

import club.ovelya.socketsystem.entity.MeetingInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingInfoRepository extends JpaRepository<MeetingInfo, Integer> {

}
