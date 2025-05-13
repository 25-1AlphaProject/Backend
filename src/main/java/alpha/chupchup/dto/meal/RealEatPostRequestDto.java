package alpha.chupchup.dto.meal;

import alpha.chupchup.entity.enums.MealType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RealEatPostRequestDto {

    @Schema(description = "식단 아이디")
    private Long mealId;

    @Schema(description = "먹은 날짜")
    private LocalDate mealDate;

    @Schema(description = "음식 칼로리")
    private float foodCalories;

    @Schema(description = "타입[BREAKFAST, LUNCH, DINNER, SNACK]")
    private MealType mealType;
}
