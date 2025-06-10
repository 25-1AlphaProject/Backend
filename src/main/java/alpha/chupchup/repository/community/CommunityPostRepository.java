package alpha.chupchup.repository.community;

import alpha.chupchup.entity.community.CommunityPost;
import alpha.chupchup.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommunityPostRepository extends JpaRepository<CommunityPost, Long> {
    List<CommunityPost> findAllByUser(User user);
    List<CommunityPost> findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(String title, String content);
}