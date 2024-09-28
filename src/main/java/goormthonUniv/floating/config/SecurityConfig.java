package goormthonUniv.floating.config;

import goormthonUniv.floating.domain.Role;
import goormthonUniv.floating.handler.OAuth2AuthenticationFailureHandler;
import goormthonUniv.floating.handler.OAuth2AuthenticationSuccessHandler;
import goormthonUniv.floating.service.CustomOAuth2UserService;
import goormthonUniv.floating.util.AuthenticationFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final AuthenticationFilter authenticationFilter;
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    private final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(withDefaults())  // CORS 설정 활성화
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        .requestMatchers("/", "/error").permitAll()
                        .requestMatchers("/login/**").permitAll()
                        .requestMatchers("/users/**").permitAll()
                        .requestMatchers("/api/**").hasAnyAuthority(Role.COMMON.getKey(), Role.GUEST.getKey()))
                .sessionManagement(session -> session
                        // 세션을 사용하지 않겠다는 의
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http
                .securityContext(AbstractHttpConfigurer::disable)   // 기본 로그인 폼 비활성화
                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(customOAuth2UserService))
                        .successHandler(oAuth2AuthenticationSuccessHandler)
                        .failureHandler(oAuth2AuthenticationFailureHandler)
                );
        http
                .csrf(AbstractHttpConfigurer::disable);

        // 필터 적용
        http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);


        return http.build();
    }
}