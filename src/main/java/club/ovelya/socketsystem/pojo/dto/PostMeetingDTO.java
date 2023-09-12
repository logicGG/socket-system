package club.ovelya.socketsystem.pojo.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 聚会信息
 */
@AllArgsConstructor
@Builder
@Getter
@Setter
public class PostMeetingDTO {

  private String name;

  private String addr;

  private LocalDateTime startDate;

  private LocalDateTime endDate;

  private String description;

  private Integer baseFee;
}
