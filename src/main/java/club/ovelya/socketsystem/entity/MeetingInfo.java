package club.ovelya.socketsystem.entity;

import club.ovelya.socketsystem.entity.parent.AbstractBaseTimeEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
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

  private Integer baseFee1;
  private String baseFee1Description = "不过夜";
  private byte baseFee1Available = 1; //1可用 0不可用

  private Integer baseFee2;
  private String baseFee2Description = "大通铺";
  private byte baseFee2Available = 1; //1可用 0不可用

  private Integer baseFee3;
  private String baseFee3Description = "普通床";
  private byte baseFee3Available = 1; //1可用 0不可用

  @OneToMany
  private List<UserInfo> participants;

}
