package goormthonUniv.floating.response;

import goormthonUniv.floating.domain.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Getter
@ToString
@Slf4j
public class OAuthAttributes {
    private Map<String, Object> attributes;     // Attributes 원본
    private String nameAttributeKey;            // attribute key : google(sub)
    private String attributeId;                 // 고유 id
    private String name;
    private String email;
    private String picture;
    private String registrationId;              // 간편로그인 서비스 구분 (ex : google)
    private Role role;                          // 권한 값

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey,
                           String attributeId, String name, String email, String picture,
                           String registrationId, Role role) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.attributeId = attributeId;
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.registrationId = registrationId;
        this.role = role != null ? role : Role.COMMON;
    }

    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        // Google 로그인에 대한 객체 세팅
        return ofGoogle(userNameAttributeName, attributes, registrationId);
    }

    public static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes, String registrationId) {
        return OAuthAttributes.builder()
                .attributeId(String.valueOf(attributes.get(userNameAttributeName)))
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .picture((String) attributes.get("picture"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .registrationId(registrationId)
                .build();
    }
}