package alpha.chupchup.dto.meal;

import alpha.chupchup.entity.enums.MealType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MealDto {
    String name;
    Long recipeId;
    List<String> recipeTexts;
    Float calories;
    Float carbohydrates;
    Float protein;
    Float fat;
    Float sodium;
    String foodImage;
    String ingredient;
    String foodType;
    MealType mealType;
    LocalDate dateTime;
}
