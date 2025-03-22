package alpha.chupchup.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "community_likes")
@IdClass(CommunityLikeId.class)
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class CommunityLike {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private CommunityPost post;
}
