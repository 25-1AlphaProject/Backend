package alpha.chupchup.dto.fastapi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FastApiCustomMealResponseDto {
    private String mealName;
    private float foodCalories;
    private Float protein;
    private Float fat;
    private Float carbohydrate;
}
