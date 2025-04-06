package alpha.chupchup.service;

import alpha.chupchup.dto.community.*;
import alpha.chupchup.entity.CommunityPost;
import alpha.chupchup.entity.User;
import alpha.chupchup.repository.CommunityPostRepository;
import alpha.chupchup.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommunityPostService {

    private final CommunityPostRepository postRepository;
    private final UserRepository userRepository;

    // 게시글 작성
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

    // 게시글 조회
    public PostDetailResponseDto getPostDetail(Long postId) {
        CommunityPost post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));

        List<CommentResponseDto> comments = post.getComments().stream()
                .map(c -> new CommentResponseDto(
                        c.getId(),
                        c.getContent(),
                        c.getParentComment() != null ? c.getParentComment().getId() : null,
                        c.getCreatedAt(),
                        new AuthorInfoDto(
                                c.getUser().getId(),
                                c.getUser().getNickname(),
                                c.getUser().getProfileImageUrl()
                        )
                ))
                .toList();

        return new PostDetailResponseDto(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                List.of(), // images
                comments,
                post.getLikes().size(),
                post.getScraps().size(),
                post.getCreatedAt(),
                new AuthorInfoDto(
                        post.getUser().getId(),
                        post.getUser().getNickname(),
                        post.getUser().getProfileImageUrl()
                )
        );
    }

}
