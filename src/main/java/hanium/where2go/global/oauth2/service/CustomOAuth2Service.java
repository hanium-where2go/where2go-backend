package hanium.where2go.global.oauth2.service;

import hanium.where2go.domain.customer.entity.Customer;
import hanium.where2go.domain.customer.repository.CustomerRepository;
import hanium.where2go.global.oauth2.CustomOAuth2User;
import hanium.where2go.global.oauth2.OAuthAttributes;
import hanium.where2go.global.oauth2.SocialType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;


@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2Service implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final CustomerRepository customerRepository;
    private static final String KAKAO = "kakao";

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        SocialType socialType = getSocialType(registrationId);
        String userNameAttributeName = userRequest.getClientRegistration()
            .getProviderDetails()
            .getUserInfoEndpoint()
            .getUserNameAttributeName();

        Map<String, Object> attributes = oAuth2User.getAttributes();

        OAuthAttributes extractAttributes = OAuthAttributes.of(socialType, userNameAttributeName, attributes);

        Customer customer = getUser(extractAttributes, socialType);

        return new CustomOAuth2User(
            Collections.singleton(new SimpleGrantedAuthority(customer.getRole().name())),
            attributes,
            extractAttributes.getNameAttributeKey(),
            customer.getEmail(),
            customer.getRole()
        );
    }

    private SocialType getSocialType(String registrationId) {
        if (KAKAO.equals(registrationId)) {
            return SocialType.KAKAO;
        }
        return SocialType.GOOGLE;
    }

    private Customer getUser(OAuthAttributes attributes, SocialType socialType) {
        return customerRepository.findBySocialTypeAndSocialId(socialType, attributes.getOAuth2UserInfo().getId())
            .orElseGet(() -> saveUser(attributes, socialType));
    }

    private Customer saveUser(OAuthAttributes attributes, SocialType socialType) {
        Customer customer = attributes.toEntity(socialType, attributes.getOAuth2UserInfo());
        return customerRepository.save(customer);
    }


}
