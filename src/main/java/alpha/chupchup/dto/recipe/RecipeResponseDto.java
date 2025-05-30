package alpha.chupchup.dto.recipe;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecipeResponseDto {

    private Long id;
    private String name;
    private List<String> recipeTexts;
    private Float calories;
    private Float carbohydrates;
    private Float protein;
    private Float fat;
    private Float sodium;
    private String foodImage;
    private String ingredient;
    private String foodType;
}
