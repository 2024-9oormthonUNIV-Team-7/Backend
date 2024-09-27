package goormthonUniv.floating.response;

import goormthonUniv.floating.domain.MiniGame;
import goormthonUniv.floating.domain.TalkSubject;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MiniGameResponse {

    private List<MiniGame> minigames;
    private int status;
    private String message;


    public MiniGameResponse(List<MiniGame> minigames, int status, String message) {
        this.minigames = minigames;
        this.status = status;
        this.message = message;
    }
}
