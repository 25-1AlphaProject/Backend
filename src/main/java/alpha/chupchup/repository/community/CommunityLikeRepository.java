package alpha.chupchup.repository.community;

import alpha.chupchup.entity.community.CommunityLike;
import alpha.chupchup.entity.community.CommunityPost;
import alpha.chupchup.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommunityLikeRepository extends JpaRepository<CommunityLike, Long> {
    Optional<CommunityLike> findByUserAndPost(User user, CommunityPost post);
    List<CommunityLike> findAllByUser(User user);
}
