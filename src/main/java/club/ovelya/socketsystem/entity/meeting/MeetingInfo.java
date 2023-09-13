package club.ovelya.socketsystem.entity.meeting;

import club.ovelya.socketsystem.entity.parent.AbstractBaseTimeEntity;
import club.ovelya.socketsystem.entity.user.UserInfo;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class MeetingInfo extends AbstractBaseTimeEntity implements Serializable {

  @Id
  @GeneratedValue
  private Integer id;

  private String name;

  private String addr;

  private LocalDateTime startDate;

  private LocalDateTime endDate;

  @OneToOne
  private UserInfo hostInfo;

  private String description;

  @OneToMany
  private List<BedType> typeList;

  @ManyToMany
  private List<UserInfo> participants;

  private byte delType = 0;//删除标记

}
