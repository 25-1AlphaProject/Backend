package alpha.chupchup.repository.community;

import alpha.chupchup.entity.community.CommunityLike;
import alpha.chupchup.entity.community.CommunityScrap;
import alpha.chupchup.entity.community.CommunityPost;
import alpha.chupchup.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommunityScrapRepository extends JpaRepository<CommunityScrap, Long> {
    Optional<CommunityScrap> findByUserAndPost(User user, CommunityPost post);
    List<CommunityScrap> findAllByUser(User user);
}
