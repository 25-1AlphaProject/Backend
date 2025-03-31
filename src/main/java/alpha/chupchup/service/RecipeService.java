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
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final UserRecipeFavoriteRepository userRecipeFavoriteRepository;
    private final UserRepository userRepository;

    @Transactional
    public void postRecipeFavorite(Long recipeId) {
        User user = userRepository.findById(0L)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RuntimeException("레시피를 찾을 수 없습니다."));
        Optional<UserRecipeFavorite> recipeFavorite = userRecipeFavoriteRepository.findByUserAndRecipe(user, recipe);
        if (recipeFavorite.isPresent()) {
            throw new RuntimeException("이미 등록된 좋아요입니다.");
        }
        UserRecipeFavorite favorite = UserRecipeFavorite.builder()
                .user(user)
                .recipe(recipe)
                .build();
        userRecipeFavoriteRepository.save(favorite);
    }

    @Transactional
    public void deleteRecipeFavorite(Long recipeId) {
        User user = userRepository.findById(0L)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RuntimeException("레시피를 찾을 수 없습니다."));
        UserRecipeFavorite favorite = userRecipeFavoriteRepository.findByUserAndRecipe(user, recipe)
                .orElseThrow(() -> new RuntimeException("즐겨찾기 기록이 존재하지 않습니다."));
        userRecipeFavoriteRepository.delete(favorite);
    }

    public List<RecipeResponseDto> getFavoriteRecipe(Long userId) {
        User user = userRepository.findById(userId)
                        .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        List<UserRecipeFavorite> recipes = userRecipeFavoriteRepository.findAllByUser(user);

        return recipes.stream()
                .map(fav -> {
                    // Recipe 엔티티에서 필요한 필드를 가져옴
                    return new RecipeResponseDto(
                            fav.getRecipe().getName(),
                            fav.getRecipe().getRecipeImage(),
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
        List<RecipeResponseDto> responseList = recipes.stream()
                .map(recipe -> new RecipeResponseDto(
                        recipe.getName(),
                        recipe.getRecipeImage(),
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
        return responseList;
    }
}
