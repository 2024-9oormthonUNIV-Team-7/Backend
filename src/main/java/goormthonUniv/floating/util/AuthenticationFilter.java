package goormthonUniv.floating.util;

import goormthonUniv.floating.domain.Role;
import goormthonUniv.floating.domain.User;
import goormthonUniv.floating.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.util.Collections;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class AuthenticationFilter extends GenericFilterBean {

    // Token Util
    private JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();
    private final UserRepository userRepository;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {



        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String requestURI = httpRequest.getRequestURI();

        // 쿠키에서 JWT 토큰 추출
        String bearerToken = getJwtFromCookies(httpRequest);  // 쿠키에서 JWT 토큰 추출 메서드

        UsernamePasswordAuthenticationToken authentication;

        if (bearerToken != null && jwtTokenUtil.validateToken(bearerToken)) {
            // 유효한 토큰인 경우
            log.info("JWT Token is valid");

            // 토큰에서 User 정보 추출
            String googleId = jwtTokenUtil.getUserIdFromToken(bearerToken);

            // 데이터베이스에서 유저 조회
            User user = userRepository.findByGoogleId(googleId)
                    .orElseThrow(() -> new RuntimeException("JWT Token 인증 실패!"));

            log.info("user role key:" + user.getRoleKey());
            // 인증 객체 생성
            authentication = new UsernamePasswordAuthenticationToken(
                    user, null, Collections.singleton(new SimpleGrantedAuthority(Role.COMMON.getKey()))
            );


        } else {
            // JWT 토큰이 없거나, 유효하지 않은 경우 게스트 권한으로 처리
            authentication = new UsernamePasswordAuthenticationToken(UUID.randomUUID(), null,
                    Collections.singleton(new SimpleGrantedAuthority(Role.GUEST.getKey())));
        }

        // 인증 정보를 SecurityContext에 설정
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // SecurityContextHolder에 저장된 인증 객체 로그
        log.info("SecurityContextHolder Authentication: {}", SecurityContextHolder.getContext().getAuthentication());

        // 필터 체인을 통해 다음 필터 또는 리소스로 이동
        chain.doFilter(request, response);
    }

    /**
     * 쿠키에서 JWT 토큰을 추출하는 메서드
     */
    private String getJwtFromCookies(HttpServletRequest request) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("jwtToken".equals(cookie.getName())) {
                    return cookie.getValue();  // JWT 토큰 반환
                }
            }
        }
        return null;  // 쿠키에서 JWT 토큰이 없을 경우 null 반환
    }
}