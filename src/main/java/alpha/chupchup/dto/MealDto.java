package alpha.chupchup.dto;

import alpha.chupchup.entity.enums.MealType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MealDto {
    String name;
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
    LocalDateTime dateTime;
}
