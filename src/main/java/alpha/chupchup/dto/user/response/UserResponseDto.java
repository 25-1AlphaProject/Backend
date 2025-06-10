package alpha.chupchup.dto.user.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserResponseDto {
    private String status;
    private String message;
    private Long userId;
    private String token;
}
