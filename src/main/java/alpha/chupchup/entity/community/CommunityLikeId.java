package alpha.chupchup.entity.community;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.*;

@Embeddable
@Data
@NoArgsConstructor @AllArgsConstructor
public class CommunityLikeId implements Serializable {
    private Long user;
    private Long post;
}
