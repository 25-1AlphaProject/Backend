package alpha.chupchup.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FastApiCustomMealResponseDto {
    private String mealName;
    private float foodCalories;
}
