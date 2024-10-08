package goormthonUniv.floating.repository;

import goormthonUniv.floating.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findByUserId(Long userId);
}
