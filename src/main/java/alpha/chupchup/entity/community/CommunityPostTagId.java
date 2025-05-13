package alpha.chupchup.entity.community;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.*;

@Embeddable
@Data
@NoArgsConstructor @AllArgsConstructor
public class CommunityPostTagId implements Serializable {
    private Long post;
    private Long tag;
}
