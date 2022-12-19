package boardex.newboard.api;

import boardex.newboard.domain.Member;
import boardex.newboard.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.stream.Collectors;

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

    @PutMapping("/api/members/{id}")
    public UpdateMemberResponse updateMember(@PathVariable("id") Long id,
                                             @RequestBody @Valid UpdateMemberRequest request) {

        memberService.updateMember(id, request.userId, request.userPassword, request.nickName, request.address);
        Member findMember = memberService.findById(id);

        return new UpdateMemberResponse(findMember.getId());
    }

    @GetMapping("api/members")
    public Result members() {

        List<MembersDto> collect = memberService.findMembers().stream()
                .map(m -> new MembersDto(m.getName()))
                .collect(Collectors.toList());

        return new Result(collect);

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
    @AllArgsConstructor
    static class CreateMemberResponse {
        private Long id;
    }

    // updateMember Dto
    @Data
    static class UpdateMemberRequest {

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
    }

    @Data
    @AllArgsConstructor
    static class UpdateMemberResponse {
        private Long id;
    }

    // members Dto Result -> API의 확장성을 위해 Result 객체에 필드를 추가할 수 있다
    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }

    @Data
    @AllArgsConstructor
    static class MembersDto {
        private String nickName;
    }
}
