package alpha.chupchup.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipeSearchRequestDto {

    @Schema(description = "검색할 키워드")
    private String keyword;
}
