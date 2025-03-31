package alpha.chupchup.controller;

import alpha.chupchup.dto.RecipeResponseDto;
import alpha.chupchup.dto.RecipeSearchRequestDto;
import alpha.chupchup.dto.ResponseDto;
import alpha.chupchup.service.RecipeService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recipe")
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;

    @PostMapping("/favorite/{recipeId}")
    public ResponseEntity<ResponseDto<String>> postRecipeFavorite(@PathVariable Long recipeId) {
        try {
            recipeService.postRecipeFavorite(recipeId);
            return ResponseEntity.ok(ResponseDto.success("좋아요 추가에 성공했습니다."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseDto.error("좋아요 추가에 실패했습니다."));
        }
    }

    @DeleteMapping("/favorite/{recipeId}")
    public ResponseEntity<ResponseDto<String>> deleteRecipeFavorite(@PathVariable Long recipeId) {
        try {
            recipeService.deleteRecipeFavorite(recipeId);
            return ResponseEntity.ok(ResponseDto.success("좋아요 삭제에 성공습니다."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseDto.error("좋아요 삭제에 실패했습니다."));
        }
    }

    @GetMapping("/favorite")
    public ResponseEntity<ResponseDto<?>> getFavoriteRecipe(HttpServletRequest servletRequest) {
        try {
            Long userId = 0L;
            List<RecipeResponseDto> favoriteRecipe = recipeService.getFavoriteRecipe(userId);
            return ResponseEntity.ok(ResponseDto.success(favoriteRecipe));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseDto.error("좋아요 조회에 실패했습니다."));
        }
    }

    @GetMapping("/search")
    public ResponseEntity<ResponseDto<?>> searchRecipe(@RequestBody RecipeSearchRequestDto requestDto) {
        try {
            String keyword = requestDto.getKeyword();
            List<RecipeResponseDto> searchRecipe = recipeService.searchRecipe(keyword);
            return ResponseEntity.ok(ResponseDto.success(searchRecipe));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseDto.error("레시피 검색에 실패했습니다."));
        }
    }
}
