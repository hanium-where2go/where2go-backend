package hanium.where2go.global.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import hanium.where2go.global.response.BaseErrorResponse;
import hanium.where2go.global.response.BaseException;
import hanium.where2go.global.response.ExceptionCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        setResponse(response, new BaseException(ExceptionCode.UNAUTHORIZED_USER));
    }

    private void setResponse(HttpServletResponse response, BaseException e) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(e.getStatus());

        response.getWriter().write(objectMapper.writeValueAsString(new BaseErrorResponse(e)));
    }
}
