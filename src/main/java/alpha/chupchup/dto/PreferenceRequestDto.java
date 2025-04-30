package alpha.chupchup.dto;

import alpha.chupchup.entity.enums.Preference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PreferenceRequestDto {

    @Schema(description = "실제 식단의 아이디")
    private Long realEatId;

    @Schema(description = "선호도 (LIKE, DISLIKE)", example = "LIKE")
    private Preference preference;
}
