package goormthonUniv.floating.service;

import goormthonUniv.floating.domain.TalkSubject;
import goormthonUniv.floating.repository.TalkSubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TalkSubjectService {

    private final TalkSubjectRepository talkSubjectRepository;

    @Transactional  // insert into TalkSubject (~~~~) values (~~~~~~~)
    public void save(TalkSubject talkSubject){
        talkSubjectRepository.save(talkSubject);
    }

}
