package goormthonUniv.floating.repository;

import goormthonUniv.floating.domain.BalanceGame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BalanceGameRepository extends JpaRepository<BalanceGame, Long> {

    List<BalanceGame> findAll();
}
