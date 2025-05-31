package alpha.chupchup.dto.meal;

import alpha.chupchup.entity.enums.MealType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RealEatResponseDto {
    String mealName;
    String mealPhoto;
    Float calories;
    Float protein;
    Float fat;
    Float carbohydrate;
    MealType mealType;
}
