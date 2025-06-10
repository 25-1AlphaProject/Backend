package alpha.chupchup.entity.community;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "community_post_images")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class CommunityPostImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private CommunityPost post;

    @Column(nullable = false)
    private String imageUrl;
}
