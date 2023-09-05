package hanium.where2go.global.oauth2;

import hanium.where2go.domain.customer.entity.Customer;
import hanium.where2go.domain.user.entity.Role;
import hanium.where2go.global.oauth2.userinfo.GoogleOAuth2UserInfo;
import hanium.where2go.global.oauth2.userinfo.KakaoOAuth2UserInfo;
import hanium.where2go.global.oauth2.userinfo.OAuth2UserInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.User;

import java.util.Map;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OAuthAttributes {

    private String nameAttributeKey;
    private OAuth2UserInfo oAuth2UserInfo;

    public static OAuthAttributes of(SocialType socialType, String userNameAttributeName, Map<String, Object> attributes) {
        if (socialType == SocialType.KAKAO) {
            return ofKakao(userNameAttributeName, attributes);
        }
        return ofGoogle(userNameAttributeName, attributes);
    }

    private static OAuthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
            .nameAttributeKey(userNameAttributeName)
            .oAuth2UserInfo(new KakaoOAuth2UserInfo(attributes))
            .build();
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
            .nameAttributeKey(userNameAttributeName)
            .oAuth2UserInfo(new GoogleOAuth2UserInfo(attributes))
            .build();
    }

    public Customer toEntity(SocialType socialType, OAuth2UserInfo oAuth2UserInfo) {
        return Customer.builder()
            .socialType(socialType)
            .socialId(oAuth2UserInfo.getId())
            .email(UUID.randomUUID() + "@socialUser.com")
            .password(UUID.randomUUID().toString())
            .nickname(oAuth2UserInfo.getNickname())
            .role(Role.GUEST)
            .build();
    }

}

