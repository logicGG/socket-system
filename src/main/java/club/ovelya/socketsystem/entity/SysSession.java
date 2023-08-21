package club.ovelya.socketsystem.entity;

import club.ovelya.socketsystem.entity.parent.AbstractBaseTimeEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class SysSession extends AbstractBaseTimeEntity {

  @Id
  private String id;
  private String session;
}
