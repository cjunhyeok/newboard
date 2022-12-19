package boardex.newboard.domain;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter
public class Member extends BaseEntity{

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String userId;
    private String userPassword;
    private String nickName;

    private String name;
    private String address;
    private String birthday;

    public Member() {
        this.setCreatedDate(LocalDateTime.now());
        this.setLastModifiedDate(LocalDateTime.now());
    }

    public Member(String userId, String userPassword, String nickName, String name, String address, String birthday) {
        this.userId = userId;
        this.userPassword = userPassword;
        this.nickName = nickName;
        this.name = name;
        this.address = address;
        this.birthday = birthday;
        this.setCreatedDate(LocalDateTime.now());
        this.setLastModifiedDate(LocalDateTime.now());
    }

    //test
    public void simpleMember(String userId, String userPassword, String nickName){
        this.userId = userId;
        this.userPassword = userPassword;
        this.nickName = nickName;
        this.setCreatedDate(LocalDateTime.now());
        this.setLastModifiedDate(LocalDateTime.now());
    }

    public void updateMember(String userPassword, String nickName, String name, String address) {
        this.userPassword = userPassword;
        this.nickName = nickName;
        this.name = name;
        this.address = address;
        this.setLastModifiedDate(LocalDateTime.now());
    }
}
