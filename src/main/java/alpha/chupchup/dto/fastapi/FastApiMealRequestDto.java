package alpha.chupchup.dto.fastapi;

import alpha.chupchup.entity.enums.Gender;
import alpha.chupchup.entity.enums.HealthGoal;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class FastApiMealRequestDto {
    private Long userId;
    private Gender gender;
    private Integer age;
    private Double weight;
    private Double height;
    private List<String> mealCount;
    private Integer targetCalories;
    private FastApiUserDietInfo userDietInfo;
    private HealthGoal healthGoal;
}
