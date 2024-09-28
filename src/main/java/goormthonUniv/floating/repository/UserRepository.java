package goormthonUniv.floating.repository;

import goormthonUniv.floating.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User>findBygoogleid(String googleId);
    Optional<User>findByEmail(String email);
}
