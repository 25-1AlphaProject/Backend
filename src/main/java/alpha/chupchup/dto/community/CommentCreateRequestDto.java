package alpha.chupchup.dto.community;

import lombok.Getter;

@Getter
public class CommentCreateRequestDto {
    private String content;
    private Long parentCommentId; // 대댓글
}
