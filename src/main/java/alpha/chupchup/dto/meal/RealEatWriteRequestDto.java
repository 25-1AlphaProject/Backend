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
public class RealEatWriteRequestDto {

    @Schema(description = "메뉴 이름")
    private String name;

    @Schema(description = "칼로리")
    private float foodCalories;

    @Schema(description = "몇 인분")
    private String amount;

    @Schema(description = "먹은 날짜")
    private LocalDate mealDate;

    @Schema(description = "타입[BREAKFAST, LUNCH, DINNER, SNACK]")
    private MealType mealType;

    @Schema(description = "음식 사진 S3 url")
    private String mealPhoto;
}
