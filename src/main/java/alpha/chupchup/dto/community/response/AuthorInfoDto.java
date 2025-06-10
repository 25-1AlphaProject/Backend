package alpha.chupchup.dto.community.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthorInfoDto {
    private Long userId;
    private String nickname;
    private String profileImageUrl;
}
