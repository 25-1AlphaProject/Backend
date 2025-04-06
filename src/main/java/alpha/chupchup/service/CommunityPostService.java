package alpha.chupchup.service;

import alpha.chupchup.dto.community.*;
import alpha.chupchup.entity.CommunityPost;
import alpha.chupchup.entity.User;
import alpha.chupchup.repository.CommunityPostRepository;
import alpha.chupchup.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommunityPostService {

    private final CommunityPostRepository postRepository;
    private final UserRepository userRepository;

    public PostCreateResponseDto createPost(PostCreateRequestDto dto) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("사용자 정보를 찾을 수 없습니다."));

        CommunityPost post = new CommunityPost();
        post.setUser(user);
        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());

        postRepository.save(post);

        return new PostCreateResponseDto("success", "게시글 작성 완료", post.getId());
    }
}
