package goormthonUniv.floating.controller;

import goormthonUniv.floating.domain.BalanceGame;
import goormthonUniv.floating.domain.MiniGame;
import goormthonUniv.floating.repository.BalanceGameRepository;
import goormthonUniv.floating.response.BalanceGameResponse;
import goormthonUniv.floating.response.MiniGameResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BalanceGameController {

    private final BalanceGameRepository balanceGameRepository;

    @GetMapping("/api/v1/balance-game")
    public ResponseEntity<BalanceGameResponse> getAllBalanceGames(){
        BalanceGame balanceGame = new BalanceGame();
        balanceGameRepository.save(balanceGame);
        List<BalanceGame> balanceGames = balanceGameRepository.findAll();

        BalanceGameResponse response = new BalanceGameResponse(balanceGames, 200, "밸런스 게임 전체 조회 성공");
        return ResponseEntity.ok(response);

    }
}
