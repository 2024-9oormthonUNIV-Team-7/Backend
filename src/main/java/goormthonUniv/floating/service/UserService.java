package goormthonUniv.floating.service;

import goormthonUniv.floating.domain.User;
import goormthonUniv.floating.repository.UserRepository;
import goormthonUniv.floating.response.UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserResponse getUserInfo(Authentication authentication) {
        //인증된 사용자가 User 객체인 경우에만 처리
        if (authentication.getPrincipal() instanceof User) {
            User user = (User) authentication.getPrincipal();
            return new UserResponse(user.getName(), user.getEmail());
        }
        throw new RuntimeException("User not found or not authenticated");
    }

}
