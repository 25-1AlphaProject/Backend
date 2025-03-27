package alpha.chupchup.dto;

import alpha.chupchup.entity.enums.Preference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PreferenceRequestDto {
    private Long realEatId;
    private Preference preference;
}
