package alpha.chupchup.dto.user;

import alpha.chupchup.entity.enums.Gender;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DietInfoRequestDto {
    private int age;
    private int height;
    private int weight;
    private Gender gender;
    private String goal;
    private int targetCalories;
}
