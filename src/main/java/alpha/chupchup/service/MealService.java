package alpha.chupchup.service;

import alpha.chupchup.dto.CookeryResponseDto;
import alpha.chupchup.dto.MealDto;
import alpha.chupchup.dto.PreferenceRequestDto;
import alpha.chupchup.dto.RealEatPostRequestDto;
import alpha.chupchup.entity.RealEat;
import alpha.chupchup.entity.Recipe;
import alpha.chupchup.entity.User;
import alpha.chupchup.entity.WeeklyMeal;
import alpha.chupchup.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MealService {
    private final MealRepository mealRepository;
    private final RealEatRepository realEatRepository;
    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;

    public List<MealDto> getOneDayMealByDate(LocalDateTime dateTime) {
        List<RealEat> realEatList = realEatRepository.findAllByMealDateOrderByIdAsc(dateTime);
        return realEatList.stream()
                .map(meal -> {
                    Recipe recipe = meal.getRecipe();
                    return MealDto.builder()
                            .name(recipe.getName())
                            .recipeImage(recipe.getRecipeImage())
                            .recipeText(recipe.getRecipeText())
                            .calories(recipe.getCalories())
                            .carbohydrates(recipe.getCarbohydrates())
                            .protein(recipe.getProtein())
                            .fat(recipe.getFat())
                            .sodium(recipe.getSodium())
                            .foodImage(recipe.getFoodImage())
                            .ingredient(recipe.getIngredient())
                            .foodType(recipe.getFoodType())
                            .mealType(meal.getMealType())
                            .dateTime(meal.getCreatedAt())
                            .build();
                })
                .collect(Collectors.toList());

    }

    @Transactional
    public void registerPreference(PreferenceRequestDto requestDto) {
        RealEat realEat = realEatRepository.findById(requestDto.getRealEatId())
                .orElseThrow(() -> new RuntimeException("해당 RealEat 기록이 존재하지 않습니다."));
        realEat.setPreference(requestDto.getPreference());
    }

    @Transactional
    public void deletePreference(Long realEatId) {
        RealEat realEat = realEatRepository.findById(realEatId)
                .orElseThrow(() -> new RuntimeException("해당 RealEat 기록이 존재하지 않습니다."));
        realEat.setPreference(null);
    }

    public CookeryResponseDto getCookery(Long mealId) {
        WeeklyMeal meal = mealRepository.findById(mealId)
                .orElseThrow(() -> new RuntimeException("해당 식단이 존재하지 않습니다."));
        String recipeText = meal.getRecipe().getRecipeText();
        String recipeImage = meal.getRecipe().getRecipeImage();
        return new CookeryResponseDto(recipeText, recipeImage);
    }

    @Transactional
    public void postRealEat(RealEatPostRequestDto requestDto) {
        Long userId = 0L; //jwt 토큰에서 받아올 예정
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        Recipe recipe = requestDto.getRecipeId() != null ? recipeRepository.findById(requestDto.getRecipeId())
                .orElseThrow(() -> new RuntimeException("레시피를 찾을 수 없습니다.")) : null;

        RealEat realEat = RealEat.builder()
                .user(user)
                .recipe(recipe)
                .mealPhoto(requestDto.getMealPhoto() == null ? recipe.getRecipeImage() : requestDto.getMealPhoto())
                .mealDate(requestDto.getMealDate())
                .customFoodCalories(requestDto.getCustomFoodCalories() == 0 ? recipe.getCalories() : requestDto.getCustomFoodCalories())
                .customFoodName(requestDto.getCustomFoodName() == null ? recipe.getName() : requestDto.getCustomFoodName())
                .build();

        realEatRepository.save(realEat);
    }
}
