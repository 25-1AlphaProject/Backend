package alpha.chupchup.entity.community;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "community_post_tags") //게시글-태그 중간 매핑 테이블
@IdClass(CommunityPostTagId.class)
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class CommunityPostTag {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private CommunityPost post;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id", nullable = false)
    private CommunityTag tag;
}
