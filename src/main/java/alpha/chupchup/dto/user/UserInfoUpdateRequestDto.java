package alpha.chupchup.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfoUpdateRequestDto {
    private String nickname;
    private String password;
    private String profileImageUrl;
}