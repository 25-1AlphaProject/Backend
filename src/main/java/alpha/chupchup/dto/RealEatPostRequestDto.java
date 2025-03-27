package alpha.chupchup.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RealEatPostRequestDto {
    private Long recipeId;

    private String mealPhoto;

    private LocalDateTime mealDate;

    private float customFoodCalories;

    private String customFoodName;
}
