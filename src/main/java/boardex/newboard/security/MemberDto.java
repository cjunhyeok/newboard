package boardex.newboard.security;

import lombok.Data;

@Data
public class MemberDto {

    private String userId;
    private String userPassword;
    private String nickName;
    private String role;

    private String name;
    private String address;
    private String birthday;
}
