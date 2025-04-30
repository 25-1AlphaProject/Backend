package alpha.chupchup.service;

import alpha.chupchup.dto.RecipeResponseDto;
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
import java.util.Optional;

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

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RuntimeException("레시피를 찾을 수 없습니다."));
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
        if (favorite.getUser().getId().equals(userId)) {
            userRecipeFavoriteRepository.delete(favorite);
        } else {
            throw new RuntimeException("해당 유저가 등록한 즐겨찾기가 아닙니다.");
        }
    }

    public List<RecipeResponseDto> getFavoriteRecipe(Long userId) {
        List<UserRecipeFavorite> recipes = userRecipeFavoriteRepository.findAllByUserId(userId);

        return recipes.stream()
                .map(fav -> {
                    // Recipe 엔티티에서 필요한 필드를 가져옴
                    return new RecipeResponseDto(
                            fav.getRecipe().getName(),
                            fav.getRecipe().getRecipeText(),
                            fav.getRecipe().getCalories(),
                            fav.getRecipe().getCarbohydrates(),
                            fav.getRecipe().getProtein(),
                            fav.getRecipe().getFat(),
                            fav.getRecipe().getSodium(),
                            fav.getRecipe().getFoodImage(),
                            fav.getRecipe().getIngredient(),
                            fav.getRecipe().getFoodType()
                    );
                })
                .toList();
    }

    public List<RecipeResponseDto> searchRecipe(String keyword) {
        List<Recipe> recipes = recipeRepository.searchRecipes(keyword);
        return recipes.stream()
                .map(recipe -> new RecipeResponseDto(
                        recipe.getName(),
                        recipe.getRecipeText(),
                        recipe.getCalories(),
                        recipe.getCarbohydrates(),
                        recipe.getProtein(),
                        recipe.getFat(),
                        recipe.getSodium(),
                        recipe.getFoodImage(),
                        recipe.getIngredient(),
                        recipe.getFoodType()
                ))
                .toList();
    }

    public RecipeResponseDto getRecipeByRecipeId(Long recipeId) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RuntimeException("해당 레시피를 찾을 수 없습니다."));

        return new RecipeResponseDto(
                recipe.getName(),
                recipe.getRecipeText(),
                recipe.getCalories(),
                recipe.getCarbohydrates(),
                recipe.getProtein(),
                recipe.getFat(),
                recipe.getSodium(),
                recipe.getFoodImage(),
                recipe.getIngredient(),
                recipe.getFoodType()
        );
    }
}
