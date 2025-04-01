package alpha.chupchup.controller;

import alpha.chupchup.dto.RecipeResponseDto;
import alpha.chupchup.dto.RecipeSearchRequestDto;
import alpha.chupchup.dto.ResponseDto;
import alpha.chupchup.service.RecipeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recipe")
@RequiredArgsConstructor
@Tag(name = "Recipe-Controller", description = "Recipe-관련-API")
public class RecipeController {

    private final RecipeService recipeService;

    @PostMapping("/favorite/{recipeId}")
    @Operation(summary = "레시피 좋아요 추가하기", description = "해당 레시피를 좋아요에 추가합니다.")
    public ResponseEntity<ResponseDto<String>> postRecipeFavorite(
            @Parameter(
                    description = "레시피 아이디",
                    required = true
            )
            @PathVariable Long recipeId
    ) {
        try {
            recipeService.postRecipeFavorite(recipeId);
            return ResponseEntity.ok(ResponseDto.success("좋아요 추가에 성공했습니다."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseDto.error("좋아요 추가에 실패했습니다."));
        }
    }

    @DeleteMapping("/favorite/{recipeId}")
    @Operation(summary = "레시피 좋아요 삭제하기", description = "해당 레시피를 좋아요에서 삭제합니다.")
    public ResponseEntity<ResponseDto<String>> deleteRecipeFavorite(
            @Parameter(
                    description = "레시피 아이디",
                    required = true
            )
            @PathVariable Long recipeId
    ) {
        try {
            recipeService.deleteRecipeFavorite(recipeId);
            return ResponseEntity.ok(ResponseDto.success("좋아요 삭제에 성공습니다."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseDto.error("좋아요 삭제에 실패했습니다."));
        }
    }

    @GetMapping("/favorite")
    @Operation(summary = "좋아요 누른 레시피 조회하기", description = "좋아요를 눌렀던 레시피를 조회합니다.")
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
    @Operation(summary = "레시피 검색하기", description = "키워드를 이용해서 관련된 레시피를 검색합니다.")
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

    @GetMapping("/{receipeId}")
    @Operation(summary = "레시피 조회하기", description = "레시피 아이디를 통해서 레시피를 조회합니다.")
    public ResponseEntity<ResponseDto<?>> getRecipeByRecipeId(
            @Parameter(
                    description = "레시피 아이디",
                    required = true
            )
            @PathVariable Long recipeId
    ) {
        try {
            RecipeResponseDto recipe = recipeService.getRecipeByRecipeId(recipeId);
            return ResponseEntity.ok(ResponseDto.success(recipe));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseDto.error("레시피 조회에 실패했습니다."));
        }
    }
}
