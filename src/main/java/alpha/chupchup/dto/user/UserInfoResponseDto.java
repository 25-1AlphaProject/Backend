package alpha.chupchup.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class UserInfoResponseDto {
    private String username;
    private String nickname;
    private String name;
    private String phoneNumber;
    private String profileImageUrl;
    private LocalDateTime createdAt;
}
