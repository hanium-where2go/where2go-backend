package hanium.where2go.global.jwt;

import hanium.where2go.domain.user.service.UserDetailServiceImpl;
import hanium.where2go.global.redis.RedisUtil;
import hanium.where2go.global.response.BaseException;
import hanium.where2go.global.response.ExceptionCode;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Slf4j
@Component
public class JwtProvider {

    private final Key secretKey;
    private final Long accessTokenExpireTime;
    private final Long refreshTokenExpireTime;
    private final UserDetailServiceImpl userDetailService;
    private final RedisUtil redisUtil;

    public JwtProvider(@Value("${jwt.secretKey}") String secretKey,
                       @Value("${jwt.access.expire-time}") Long accessTokenExpireTime,
                       @Value("${jwt.refresh.expire-time}") Long refreshTokenExpireTime,
                       UserDetailServiceImpl userDetailService,
                       RedisUtil redisUtil) {
        this.secretKey = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        this.accessTokenExpireTime = accessTokenExpireTime;
        this.refreshTokenExpireTime = refreshTokenExpireTime;
        this.userDetailService = userDetailService;
        this.redisUtil = redisUtil;
    }

    //이메일을 바탕으로 AccessToken 생성
    public String generateAccessTokenByEmail(String email) {
        Date now = new Date();

        return Jwts.builder()
            .setSubject("AccessToken")
            .claim("email", email)
            .setExpiration(new Date(now.getTime() + accessTokenExpireTime))
            .signWith(secretKey, SignatureAlgorithm.HS256)
            .compact();
    }

    //RefreshToken 생성
    public String generateRefreshToken(String email) {
        Date now = new Date();

        String refreshToken = Jwts.builder()
            .setSubject("RefreshToken")
            .claim("email", email)
            .setExpiration(new Date(now.getTime() + refreshTokenExpireTime))
            .signWith(secretKey, SignatureAlgorithm.HS256)
            .compact();

        redisUtil.set(email, refreshToken, refreshTokenExpireTime);
        return refreshToken;
    }

    //토큰 검증 메소드
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            return true;

        } catch (MalformedJwtException | IllegalArgumentException | UnsupportedJwtException e) {
            throw new BaseException(ExceptionCode.INVALID_TOKEN);
        } catch (ExpiredJwtException e) {
            throw new BaseException(ExceptionCode.EXPIRED_TOKEN);
        }
    }

    //토큰 만료 검증 확인 메서드
    public boolean expiredToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            throw new BaseException(ExceptionCode.EXPIRED_TOKEN);
        }
    }


    //Access Token 추출
    public String resolveAccessToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    //RefreshToken Token 추출
    public String resolveRefreshToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization-Refresh");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    //토큰으로 부터 이메일 추출
    public String extractEmail(String accessToken) {
        Claims body = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(accessToken).getBody();
        return body.get("email", String.class);
    }

    //Access token으로 부터 Authentication 객체 생성
    public Authentication getAuthenticationByAccessToken(String accessToken) {
        String email = extractEmail(accessToken);
        UserDetails userDetails = userDetailService.loadUserByUsername(email);

        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }
}
