package goormthonUniv.floating.response;

import goormthonUniv.floating.domain.BalanceGame;
import goormthonUniv.floating.domain.MiniGame;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BalanceGameResponse {

    private List<BalanceGame> balanceGame;
    private int status;
    private String message;


    public BalanceGameResponse(List<BalanceGame> balanceGames, int status, String message) {
        this.balanceGame = balanceGames;
        this.status = status;
        this.message = message;
    }

}
