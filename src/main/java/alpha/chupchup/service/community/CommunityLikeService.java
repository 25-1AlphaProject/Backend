package alpha.chupchup.service.community;

import alpha.chupchup.dto.community.response.PostListItemDto;
import alpha.chupchup.entity.community.CommunityLike;
import alpha.chupchup.entity.community.CommunityPost;
import alpha.chupchup.entity.user.User;
import alpha.chupchup.repository.community.CommunityLikeRepository;
import alpha.chupchup.repository.community.CommunityPostRepository;
import alpha.chupchup.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommunityLikeService {

    private final UserRepository userRepository;
    private final CommunityPostRepository postRepository;
    private final CommunityLikeRepository likeRepository;

    @Transactional
    public int toggleLike(Long postId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username).orElseThrow();
        CommunityPost post = postRepository.findById(postId).orElseThrow();

        Optional<CommunityLike> existing = likeRepository.findByUserAndPost(user, post);
        if (existing.isPresent()) {
            likeRepository.delete(existing.get());
            post.setLikeCount(post.getLikeCount() - 1);
        } else {
            likeRepository.save(new CommunityLike(user, post));
            post.setLikeCount(post.getLikeCount() + 1);
        }
        return post.getLikeCount();
    }

    @Transactional(readOnly = true)
    public List<PostListItemDto> getLikedPostsByCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username).orElseThrow();

        return likeRepository.findAllByUser(user).stream()
                .map(like -> {
                    CommunityPost post = like.getPost();
                    return new PostListItemDto(
                            post.getId(),
                            post.getTitle(),
                            post.getContent(),
                            post.getLikeCount(),
                            post.getScrapCount(),
                            post.getComments().size(),
                            post.getImages().isEmpty() ? null : post.getImages().get(0).getImageUrl(),
                            post.getCreatedAt()
                    );
                })
                .toList();
    }
}
