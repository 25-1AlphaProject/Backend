package alpha.chupchup.service;

import alpha.chupchup.dto.community.*;
import alpha.chupchup.entity.community.CommunityPost;
import alpha.chupchup.entity.User;
import alpha.chupchup.repository.CommunityPostRepository;
import alpha.chupchup.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Page;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    // 게시글 수정
    public void updatePost(Long postId, PostUpdateRequestDto dto) {
        CommunityPost post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!post.getUser().getUsername().equals(username)) {
            throw new SecurityException("수정 권한이 없습니다.");
        }

        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());
    }

    // 게시글 삭제
    public void deletePost(Long postId) {
        CommunityPost post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!post.getUser().getUsername().equals(username)) {
            throw new SecurityException("삭제 권한이 없습니다.");
        }

        postRepository.delete(post);
    }

    // 게시글 목록 조회 페이징
    public Map<String, Object> getPostList(String sort, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, getSortBy(sort));
        Page<CommunityPost> postPage = postRepository.findAll(pageable);

        List<Map<String, Object>> postList = postPage.stream().map(post -> {
            Map<String, Object> p = new HashMap<>();
            p.put("postId", post.getId());
            p.put("title", post.getTitle());
            p.put("likeCount", post.getLikes().size());
            p.put("scrapCount", post.getScraps().size());
            p.put("createdAt", post.getCreatedAt());
            return p;
        }).toList();

        Map<String, Object> result = new HashMap<>();
        result.put("posts", postList);
        result.put("currentPage", postPage.getNumber() + 1);
        result.put("totalPages", postPage.getTotalPages());

        return result;
    }

    private Sort getSortBy(String sort) {
        return switch (sort) {
            case "popular" -> Sort.by(Sort.Order.desc("likes"));  // likeCount를 기준으로 정렬
            default -> Sort.by(Sort.Order.desc("createdAt"));
        };
    }


}
