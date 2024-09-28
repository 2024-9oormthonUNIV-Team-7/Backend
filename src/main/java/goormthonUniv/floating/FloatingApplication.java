package goormthonUniv.floating;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FloatingApplication {

	public static void main(String[] args) {
		// .env 파일 로드
		Dotenv dotenv = Dotenv.load();

		// 환경 변수 읽기
		String dbUrl = dotenv.get("DB_URL");
		String dbUsername = dotenv.get("DB_USERNAME");
		String dbPassword = dotenv.get("DB_PASSWORD");
		String oauthClientId = dotenv.get("OAUTH_CLIENT_ID");
		String oauthClientSecret = dotenv.get("OAUTH_CLIENT_SECRET");
		String baseUrl = dotenv.get("BASE_URL");
		String jwtCookieSecure = dotenv.get("JWT_COOKIE_SECURE");

		// 환경 변수를 시스템에 설정 (필요한 경우)
		System.setProperty("DB_URL", dbUrl);
		System.setProperty("DB_USERNAME", dbUsername);
		System.setProperty("DB_PASSWORD", dbPassword);
		System.setProperty("OAUTH_CLIENT_ID", oauthClientId);
		System.setProperty("OAUTH_CLIENT_SECRET", oauthClientSecret);
		System.setProperty("BASE_URL", baseUrl);
		System.setProperty("JWT_COOKIE_SECURE", jwtCookieSecure);

		SpringApplication.run(FloatingApplication.class, args);

	}

}
