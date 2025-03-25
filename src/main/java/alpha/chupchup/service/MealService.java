package alpha.chupchup.service;

import alpha.chupchup.dto.CookeryResponseDto;
import alpha.chupchup.dto.FeedbackRequestDto;
import alpha.chupchup.dto.MealDto;
import alpha.chupchup.entity.Recipe;
import alpha.chupchup.entity.WeeklyMeal;
import alpha.chupchup.repository.MealFeedbackRepository;
import alpha.chupchup.repository.MealRepository;
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
    private final MealFeedbackRepository mealFeedbackRepository;

    public List<MealDto> getOneDayMealByDate(LocalDateTime dateTime) {
        List<WeeklyMeal> mealList = mealRepository.findAllByMealDateOrderByIdAsc(dateTime);
        return mealList.stream()
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
    public void registerFeedback(FeedbackRequestDto requestDto) {
        WeeklyMeal meal = mealRepository.findById(requestDto.getMealId())
                .orElseThrow(() -> new RuntimeException("해당 식단이 존재하지 않습니다."));

        MealFeedback feedback = MealFeedback.builder()
                .user(user)
                .meal(meal)
                .feedback(requestDto.getFeedback())
                .build();

        mealFeedbackRepository.save(feedback);
    }

    @Transactional
    public void deleteFeedback(Long mealId) {
        MealFeedback feedback = mealFeedbackRepository.findByMealIdAndUserId(mealId, userId)
                .orElseThrow(() -> new RuntimeException("피드백이 존재하지 않습니다."));
        mealFeedbackRepository.delete(feedback);
    }

    public CookeryResponseDto getCookery(Long mealId) {
        WeeklyMeal meal = mealRepository.findById(mealId)
                .orElseThrow(() -> new RuntimeException("해당 식단이 존재하지 않습니다."));
        String recipeText = meal.getRecipe().getRecipeText();
        String recipeImage = meal.getRecipe().getRecipeImage();
        return new CookeryResponseDto(recipeText, recipeImage);
    }
}
