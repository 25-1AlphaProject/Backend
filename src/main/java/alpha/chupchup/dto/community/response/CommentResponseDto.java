package alpha.chupchup.dto.community.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CommentResponseDto {
    private Long commentId;
    private String content;
    private Long parentCommentId;
    private LocalDateTime createdAt;
    private AuthorInfoDto author;
}
