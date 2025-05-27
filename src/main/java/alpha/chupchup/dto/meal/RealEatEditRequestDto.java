package alpha.chupchup.dto.meal;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RealEatEditRequestDto {

    @Schema(description = "사진")
    private String mealPhoto;

    @Schema(description = "메뉴 이름")
    private String customFoodName;

    @Schema(description = "음식 칼로리")
    private Float customFoodCalories;
}
