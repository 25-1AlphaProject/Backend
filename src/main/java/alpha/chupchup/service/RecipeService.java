package alpha.chupchup.service;

import alpha.chupchup.dto.recipe.RecipeResponseDto;
import alpha.chupchup.entity.Recipe;
import alpha.chupchup.entity.User;
import alpha.chupchup.entity.UserRecipeFavorite;
import alpha.chupchup.repository.RecipeRepository;
import alpha.chupchup.repository.UserRecipeFavoriteRepository;
import alpha.chupchup.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
