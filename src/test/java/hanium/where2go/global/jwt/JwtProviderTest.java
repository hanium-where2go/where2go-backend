package hanium.where2go.global.jwt;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class JwtProviderTest {

    @Autowired
    private JwtProvider jwtProvider;

    @Test
    @DisplayName("AccessToken 추출하기")
    public void resolveAccessToken() throws Exception {

        //given
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer " + jwtProvider.generateAccessTokenByEmail("test@test.com"));

        //when
        String accessToken = jwtProvider.resolveAccessToken(request);
        String email = jwtProvider.extractEmail(accessToken);

        //then
        assertThat(email).isEqualTo("test@test.com");
    }

}