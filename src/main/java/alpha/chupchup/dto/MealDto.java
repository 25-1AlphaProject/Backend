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
    Float calories;
    Float carbohydrates;
    Float protein;
    Float fat;
    Float sodium;
    String foodImage;
    String ingredient;
    String foodType;
    MealType mealType;
    LocalDateTime dateTime;
}
