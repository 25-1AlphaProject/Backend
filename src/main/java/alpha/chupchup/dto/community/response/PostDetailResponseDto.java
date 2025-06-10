package alpha.chupchup.dto.community.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class PostDetailResponseDto {
    private Long postId;
    private String title;
    private String content;
    private List<String> imageUrls;
    private List<CommentResponseDto> comments;
    private int likeCount;
    private int scrapCount;
    private int commentCount;
    private LocalDateTime createdAt;
    private AuthorInfoDto author;
}
