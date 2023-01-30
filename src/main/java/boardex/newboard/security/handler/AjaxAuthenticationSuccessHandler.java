package boardex.newboard.security.handler;

import boardex.newboard.domain.Member;
import boardex.newboard.security.MemberDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AjaxAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // authentication : 인증 성공 핸들러 이므로 인증에 성공한 객체 정보

        Member member = (Member) authentication.getPrincipal(); // 인증에 최종 성공한 member 객체
        MemberDto memberDto = new MemberDto();
        memberDto.setUserId(member.getUserId());
        memberDto.setNickName(member.getNickName());
        memberDto.setRole(member.getRole());
        memberDto.setName(member.getName());

        response.setStatus(HttpStatus.OK.value());
        response.setContentType(String.valueOf(MediaType.APPLICATION_JSON));

        objectMapper.writeValue(response.getWriter(), memberDto);
    }
}
