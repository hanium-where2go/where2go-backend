package hanium.where2go.global.jwt;

import hanium.where2go.global.response.BaseException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
//Authentication Error 처리
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        BaseException exception = new BaseException(HttpStatus.UNAUTHORIZED.value(), "Unauthorized");
        setResponse(response, exception);

    }

    private void setResponse(HttpServletResponse response, BaseException e) throws IOException {

        //에러 응답을 json으로 변환.
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(e.getStatus());
        response.getWriter().println("{ \"status\" : \"" + e.getStatus()
            + "\", \"message\" : \"" +  e.getMessage()
            + "\"}");
    }
}
