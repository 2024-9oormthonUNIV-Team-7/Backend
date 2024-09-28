package goormthonUniv.floating.util;

import io.github.cdimascio.dotenv.Dotenv;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import lombok.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenUtil {

    private final Dotenv dotenv = Dotenv.load();  // Dotenv를 사용하여 .env 파일 로드

    // 환경 변수에서 secret과 expiration을 가져옴
    private String secret = dotenv.get("JWT_SECRET", "defaultSecret");
    private long expiration = Long.parseLong(dotenv.get("JWT_EXPIRATION", "1800000"));


    // 서명 키 생성
    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // JWT 토큰 생성
    public String generateToken(Authentication authentication) {
        Key key = getSigningKey();

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);

        // 구글 로그인 관련 정보를 가져옴
        String clientRegistrationId = getRegistrationIdFromAuthentication(authentication);

        return Jwts.builder()
                .setSubject(authentication.getName())  // 사용자 이름을 Subject로 설정
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .claim("registration_id", clientRegistrationId)  // 구글 로그인 서비스 구분 추가
                .signWith(key, SignatureAlgorithm.HS512)  // 서명 알고리즘 사용
                .compact();
    }

    // JWT 토큰으로부터 사용자 ID 추출
    public String getUserIdFromToken(String token) {
        Key key = getSigningKey();
        Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
        return claims.getBody().getSubject();
    }

    // JWT 토큰으로부터 registrationId 추출 (구글)
    public String getRegistrationIdFromToken(String token) {
        Key key = getSigningKey();
        Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
        return (String) claims.getBody().get("registration_id");
    }

    // JWT 토큰 유효성 검사
    public boolean validateToken(String token) {
        try {
            Key key = getSigningKey();
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    // RegistrationId(google) 를 얻기 위한 메서드
    private String getRegistrationIdFromAuthentication(Authentication authentication) {
        if (authentication instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
            return oauthToken.getAuthorizedClientRegistrationId();
        }
        return null;
    }
}