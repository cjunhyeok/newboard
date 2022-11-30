package boardex.newboard.domain;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

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
    }

    public Member(String userId, String userPassword, String nickName, String name, String address, String birthday) {
        this.userId = userId;
        this.userPassword = userPassword;
        this.nickName = nickName;
        this.name = name;
        this.address = address;
        this.birthday = birthday;
    }

    public void simpleMember(String userId, String userPassword, String nickName){
        this.userId = userId;
        this.userPassword = userPassword;
        this.nickName = nickName;
    }
}
