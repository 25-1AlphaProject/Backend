package alpha.chupchup.service;

import alpha.chupchup.dto.recipe.RecipeResponseDto;
import alpha.chupchup.entity.recipe.Recipe;
import alpha.chupchup.entity.recipe.UserRecipeFavorite;
import alpha.chupchup.entity.user.User;
import alpha.chupchup.repository.RecipeRepository;
import alpha.chupchup.repository.UserRecipeFavoriteRepository;
import alpha.chupchup.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final UserRecipeFavoriteRepository userRecipeFavoriteRepository;
    private final UserRepository userRepository;
    private final RestTemplate restTemplate = new RestTemplate();

    @Transactional
    public void postRecipeFavorite(Long userId, Long recipeId) {
        Optional<UserRecipeFavorite> recipeFavorite = userRecipeFavoriteRepository.findByUserIdAndRecipeId(userId, recipeId);
        if (recipeFavorite.isPresent()) {
            throw new RuntimeException("이미 등록된 좋아요입니다.");
        }

        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RuntimeException("레시피를 찾을 수 없습니다."));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("해당 유저를 찾을 수 없습니다."));

        UserRecipeFavorite favorite = UserRecipeFavorite.builder()
                .user(user)
                .recipe(recipe)
                .build();
        userRecipeFavoriteRepository.save(favorite);
    }

    @Transactional
    public void deleteRecipeFavorite(Long userId, Long recipeId) {
        UserRecipeFavorite favorite = userRecipeFavoriteRepository.findByUserIdAndRecipeId(userId, recipeId)
                .orElseThrow(() -> new RuntimeException("즐겨찾기 기록이 존재하지 않습니다."));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("해당 유저를 찾을 수 없습니다."));

        if (favorite.getUser().equals(user)) {
            userRecipeFavoriteRepository.delete(favorite);
        } else {
            throw new RuntimeException("해당 유저가 등록한 즐겨찾기가 아닙니다.");
        }
    }

    public List<RecipeResponseDto> getFavoriteRecipe(Long userId) {
        List<UserRecipeFavorite> recipes = userRecipeFavoriteRepository.findAllByUserId(userId);

        return recipes.stream()
                .map(fav -> RecipeResponseDto.builder()
                        .name(fav.getRecipe().getName())
                        .recipeTexts(getRecipeTexts(fav.getRecipe()))
                        .calories(fav.getRecipe().getCalories())
                        .carbohydrates(fav.getRecipe().getCarbohydrates())
                        .protein(fav.getRecipe().getProtein())
                        .fat(fav.getRecipe().getFat())
                        .sodium(fav.getRecipe().getSodium())
                        .foodImage(fav.getRecipe().getFoodImage())
                        .ingredient(fav.getRecipe().getIngredient())
                        .foodType(fav.getRecipe().getFoodType())
                        .build())
                .toList();
    }

    public List<RecipeResponseDto> searchRecipe(String keyword) {
        List<Recipe> recipes = recipeRepository.searchRecipes(keyword);
        return recipes.stream()
                .map(r -> RecipeResponseDto.builder()
                        .id(r.getId())
                        .name(r.getName())
                        .recipeTexts(getRecipeTexts(r))
                        .calories(r.getCalories())
                        .carbohydrates(r.getCarbohydrates())
                        .protein(r.getProtein())
                        .fat(r.getFat())
                        .sodium(r.getSodium())
                        .foodImage(r.getFoodImage())
                        .ingredient(r.getIngredient())
                        .foodType(r.getFoodType())
                        .build())
                .toList();
    }

    public RecipeResponseDto getRecipeByRecipeId(Long recipeId) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RuntimeException("해당 레시피를 찾을 수 없습니다."));

        return RecipeResponseDto.builder()
                .name(recipe.getName())
                .recipeTexts(getRecipeTexts(recipe))
                .calories(recipe.getCalories())
                .carbohydrates(recipe.getCarbohydrates())
                .protein(recipe.getProtein())
                .fat(recipe.getFat())
                .sodium(recipe.getSodium())
                .foodImage(recipe.getFoodImage())
                .ingredient(recipe.getIngredient())
                .foodType(recipe.getFoodType())
                .build();
    }

    List<String> getRecipeTexts(Recipe recipe) {
        return Stream.of(
                recipe.getRecipeText1(),
                recipe.getRecipeText2(),
                recipe.getRecipeText3(),
                recipe.getRecipeText4(),
                recipe.getRecipeText5(),
                recipe.getRecipeText6()
        )
        .filter(Objects::nonNull)
        .toList();
    }

    public String getRecipeImage(String imageUrl) {
        ResponseEntity<byte[]> resp = restTemplate.getForEntity(imageUrl, byte[].class);

        if (!resp.getStatusCode().is2xxSuccessful() || resp.getBody() == null) {
            throw new RuntimeException("이미지 조회에 실패했습니다.");
        }

        // 1) 응답 바이트를 Base64 문자열로 인코딩
        String base64 = Base64.getEncoder().encodeToString(resp.getBody());
        // 2) Content-Type 헤더 가져오기 (없으면 application/octet-stream)
        String contentType = Optional.ofNullable(resp.getHeaders().getContentType())
                .map(MediaType::toString)
                .orElse("application/octet-stream");
        // 3) data URI 조합
        return "data:" + contentType + ";base64," + base64;
    }
}
