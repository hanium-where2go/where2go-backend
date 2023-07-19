package hanium.where2go.global.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import hanium.where2go.global.response.BaseErrorResponse;
import hanium.where2go.global.response.BaseException;
import hanium.where2go.global.response.ExceptionCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
        BaseException exception = new BaseException(ExceptionCode.UNAUTHENTICATED_USER);
        setResponse(response, exception);

    }

    private void setResponse(HttpServletResponse response, BaseException e) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();

        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.setStatus(e.getStatus());

        response.getWriter().write(objectMapper.writeValueAsString(new BaseErrorResponse(e)));

    }
}
