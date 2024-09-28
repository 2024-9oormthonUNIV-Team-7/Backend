package goormthonUniv.floating.handler;

import goormthonUniv.floating.util.JwtTokenUtil;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtTokenUtil jwtTokenUtil;
    private final Dotenv dotenv = Dotenv.configure().load();
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        // Get the access token and refresh token from the authorized client
        log.info("OAuth2 로그인에 성공하였습니다.");

        // 토큰 발행
        String token = jwtTokenUtil.generateToken(authentication);

        // JWT 토큰을 HttpOnly 쿠키로 설정
        Cookie jwtCookie = new Cookie("jwtToken", token);
        jwtCookie.setHttpOnly(true);  // 브라우저 자바스크립트에서 접근 불가
        jwtCookie.setSecure(true);    // HTTPS 환경에서만 전송 (배포 시)
        jwtCookie.setPath("/");       // 쿠키의 경로 설정
        jwtCookie.setMaxAge(60 * 60 * 24);  // 쿠키 유효 기간 설정 (예: 24시간)
        jwtCookie.setSecure(false);
        response.addCookie(jwtCookie);
        // SameSite=None; 설정 추가
        response.setHeader("Set-Cookie", String.format("jwtToken=%s; Max-Age=%d; Path=/; Secure; HttpOnly; SameSite=None", token, 60 * 60 * 24));
        String jwtCookieDomain = dotenv.get("JWT_COOKIE_DOMAIN");
        jwtCookie.setDomain(jwtCookieDomain);

        // .env 파일에서 리다이렉트 URL 가져오기
        String redirectUrl = dotenv.get("REDIRECT_URL");
        response.sendRedirect(redirectUrl);

        super.onAuthenticationSuccess(request, response, authentication);
    }
}