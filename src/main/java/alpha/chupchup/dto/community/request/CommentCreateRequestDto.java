package alpha.chupchup.dto.community.request;

import lombok.Getter;

@Getter
public class CommentCreateRequestDto {
    private String content;
    private Long parentCommentId; // 대댓글
}
