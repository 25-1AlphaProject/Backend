package alpha.chupchup.dto.user;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class UserDietInfoDto {
    private List<String> allergies;
    private List<String> diseases;
    private List<String> preferredMenus;
    private List<String> avoidIngredients;
}
