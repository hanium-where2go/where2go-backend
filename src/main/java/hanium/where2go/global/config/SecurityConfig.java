package hanium.where2go.global.config;

import hanium.where2go.domain.user.entity.Role;
import hanium.where2go.global.handler.AccessDeniedHandlerImpl;
import hanium.where2go.global.jwt.JwtAuthenticationEntryPoint;
import hanium.where2go.global.jwt.JwtFilter;
import hanium.where2go.global.jwt.JwtProvider;
import hanium.where2go.global.oauth2.handler.OAuth2LoginFailureHandler;
import hanium.where2go.global.oauth2.handler.OAuth2LoginSuccessHandler;
import hanium.where2go.global.oauth2.service.CustomOAuth2Service;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtProvider jwtProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final AccessDeniedHandlerImpl accessDeniedHandler;
    private final CustomOAuth2Service customOAuth2Service;
    private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;
    private final OAuth2LoginFailureHandler oAuth2LoginFailureHandler;

    private final String[] WHITE_LIST = {
        "/customer/signup",
        "/customer/login",
        "/customer/find-email",
        "/customer/email-verification/*",
        "/maps/**",
        "/",
        "**.html",
        "/customer/reissue"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        //Spring Security 기본 설정
        http
            .httpBasic(httpSecurityHttpBasicConfigurer -> httpSecurityHttpBasicConfigurer.disable())
            .csrf(csrfConfigurer -> csrfConfigurer.disable())
            .cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.disable())
            .formLogin(httpSecurityFormLoginConfigurer -> httpSecurityFormLoginConfigurer.disable())
            .sessionManagement(sessionManagementConfigurer -> sessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        //WHITE LIST 만 허용, 나머지는 로그인 필요
        http
            .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests.requestMatchers(WHITE_LIST).permitAll())
//            .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests.anyRequest().authenticated())
            .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests.anyRequest().permitAll())
//            Unauthenticated | Unauthorized user exception
            .exceptionHandling(exceptionHandling ->
                exceptionHandling
                    .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                    .accessDeniedHandler(accessDeniedHandler));
        http
            .oauth2Login(configurer ->
                configurer.successHandler(oAuth2LoginSuccessHandler)
                    .failureHandler(oAuth2LoginFailureHandler)
                    .userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig.userService(customOAuth2Service)));

        //JwtFilter 적용
        http.
            addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public JwtFilter jwtFilter() {
        return new JwtFilter(jwtProvider);
    }
}
