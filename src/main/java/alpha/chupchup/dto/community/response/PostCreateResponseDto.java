package alpha.chupchup.dto.community.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostCreateResponseDto {
    private String status;
    private String message;
    private Long postId;
}
