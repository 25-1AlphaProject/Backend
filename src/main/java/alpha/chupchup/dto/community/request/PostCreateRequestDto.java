package alpha.chupchup.dto.community.request;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostCreateRequestDto {
    private String title;
    private String content;
    private List<String> imageUrls;
}
