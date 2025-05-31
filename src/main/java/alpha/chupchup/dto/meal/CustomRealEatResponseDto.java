package alpha.chupchup.dto.meal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomRealEatResponseDto {
    private String mealName;
    private Float foodCalories;
    private Float protein;
    private Float fat;
    private Float carbohydrate;
}
