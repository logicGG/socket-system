package club.ovelya.socketsystem.entity;

import club.ovelya.socketsystem.entity.parent.AbstractBaseTimeEntity;
import club.ovelya.socketsystem.utils.WebSocketMessageUtils.Type;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class WebSocketMessage extends AbstractBaseTimeEntity {

  @Id
  @GeneratedValue
  private Integer id;
  private String fromWho;
  private String toWho;
  @Enumerated
  private Type type;
  private String content;
}
