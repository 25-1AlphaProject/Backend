package alpha.chupchup.repository;

import alpha.chupchup.entity.CommunityComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommunityCommentRepository extends JpaRepository<CommunityComment, Long> {

    List<CommunityComment> findAllByPostId(Long postId); // 댓글 조회용
}
