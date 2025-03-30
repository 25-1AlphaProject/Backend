package alpha.chupchup.dto.user;

import alpha.chupchup.entity.enums.Gender;
import alpha.chupchup.entity.enums.MealCount;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserDetailResponseDto {
    private int age;
    private double height;
    private double weight;
    private Gender gender;
    private MealCount mealCount;
    private int targetCalories;
    private String userDietInfo;
}
