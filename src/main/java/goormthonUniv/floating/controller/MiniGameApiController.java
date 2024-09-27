package goormthonUniv.floating.controller;

import goormthonUniv.floating.domain.MiniGame;
import goormthonUniv.floating.domain.TalkSubject;
import goormthonUniv.floating.repository.MiniGameRepository;
import goormthonUniv.floating.response.MiniGameResponse;
import goormthonUniv.floating.response.TalkSubjectResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MiniGameApiController {

    private final MiniGameRepository miniGameRepository;

    @GetMapping("/api/v1/mini-game")
    public ResponseEntity<MiniGameResponse> getAllMiniGames(){
        MiniGame miniGame = new MiniGame();
        miniGameRepository.save(miniGame);
        List<MiniGame> miniGames = miniGameRepository.findAll();

        MiniGameResponse response = new MiniGameResponse(miniGames, 200, "스몰 토크 전체 조회 성공");
        return ResponseEntity.ok(response);

    }

}
