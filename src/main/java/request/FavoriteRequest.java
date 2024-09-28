package request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FavoriteRequest {

    private String email;
    private String category;
    private Long itemId;

}
