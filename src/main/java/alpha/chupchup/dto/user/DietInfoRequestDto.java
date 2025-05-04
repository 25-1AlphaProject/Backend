package alpha.chupchup.dto.user;

import alpha.chupchup.entity.enums.Gender;
import alpha.chupchup.entity.enums.MealCount;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DietInfoRequestDto {
    private int age;
    private double height;
    private double weight;
    private Gender gender;
    private MealCount mealCount;
    private int targetCalories;
    private String userDietInfo; // JSON 문자열
}