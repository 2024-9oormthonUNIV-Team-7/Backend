package goormthonUniv.floating.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:3000", "http://floating.site", "http://floating.site:3000","https://floating.site")  // 허용할 도메인
                        .allowedMethods("*")
                        .allowedHeaders("*")
                        .exposedHeaders("Authorization")  // Authorization 헤더 노출 허용
                        .allowCredentials(true);  // 자격 증명 허용
            }
        };
    }
}