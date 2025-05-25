package alpha.chupchup.dto.community.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class PostListItemDto {
    private Long postId;
    private String title;
    private int likeCount;
    private int scrapCount;
    private LocalDateTime createdAt;
}