package goormthonUniv.floating.service;

import exception.FavoriteNotFoundException;
import exception.UserNotFoundException;
import goormthonUniv.floating.domain.*;
import goormthonUniv.floating.repository.*;
import goormthonUniv.floating.response.FavoriteResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;


    @Transactional
    public void addFavorite(String email, String category, Long itemId){
        Optional<User> user = userRepository.findByEmail(email);
        Favorite favorite = new Favorite();
        favorite.setUser(user.get());
        favorite.setCategory(FavoriteCategory.valueOf(category));
        System.out.println("itemId = " + itemId);
        favorite.setItemId(itemId);
        favorite.setCreateTime(LocalDateTime.now());

        favoriteRepository.save(favorite);
    }

    private final MiniGameRepository miniGameRepository;  // 미니게임 리포지토리
    private final TalkSubjectRepository talkSubjectRepository;  // 토크 주제 리포지토리
    private final BalanceGameRepository balanceGameRepository;  // 밸런스 게임 리포지토리

    public List<FavoriteResponse> getFavoritesByGoogleId(String googleId) {
        Optional<User> user = userRepository.findBygoogleid(googleId);
        if (user.isEmpty()) {
            throw new UserNotFoundException("User not found for googleId: " + googleId);
        }

        // createTime 기준으로 최신순 정렬하여 가져오기
        List<Favorite> favorites = favoriteRepository.findByUserIdOrderByCreateTimeDesc(user.get().getId());

        return favorites.stream().map(favorite -> {
            FavoriteResponse response = new FavoriteResponse(
                    favorite.getId(),
                    favorite.getCategory().toString(),
                    favorite.getItemId()
            );

            switch (favorite.getCategory()) {
                case mini_game -> {
                    MiniGame miniGame = miniGameRepository.findById(favorite.getItemId())
                            .orElseThrow(() -> new RuntimeException("미니게임 데이터를 찾을 수 없습니다."));
                    response.setName(miniGame.getName());
                    response.setDescription(miniGame.getDescription());
                }
                case small_talk -> {
                    TalkSubject talkSubject = talkSubjectRepository.findById(favorite.getItemId())
                            .orElseThrow(() -> new RuntimeException("토크 주제 데이터를 찾을 수 없습니다."));
                    response.setSubject(talkSubject.getSubject());
                    response.setTalkDescription(talkSubject.getDescription());
                }
                case balance_game -> {
                    BalanceGame balanceGame = balanceGameRepository.findById(favorite.getItemId())
                            .orElseThrow(() -> new RuntimeException("밸런스 게임 데이터를 찾을 수 없습니다."));
                    response.setQuestionA(balanceGame.getQuestionA());
                    response.setQuestionB(balanceGame.getQuestionB());
                }
                default -> throw new IllegalArgumentException("잘못된 카테고리: " + favorite.getCategory());
            }

            return response;
        }).collect(Collectors.toList());
    }


    @Transactional
    public void deleteFavoriteByGoogleIdAndItemId(String googleId, Long favoriteId){
        Optional<User> user = userRepository.findBygoogleid(googleId);
        if (user.isPresent()) {
            // userId와 itemId로 즐겨찾기 항목을 조회
            Optional<Favorite> favorite = favoriteRepository.findById(favoriteId);

            if (favorite.isPresent()) {
                // 즐겨찾기 항목 삭제
                favoriteRepository.delete(favorite.get());
            } else {
                throw new FavoriteNotFoundException("해당 즐겨찾기 항목이 존재하지 않습니다.");
            }
        } else {
            throw new UserNotFoundException("해당 google_id로 유저를 찾을 수 없습니다.");
        }
    }

}
