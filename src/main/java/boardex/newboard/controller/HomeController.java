package boardex.newboard.controller;

import boardex.newboard.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@Slf4j
public class HomeController {

    //public Stirng home(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member, Model model)
//    @RequestMapping("/")
//    public String home(HttpServletRequest request, Model model) {
//
//        HttpSession session = request.getSession(false);
//        if (session == null) {
//            return "home";
//        }
//
//        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
//
//        // 세션에 회원 데이터가 없으면 home
//        if (loginMember == null) {
//            return "home";
//        }
//
//        model.addAttribute("member", loginMember);
//        log.info("member.nickName = {}", loginMember.getNickName());
//        return "loginHome";
//    }

    @RequestMapping("/")
    public String home(HttpServletRequest request, Model model) {

//        Object login = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        if (login.equals("anonymousUser")) {
//            return "home";
//        } else {
//            log.info("login {}", login);
//            Member member = (Member) login;
//            model.addAttribute("member", member);
//            return "loginHome";
//        }
        return "home";
    }

//    @ResponseBody
//    @PostMapping("/login")
//    public String test() {
//        log.info("loginurl");
//        return "ok";
//    }
}
