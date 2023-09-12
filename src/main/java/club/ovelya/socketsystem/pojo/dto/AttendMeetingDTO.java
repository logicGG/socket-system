package club.ovelya.socketsystem.pojo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class AttendMeetingDTO {

  //聚会ID
  @NotBlank
  private Integer meetingId;

  //baseFee Type
  @NotBlank
  private byte type;

  private String description;


}
