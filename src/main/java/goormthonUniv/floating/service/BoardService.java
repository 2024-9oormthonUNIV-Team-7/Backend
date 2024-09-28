package goormthonUniv.floating.service;

import goormthonUniv.floating.domain.Board;
import goormthonUniv.floating.repository.BoardRepository;
import goormthonUniv.floating.response.BoardResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    @Transactional
    public Board writeBoard(Long userId,String title, String content) {
        Board board = new Board();
        board.setUserId(userId);
        board.setTitle(title);
        board.setContent(content);

        return boardRepository.save(board);
    }

    @Transactional //모두를 조회하는 복잡한 잡업
    public List<BoardResponse> getAllBoards() {
        return boardRepository.findAll().stream()
                .map(board -> new BoardResponse(board.getBoardId(),
                                                board.getTitle(),
                                                board.getContent(),
                                                board.getLikeCount(),
                                                board.getCreateDate()))
                .collect(Collectors.toList());
    }

    @Transactional //예외처리 작업 포함
    public BoardResponse getBoardById(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("Board not found with id: " + boardId));
        return new BoardResponse(board.getBoardId(),
                                board.getTitle(),
                                board.getContent(),
                                board.getLikeCount(),
                                board.getCreateDate());
    }

    //단순 조회 작업이기에 @Transactional 아직 필요X
    public List<Board> getUserBoards(Long userId) {
        return boardRepository.findByUserId(userId);
    }

    @Transactional //데이터 값 변화
    public void increaseLike(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("Board not found with id: " + boardId));
        board.setLikeCount(board.getLikeCount() + 1);
    }
}
