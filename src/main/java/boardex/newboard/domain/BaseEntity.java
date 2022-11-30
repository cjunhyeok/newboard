package boardex.newboard.domain;

import lombok.Getter;

import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter
public class BaseEntity {

    //@Column(nullable = false)
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
}
