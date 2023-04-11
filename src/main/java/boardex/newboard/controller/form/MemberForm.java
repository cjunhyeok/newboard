package boardex.newboard.controller.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class MemberForm {

    @NotEmpty
    private String userId;

    @NotEmpty
    private String userPassword;

    @NotEmpty
    private String nickName;
    private String name;
    private String address;
    private String birthday;

}
