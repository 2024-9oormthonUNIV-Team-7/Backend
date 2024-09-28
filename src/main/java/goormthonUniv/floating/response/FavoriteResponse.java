package goormthonUniv.floating.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FavoriteResponse {

    private Long id;
    private String category;
    private Long itemId;

    // mini_game의 경우
    private String name;
    private String description;

    // talk_subject의 경우
    private String subject;
    private String talkDescription;

    // balance_game의 경우
    private String questionA;
    private String questionB;

    public FavoriteResponse(Long id, String category, Long itemId) {
        this.id = id;
        this.category = category;
        this.itemId = itemId;
    }
}
