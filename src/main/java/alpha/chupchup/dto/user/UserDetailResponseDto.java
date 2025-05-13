package alpha.chupchup.dto.user;

import alpha.chupchup.entity.enums.Gender;
import alpha.chupchup.entity.enums.HealthGoal;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class UserDetailResponseDto {
    private int age;
    private double height;
    private double weight;
    private Gender gender;
    private List<String> mealCounts;
    private int targetCalories;
    private UserDietInfoDto userDietInfo;
    private HealthGoal healthGoal;
}
