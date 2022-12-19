package boardex.newboard.api;

import boardex.newboard.domain.Member;
import boardex.newboard.service.MemberService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    @PostMapping("/api/members")
    public CreateMemberResponse saveMember(@RequestBody @Valid CreateMemberRequest request) {
        Member member = new Member(request.userId, request.userPassword, request.nickName,
                                    request.name, request.address, request.birthday);

        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    // saveMember Dto
    @Data
    static class CreateMemberRequest {

        @NotEmpty
        private String userId;
        @NotEmpty
        @Size(min = 8)
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&])[A-Za-z[0-9]$@$!%*#?&]{8,20}$")
        private String userPassword;
        @NotEmpty
        private String nickName;

        private String name;
        private String address;
        private String birthday;
    }

    @Data
    static class CreateMemberResponse {
        private Long id;

        public CreateMemberResponse(Long id) {
            this.id = id;
        }
    }
}
