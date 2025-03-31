package alpha.chupchup.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecipeResponseDto {

    private String name;
    private String recipeImage;
    private String recipeText;
    private Float calories;
    private Float carbohydrates;
    private Float protein;
    private Float fat;
    private Float sodium;
    private String foodImage;
    private String ingredient;
    private String foodType;
}
