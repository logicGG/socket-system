package club.ovelya.socketsystem.entity.parent;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@EntityListeners(value = AuditingEntityListener.class)
@Getter
@Setter
public class AbstractBaseTimeEntity {

  @CreatedDate
  @Column(nullable = false, updatable = false)
  private LocalDateTime createTime;

  @LastModifiedDate
  @Column()
  private LocalDateTime updateTime;
}
