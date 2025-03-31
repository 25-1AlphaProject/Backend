package alpha.chupchup.dto;

import alpha.chupchup.entity.enums.MealType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MealDto {
    String name;
    String recipeText;
    float calories;
    float carbohydrates;
    float protein;
    float fat;
    float sodium;
    String foodImage;
    String ingredient;
    String foodType;
    MealType mealType;
    LocalDateTime dateTime;
}
