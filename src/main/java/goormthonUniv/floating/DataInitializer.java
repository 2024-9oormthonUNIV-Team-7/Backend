package goormthonUniv.floating;

import goormthonUniv.floating.domain.*;
import goormthonUniv.floating.repository.FavoriteRepository;
import goormthonUniv.floating.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final FavoriteRepository favoriteRepository;

    // 생성자 주입을 사용하여 리포지토리 주입
    public DataInitializer(UserRepository userRepository, FavoriteRepository favoriteRepository) {
        this.userRepository = userRepository;
        this.favoriteRepository = favoriteRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // User 더미 데이터 생성
        User user1 = new User();
        user1.setGoogleId("googleId_1");
        user1.setEmail("john@example.com");
        user1.setRole(Role.GUEST);  // Role 설정
        user1.setName("John Doe");

        User user2 = new User();
        user2.setGoogleId("googleId_2");
        user2.setEmail("jane@example.com");
        user2.setRole(Role.GUEST);  // Role 설정
        user2.setName("Jane Smith");

        // 사용자 저장
        userRepository.saveAll(List.of(user1, user2));

        // Favorite 더미 데이터 생성
        Favorite favorite1 = new Favorite();
        favorite1.setUser(user1);
        favorite1.setCategory(FavoriteCategory.balance_game);
        favorite1.setItemId(1L);
        favorite1.setCreateTime(LocalDateTime.now());

        Favorite favorite2 = new Favorite();
        favorite2.setUser(user1);
        favorite2.setCategory(FavoriteCategory.mini_game);
        favorite2.setItemId(2L);
        favorite2.setCreateTime(LocalDateTime.now());

        Favorite favorite3 = new Favorite();
        favorite3.setUser(user1);
        favorite3.setCategory(FavoriteCategory.mini_game);
        favorite3.setItemId(3L);
        favorite3.setCreateTime(LocalDateTime.now());

        Favorite favorite4 = new Favorite();
        favorite4.setUser(user1);
        favorite4.setCategory(FavoriteCategory.talk_subject);
        favorite4.setItemId(5L);
        favorite4.setCreateTime(LocalDateTime.now());

        // 즐겨찾기 데이터 저장
        favoriteRepository.saveAll(List.of(favorite1, favorite2,favorite3,favorite4));

        System.out.println("더미 데이터 삽입 완료");
    }
}
