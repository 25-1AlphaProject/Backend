package alpha.chupchup.dto.fastapi;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class FastApiUserDietInfo {

    private List<String> diseases;
    private List<String> allergies;
    @JsonAlias({"preferredMenus","preferred_menus"})
    private List<String> preferredMenus;
    @JsonAlias({"avoidIngredients","avoid_ingredients"})
    private List<String> avoidIngredients;
}
