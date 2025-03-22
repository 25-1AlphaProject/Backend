package alpha.chupchup.entity;

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
