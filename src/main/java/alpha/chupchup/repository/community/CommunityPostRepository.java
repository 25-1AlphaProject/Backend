package alpha.chupchup.repository.community;

import alpha.chupchup.entity.community.CommunityPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunityPostRepository extends JpaRepository<CommunityPost, Long> {
}