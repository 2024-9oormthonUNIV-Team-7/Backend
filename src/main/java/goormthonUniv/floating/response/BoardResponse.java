package goormthonUniv.floating.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class BoardResponse {
    private Long BoardId;
    private String title;
    private String content;
    private int likeCount;
    private LocalDateTime createDate;
}
