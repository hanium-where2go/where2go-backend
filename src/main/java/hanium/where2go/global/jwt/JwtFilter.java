package hanium.where2go.global.jwt;

import hanium.where2go.global.response.BaseException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = jwtProvider.resolveAccessToken(request);
        try {
            //토큰 validation
            if (token != null && jwtProvider.validateToken(token)) {
                //uri가 reissue가 아닌 경우에만 설정.
                if (!request.getRequestURI().equals("/customer/reissue")) {
                    Authentication authentication = jwtProvider.getAuthenticationByAccessToken(token);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }

        } catch (BaseException e) {
            SecurityContextHolder.clearContext();
            request.setAttribute("error", e);
        }
        filterChain.doFilter(request, response);
    }
}
