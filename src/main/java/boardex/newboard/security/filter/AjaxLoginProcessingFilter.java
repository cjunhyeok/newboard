package boardex.newboard.security.filter;

import boardex.newboard.security.MemberDto;
import boardex.newboard.security.token.AjaxAuthenticationToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AjaxLoginProcessingFilter extends AbstractAuthenticationProcessingFilter {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public AjaxLoginProcessingFilter() {
        super(new AntPathRequestMatcher("/api/login")); // 해당 url로 접근하면 필터 작동
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {

        if (!isJson(request)) {
            throw new IllegalStateException("Authentication is not supported");
        }

        MemberDto memberDto = objectMapper.readValue(request.getReader(), MemberDto.class);

        if (!StringUtils.hasText(memberDto.getUserId()) || !StringUtils.hasText(memberDto.getUserPassword())) {
            throw new IllegalArgumentException("Id or Password is empty");
        }

        AjaxAuthenticationToken ajaxAuthenticationToken = new AjaxAuthenticationToken(memberDto.getUserId(), memberDto.getUserPassword());
        return getAuthenticationManager().authenticate(ajaxAuthenticationToken);
    }

    private boolean isJson(HttpServletRequest request) {

        if (request.getContentType().equals("application/json")) {
            return true;
        } else {
            return false;
        }
    }
}
