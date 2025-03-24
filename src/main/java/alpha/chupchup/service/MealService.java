package alpha.chupchup.service;

import alpha.chupchup.dto.MealDto;
import alpha.chupchup.entity.Recipe;
import alpha.chupchup.entity.WeeklyMeal;
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
}
