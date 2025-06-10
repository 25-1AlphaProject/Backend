package alpha.chupchup.repository.community;

import alpha.chupchup.entity.community.CommunityPost;
import alpha.chupchup.entity.community.CommunityPostImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunityPostImageRepository extends JpaRepository<CommunityPostImage, Long> {
    void deleteAllByPost(CommunityPost post);
}
