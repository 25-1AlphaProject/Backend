package alpha.chupchup.dto.user;

import alpha.chupchup.entity.enums.Gender;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DietInfoRequestDto {
    private int age;
    private double height;
    private double weight;
    private Gender gender;
    private List<String> mealCount;
    private int targetCalories;
    private UserDietInfoDto userDietInfo;
}