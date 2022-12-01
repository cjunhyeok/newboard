package boardex.newboard.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
public class BaseEntity {

    @Column(nullable = false)
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
}
