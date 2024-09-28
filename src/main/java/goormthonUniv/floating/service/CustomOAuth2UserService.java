package goormthonUniv.floating.service;

import goormthonUniv.floating.domain.User;
import goormthonUniv.floating.repository.UserRepository;
import goormthonUniv.floating.response.OAuthAttributes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;

@RequiredArgsConstructor
@Service
@Slf4j
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        // 구글 로그인만 사용하므로 구글에 대한 설정만 남김
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        // 구글 로그인 정보를 통합 객체로 만들어준다.
        OAuthAttributes attributes = OAuthAttributes.ofGoogle(userNameAttributeName, oAuth2User.getAttributes(), "google");

        User user = saveOrUpdate(attributes);

        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
    }

    /**
     * User 조회 후 저장 및 세팅
     * @param attributes
     * @return
     */
    private User saveOrUpdate(OAuthAttributes attributes) {
        // User 객체 조회 및 세팅
        User user = userRepository.findByGoogleId(attributes.getAttributeId())
                .map(entity -> entity.updateNameAndEmail(attributes.getName(), attributes.getEmail()))
                .orElseGet(() -> User.toEntity(attributes.getAttributeId(), attributes.getEmail(), attributes.getName(), attributes.getRole()));

        return userRepository.save(user);
    }
}
