package club.ovelya.socketsystem.pojo.dto.meeting;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AttendMeetingDTO {

  //聚会ID
  @NotBlank
  private Integer meetingId;

  //bedType
  @NotBlank
  private Integer typeId;

  private String description;


}
