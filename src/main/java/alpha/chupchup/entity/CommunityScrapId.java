package alpha.chupchup.entity;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.*;

@Embeddable
@Data
@NoArgsConstructor @AllArgsConstructor
public class CommunityScrapId implements Serializable {
    private Long user;
    private Long post;
}
