package alpha.chupchup.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RealEatPostRequestDto {

    @Schema(description = "레시피 아이디")
    private Long recipeId;

    @Schema(description = "사진")
    private String mealPhoto;

    @Schema(description = "먹은 날짜")
    private LocalDateTime mealDate;

    @Schema(description = "음식 칼로리(사용자가 입력하지 않은 경우: 0으로 입력)")
    private float customFoodCalories;

    @Schema(description = "음식 이름(사용자가 바꾼 경우에만 입력)")
    private String customFoodName;
}
