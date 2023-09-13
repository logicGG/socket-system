package club.ovelya.socketsystem;

import club.ovelya.socketsystem.pojo.dto.meeting.PutMeetingDTO;
import club.ovelya.socketsystem.service.meeting.MeetingAdminService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SocketSystemApplicationTests {

    @Test
    void contextLoads() {
        MeetingAdminService meetingAdminService = new MeetingAdminService();
        meetingAdminService.putMeeting(new PutMeetingDTO());
    }

}
