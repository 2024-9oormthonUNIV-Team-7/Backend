package goormthonUniv.floating.controller;

import goormthonUniv.floating.domain.Board;
import goormthonUniv.floating.response.BoardResponse;
import goormthonUniv.floating.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    //게시글 작성
    @PostMapping("/write")
    public ResponseEntity<Board> writeBoard(@RequestBody Board boardRequest) {
        Board board = boardService.writeBoard(boardRequest.getUserId(),boardRequest.getTitle(), boardRequest.getContent());
        return ResponseEntity.ok(board);
    }

    //모든 게시글 리스트 조회
    @GetMapping
    public ResponseEntity<List<BoardResponse>> getAllBoards() {
        List<BoardResponse> boards = boardService.getAllBoards();
        return ResponseEntity.ok(boards);
    }

    //특정 게시글 상세 조회
    @GetMapping("/{boardId}")
    public ResponseEntity<BoardResponse> getBoardById(@PathVariable Long boardId) {
        BoardResponse boardResponse = boardService.getBoardById(boardId);
        return ResponseEntity.ok(boardResponse);
    }

    //본인의 게시글 리스트 조회
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BoardResponse>> getUserBoards(@PathVariable Long userId) {
        List<Board> boards = boardService.getUserBoards(userId);
        List<BoardResponse> response = boards.stream()
                .map(board -> new BoardResponse(board.getBoardId(), board.getTitle(), board.getContent(), board.getLikeCount(), board.getCreateDate()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);

    }

    //게시글 좋아요 수 증가
    @PutMapping("/{boardId}/like")
    public ResponseEntity<Void> increaseLike(@PathVariable Long boardId) {
        boardService.increaseLike(boardId);
        return ResponseEntity.ok().build();
    }
}
