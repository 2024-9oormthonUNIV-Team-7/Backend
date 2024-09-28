package goormthonUniv.floating.controller;

import goormthonUniv.floating.domain.Favorite;
import goormthonUniv.floating.domain.User;
import goormthonUniv.floating.repository.FavoriteRepository;
import goormthonUniv.floating.repository.UserRepository;
import goormthonUniv.floating.response.FavoriteResponse;
import goormthonUniv.floating.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import request.FavoriteRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Slf4j
public class FavoriteController {

    private final FavoriteService favoriteService;
    @GetMapping("/favorites/{googleId}")
    public ResponseEntity<Map<String, Object>> getFavoritesByGoogleId(@PathVariable String googleId) {
        List<FavoriteResponse> favoriteResponses = favoriteService.getFavoritesByGoogleId(googleId);

        Map<String, Object> response = new HashMap<>();
        response.put("favorite", favoriteResponses);
        response.put("status", 200);
        response.put("message", "즐겨찾기 조회 성공");
        return ResponseEntity.ok(response);
    }


    @PostMapping("/post-favorite")
    public ResponseEntity<Map<String, Object>> postFavorite(@RequestBody Map<String, Object> requestBody) {
        String email = (String) requestBody.get("email");
        String category = (String) requestBody.get("category");
        Long itemId = Long.valueOf(requestBody.get("itemId").toString());  // Long 변환 처리

        favoriteService.addFavorite(email, category, itemId);

        Map<String, Object> response = new HashMap<>();
        response.put("status", 200);
        response.put("message", "즐겨찾기 추가 성공");
        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/delete-favorite")
    public ResponseEntity<Map<String,Object>> deleteFavorite( @RequestParam("google_Id") String googleId,
                                                              @RequestParam("favorite_Id") Long favoriteId){
        favoriteService.deleteFavoriteByGoogleIdAndItemId(googleId,favoriteId);
        Map<String, Object> response = new HashMap<>();
        response.put("status",200);
        response.put("message", "즐겨찾기 항목 삭제 성공");
        return ResponseEntity.ok(response);
    }


}
