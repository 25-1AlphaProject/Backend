package alpha.chupchup.repository.community;

import alpha.chupchup.entity.community.CommunityComment;
import alpha.chupchup.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommunityCommentRepository extends JpaRepository<CommunityComment, Long> {
    List<CommunityComment> findAllByPostId(Long postId);
    List<CommunityComment> findAllByPostIdOrderByCreatedAtAsc(Long postId);
    List<CommunityComment> findAllByUser(User user);

}
