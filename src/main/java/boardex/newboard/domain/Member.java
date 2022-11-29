package boardex.newboard.domain;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String userId;
    private String userPassword;
    private String nickName;

    private String name;
    private String address;
    private String birthday;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

}
