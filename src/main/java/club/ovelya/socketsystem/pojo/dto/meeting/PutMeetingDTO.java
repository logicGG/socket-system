package club.ovelya.socketsystem.pojo.dto.meeting;

import club.ovelya.socketsystem.entity.meeting.BedType;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * 聚会信息
 */

@Getter
@Setter
public class PutMeetingDTO {

  @NotBlank(message = "id不能为空")
  private Integer id;

  private String name;

  private String addr;

  private LocalDateTime startDate;

  private LocalDateTime endDate;

  private String description;

  private List<BedType> typeList;
}
