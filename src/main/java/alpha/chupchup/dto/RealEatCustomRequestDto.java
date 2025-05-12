package alpha.chupchup.dto;

import alpha.chupchup.entity.enums.MealType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class RealEatCustomRequestDto {

    @Schema(description = "사진")
    private String mealPhoto;

    @Schema(description = "먹은 날짜")
    private LocalDate mealDate;

    @Schema(description = "몇 인분")
    private float amount;

    @Schema(description = "타입[BREAKFAST, LUNCH, DINNER, SNACK]")
    private MealType mealType;
}
