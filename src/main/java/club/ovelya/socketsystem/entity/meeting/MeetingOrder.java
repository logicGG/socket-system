package club.ovelya.socketsystem.entity.meeting;

import club.ovelya.socketsystem.entity.parent.AbstractBaseTimeEntity;
import club.ovelya.socketsystem.entity.user.UserInfo;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class MeetingOrder extends AbstractBaseTimeEntity implements Serializable {

  @Id
  @GeneratedValue
  private Integer orderId;

  @ManyToOne
  private UserInfo userInfo;

  @ManyToOne
  private MeetingInfo meetingInfo;

  private Integer baseFee;

  private Integer addedFee;

  private Integer bedType;

  private String description;

  private byte state = 0;//0未完成 1已完成 2已停止

  private LocalDateTime paidDate;

  private LocalDateTime dropDate;
}
