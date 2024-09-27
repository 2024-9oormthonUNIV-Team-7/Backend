package goormthonUniv.floating.repository;

import goormthonUniv.floating.domain.MiniGame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MiniGameRepository extends JpaRepository<MiniGame, Long> {

    List<MiniGame> findAll();

}
