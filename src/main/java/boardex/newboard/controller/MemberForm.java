package boardex.newboard.controller;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class MemberForm {

    @Column(unique = true)
    @NotEmpty
    private String userId;

    @NotEmpty
    private String userPassword;

    @Column(unique = true)
    @NotEmpty
    private String nickName;
    private String name;
    private String address;
    private String birthday;
}
