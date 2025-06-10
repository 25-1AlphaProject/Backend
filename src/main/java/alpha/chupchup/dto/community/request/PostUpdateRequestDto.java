package alpha.chupchup.dto.community.request;

import lombok.Getter;

import java.util.List;

@Getter
public class PostUpdateRequestDto {
    private String title;
    private String content;
    private List<String> imageUrls;
}
