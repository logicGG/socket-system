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

  private Integer baseFee1;
  private String baseFee1Description;
  private byte baseFee1Available; //1可用 0不可用

  private Integer baseFee2;
  private String baseFee2Description;
  private byte baseFee2Available; //1可用 0不可用

  private Integer baseFee3;
  private String baseFee3Description;
  private byte baseFee3Available; //1可用 0不可用
}
