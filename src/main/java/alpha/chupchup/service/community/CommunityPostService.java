package alpha.chupchup.service.community;

import alpha.chupchup.dto.community.request.PostCreateRequestDto;
import alpha.chupchup.dto.community.request.PostUpdateRequestDto;
import alpha.chupchup.dto.community.response.*;
import alpha.chupchup.entity.community.CommunityPost;
import alpha.chupchup.entity.community.CommunityPostImage;
import alpha.chupchup.entity.user.User;
import alpha.chupchup.repository.CommunityPostImageRepository;
import alpha.chupchup.repository.community.CommunityPostRepository;
import alpha.chupchup.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CommunityPostService {

    private final CommunityPostRepository postRepository;
    private final CommunityPostImageRepository imageRepository;
    private final UserRepository userRepository;

    // 게시글 작성
    @Transactional
    public PostCreateResponseDto createPost(PostCreateRequestDto dto) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("사용자 정보를 찾을 수 없습니다."));

        CommunityPost post = new CommunityPost();
        post.setUser(user);
        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());
        postRepository.save(post);

        // 이미지 URL 저장
        if (dto.getImageUrls() != null) {
            for (String imageUrl : dto.getImageUrls()) {
                CommunityPostImage image = new CommunityPostImage();
                image.setPost(post);
                image.setImageUrl(imageUrl);
                imageRepository.save(image);
            }
        }

        return new PostCreateResponseDto("success", "게시글 작성 완료", post.getId());
    }

    // 게시글 상세 조회
    @Transactional(readOnly = true)
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
                )).toList();

        List<String> imageUrls = post.getImages().stream()
                .map(CommunityPostImage::getImageUrl)
                .toList();

        return new PostDetailResponseDto(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                imageUrls,
                comments,
                post.getLikeCount(),
                post.getScrapCount(),
                post.getComments().size(),
                post.getCreatedAt(),
                new AuthorInfoDto(
                        post.getUser().getId(),
                        post.getUser().getNickname(),
                        post.getUser().getProfileImageUrl()
                )
        );
    }

    // 게시글 수정
    @Transactional
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
    @Transactional
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
    @Transactional(readOnly = true)
    public Map<String, Object> getPostList(String sort, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, getSortBy(sort));
        Page<CommunityPost> postPage = postRepository.findAll(pageable);

        List<PostListItemDto> postList = postPage.stream().map(post -> new PostListItemDto(
                post.getId(),
                post.getTitle(),
                post.getLikeCount(),
                post.getScrapCount(),
                post.getComments().size(),
                post.getImages().isEmpty() ? null : post.getImages().get(0).getImageUrl(),
                post.getCreatedAt()
        )).toList();

        Map<String, Object> result = new HashMap<>();
        result.put("posts", postList);
        result.put("currentPage", postPage.getNumber() + 1);
        result.put("totalPages", postPage.getTotalPages());

        return result;
    }

    private Sort getSortBy(String sort) {
        return switch (sort) {
            case "popular" -> Sort.by(Sort.Order.desc("likeCount"));
            case "scrapped" -> Sort.by(Sort.Order.desc("scrapCount"));
            default -> Sort.by(Sort.Order.desc("createdAt"));
        };
    }

    @Transactional(readOnly = true)
    public List<PostListItemDto> getMyPosts() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username).orElseThrow();

        return postRepository.findAllByUser(user).stream()
                .map(post -> new PostListItemDto(
                        post.getId(),
                        post.getTitle(),
                        post.getLikeCount(),
                        post.getScrapCount(),
                        post.getComments().size(),
                        post.getImages().isEmpty() ? null : post.getImages().get(0).getImageUrl(),
                        post.getCreatedAt()
                )).toList();
    }

    @Transactional(readOnly = true)
    public List<PostListItemDto> searchPosts(String keyword) {
        return postRepository
                .findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(keyword, keyword)
                .stream()
                .map(post -> new PostListItemDto(
                        post.getId(),
                        post.getTitle(),
                        post.getLikeCount(),
                        post.getScrapCount(),
                        post.getComments().size(),
                        post.getImages().isEmpty() ? null : post.getImages().get(0).getImageUrl(),
                        post.getCreatedAt()
                )).toList();
    }
}
