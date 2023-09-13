package club.ovelya.socketsystem.pojo.vo;

import club.ovelya.socketsystem.entity.meeting.BedType;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class GetMeetingVo {

  private Integer id;

  private String name;

  private String addr;

  private LocalDateTime startDate;

  private LocalDateTime endDate;

  private GetUserInfoVo hostInfoVo;

  private String description;

  private List<BedType> typeList;

  private List<GetUserInfoVo> participantsInfo;

}
