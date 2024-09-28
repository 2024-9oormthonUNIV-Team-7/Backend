package goormthonUniv.floating.controller;

import exception.SeverErrorException;
import goormthonUniv.floating.domain.Category;
import goormthonUniv.floating.domain.TalkSubject;
import goormthonUniv.floating.repository.TalkSubjectRepository;
import goormthonUniv.floating.response.TalkSubjectResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TalkSubjectApiController {

    private final TalkSubjectRepository talkSubjectRepository;

    @GetMapping("/api/v1/talk_subject")
    public ResponseEntity<TalkSubjectResponse> getAllTalkSubjects() {

        List<TalkSubject> talkSubjects = talkSubjectRepository.findAll();
        if (talkSubjects.isEmpty()){
            throw new SeverErrorException("스몰 토크 리스트를 불러 올 수 없습니다.");
        }
        TalkSubjectResponse response = new TalkSubjectResponse(talkSubjects, 200, "스몰 토크 전체 조회 성공");
        return ResponseEntity.ok(response);

    }


}
