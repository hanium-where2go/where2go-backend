package hanium.where2go.global.oauth2.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import hanium.where2go.domain.customer.dto.CustomerLoginResponseDto;
import hanium.where2go.domain.customer.repository.CustomerRepository;
import hanium.where2go.domain.user.entity.Role;
import hanium.where2go.global.jwt.JwtProvider;
import hanium.where2go.global.oauth2.CustomOAuth2User;
import hanium.where2go.global.response.BaseResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtProvider jwtProvider;
    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
        String accessToken = jwtProvider.generateAccessTokenByEmail(oAuth2User.getEmail());
        String refreshToken = jwtProvider.generateRefreshToken(oAuth2User.getEmail());

        response.setCharacterEncoding("UTF-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        if (oAuth2User.getRole() == Role.GUEST) {
            //추가 정보 입력페이지로 redirect 필요
            response.setStatus(HttpStatus.TEMPORARY_REDIRECT.value());
            objectMapper.writeValue(response.getWriter(), new BaseResponse<CustomerLoginResponseDto>(HttpStatus.TEMPORARY_REDIRECT.value(), "oauth2 회원가입 페이지로 이동합니다.", new CustomerLoginResponseDto(accessToken, refreshToken)));
        } else {
            response.setStatus(HttpStatus.OK.value());
            objectMapper.writeValue(response.getWriter(), new BaseResponse<CustomerLoginResponseDto>(HttpStatus.TEMPORARY_REDIRECT.value(), "로그인에 성공하였습니다.", new CustomerLoginResponseDto(accessToken, refreshToken)));
        }
    }
}
