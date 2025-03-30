package alpha.chupchup.dto.user;

import alpha.chupchup.entity.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserDetailResponseDto {
    private int age;
    private int height;
    private int weight;
    private Gender gender;
    private String goal;
    private int targetCalories;
}