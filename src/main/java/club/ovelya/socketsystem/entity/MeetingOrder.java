package club.ovelya.socketsystem.entity;

import club.ovelya.socketsystem.entity.parent.AbstractBaseTimeEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class MeetingOrder extends AbstractBaseTimeEntity implements Serializable {

  @Id
  @GeneratedValue
  private Integer orderId;

  @OneToOne
  private UserInfo userInfo;

  @OneToOne
  private MeetingInfo meetingInfo;

  private Integer baseFee;

  private Integer addedFee;

  private byte baseFeeType;

  private String description;

  private byte state = 0;//0未完成 1已完成 2已停止

  private LocalDateTime paidDate;

  private LocalDateTime dropDate;
}
