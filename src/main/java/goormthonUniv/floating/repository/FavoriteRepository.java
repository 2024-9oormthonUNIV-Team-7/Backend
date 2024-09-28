package goormthonUniv.floating.repository;

import goormthonUniv.floating.domain.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    List<Favorite> findByUserId(Long userId);
    Optional<Favorite> findByUserIdAndItemId(Long userId, Long itemid);

    List<Favorite> findByUserIdOrderByCreateTimeDesc(Long userId);
}
