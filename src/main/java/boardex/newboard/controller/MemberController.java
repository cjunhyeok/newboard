package boardex.newboard.controller;

import boardex.newboard.SessionConst;
import boardex.newboard.domain.Member;
import boardex.newboard.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
@Slf4j
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/members/new")
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());

        log.info("createForm");
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(@Valid MemberForm form, BindingResult result) {

        if (result.hasErrors()) {
            return "members/createMemberForm";
        }

        Member member = new Member(form.getUserId(), form.getUserPassword(), form.getNickName(),
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

    @GetMapping("/login")
    public String loginForm(@ModelAttribute("loginForm") LoginForm form) {
        return "login/loginForm";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("loginForm") LoginForm form, BindingResult result, HttpServletRequest request) {

        if (result.hasErrors()) {
            return "login/loginForm";
        }

        Member loginMember = memberService.login(form.getUserId(), form.getUserPassword());

        if (loginMember == null) {
            result.reject("loginFail", "????????? ?????? ??????????????? ?????? ????????????.");
            return "login/loginForm";
        }

        // ????????? ?????? ??????
        // ????????? ????????? ?????? ?????? ??????, ????????? ?????? ?????? ??????
        HttpSession session = request.getSession();
        // ????????? ????????? ?????? ?????? ??????
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);

        return "redirect:/";
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }
}
