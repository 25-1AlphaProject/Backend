package alpha.chupchup.service.community;

import alpha.chupchup.dto.community.response.PostListItemDto;
import alpha.chupchup.entity.community.CommunityScrap;
import alpha.chupchup.entity.community.CommunityPost;
import alpha.chupchup.entity.user.User;
import alpha.chupchup.repository.community.CommunityScrapRepository;
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
public class CommunityScrapService {

    private final UserRepository userRepository;
    private final CommunityPostRepository postRepository;
    private final CommunityScrapRepository scrapRepository;

    @Transactional
    public int toggleScrap(Long postId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username).orElseThrow();
        CommunityPost post = postRepository.findById(postId).orElseThrow();

        Optional<CommunityScrap> existing = scrapRepository.findByUserAndPost(user, post);
        if (existing.isPresent()) {
            scrapRepository.delete(existing.get());
            post.setScrapCount(post.getScrapCount() - 1);
        } else {
            scrapRepository.save(new CommunityScrap(user, post));
            post.setScrapCount(post.getScrapCount() + 1);
        }
        return post.getScrapCount();
    }

    @Transactional(readOnly = true)
    public List<PostListItemDto> getScrappedPostsByCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username).orElseThrow();

        return scrapRepository.findAllByUser(user).stream()
                .map(scrap -> {
                    CommunityPost post = scrap.getPost();
                    return new PostListItemDto(
                            post.getId(),
                            post.getTitle(),
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
