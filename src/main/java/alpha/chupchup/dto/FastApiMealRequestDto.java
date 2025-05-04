package alpha.chupchup.dto;

import alpha.chupchup.entity.enums.Gender;
import alpha.chupchup.entity.enums.MealCount;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FastApiMealRequestDto {
    private Long user_id;
    private Gender gender;
    private Integer age;
    private Double weight;
    private MealCount meal_count;
    private Integer target_calories;
    private String user_diet_info;
}
