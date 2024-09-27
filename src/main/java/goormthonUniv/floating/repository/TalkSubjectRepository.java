package goormthonUniv.floating.repository;

import goormthonUniv.floating.domain.TalkSubject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TalkSubjectRepository extends JpaRepository<TalkSubject, Long> {
    List<TalkSubject> findAll();
}
