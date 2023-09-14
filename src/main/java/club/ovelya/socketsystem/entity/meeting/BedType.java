package club.ovelya.socketsystem.entity.meeting;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.io.Serializable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class BedType implements Serializable {

  @Id
  @GeneratedValue
  private Integer id;
  private Integer baseFee;
  private String baseFeeDescription;
}
