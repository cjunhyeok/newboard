package boardex.newboard.controller;

import boardex.newboard.controller.form.MemberForm;
import boardex.newboard.domain.Member;
import boardex.newboard.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@Slf4j
public class MemberController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public MemberController(MemberService memberService, PasswordEncoder passwordEncoder) {
        this.memberService = memberService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/members/new")
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());

        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(@Valid MemberForm form, BindingResult result) {

        if (result.hasErrors()) {
            return "members/createMemberForm";
        }

        Member member = new Member(form.getUserId(), passwordEncoder.encode(form.getUserPassword()), form.getNickName(),
                form.getName(), form.getAddress(), form.getBirthday());

        memberService.join(member);

        return "redirect:/";
    }

    @GetMapping("/members")
    public String listMember(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);

        return "members/memberList";
    }

    @GetMapping("/members/{id}")
    public String member(@PathVariable(name = "id") Long id, Model model) {

        boolean login = false;

        Member principal = (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long loginId = principal.getId();

        Member findMember = memberService.findById(id);
        MemberDto memberDto = new MemberDto(findMember.getNickName(),
                                            findMember.getName(),
                                            findMember.getAddress(),
                                            findMember.getBirthday());

        if (loginId.equals(id)) {
            login = true;
            model.addAttribute("login", login);
            log.info("loginMember");
        }

        model.addAttribute("member", memberDto);

        return "members/member";


    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class MemberDto {

        private String nickName;

        private String name;
        private String address;
        private String birthday;
    }
}


