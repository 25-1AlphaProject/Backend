package alpha.chupchup.service.community;

import alpha.chupchup.dto.community.response.AuthorInfoDto;
import alpha.chupchup.dto.community.request.CommentCreateRequestDto;
import alpha.chupchup.dto.community.response.CommentResponseDto;
import alpha.chupchup.dto.community.request.CommentUpdateRequestDto;
import alpha.chupchup.entity.community.CommunityComment;
import alpha.chupchup.entity.community.CommunityPost;
import alpha.chupchup.entity.user.User;
import alpha.chupchup.repository.*;
import alpha.chupchup.repository.community.CommunityCommentRepository;
import alpha.chupchup.repository.community.CommunityPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommunityCommentService {

    private final CommunityPostRepository postRepository;
    private final CommunityCommentRepository commentRepository;
    private final UserRepository userRepository;

    // (대)댓글 작성
    @Transactional
    public void createComment(Long postId, CommentCreateRequestDto dto) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("사용자 정보를 찾을 수 없습니다."));

        CommunityPost post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

        CommunityComment comment = new CommunityComment();
        comment.setUser(user);
        comment.setPost(post);
        comment.setContent(dto.getContent());

        if (dto.getParentCommentId() != null) {
            CommunityComment parent = commentRepository.findById(dto.getParentCommentId())
                    .orElseThrow(() -> new IllegalArgumentException("부모 댓글이 존재하지 않습니다."));

            if (parent.getParentComment() != null) {
                throw new IllegalArgumentException("대댓글에는 댓글을 달 수 없습니다.");
            }
            comment.setParentComment(parent);
        }

        commentRepository.save(comment);
    }

    // (대)댓글 조회
    @Transactional
    public List<CommentResponseDto> getCommentsByPost(Long postId) {
        List<CommunityComment> commentList = commentRepository.findAllByPostId(postId);

        return commentList.stream().map(c ->
                new CommentResponseDto(
                        c.getId(),
                        c.getContent(),
                        c.getParentComment() != null ? c.getParentComment().getId() : null,
                        c.getCreatedAt(),
                        new AuthorInfoDto(
                                c.getUser().getId(),
                                c.getUser().getNickname(),
                                c.getUser().getProfileImageUrl()
                        )
                )
        ).toList();
    }
    // (대)댓글 수정
    @Transactional
    public void updateComment(Long commentId, CommentUpdateRequestDto dto) {
        CommunityComment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글이 존재하지 않습니다."));

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!comment.getUser().getUsername().equals(username)) {
            throw new SecurityException("수정 권한이 없습니다.");
        }

        comment.setContent(dto.getContent());
    }
    // (대)댓글 삭제
    @Transactional
    public void deleteComment(Long commentId) {
        CommunityComment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글이 존재하지 않습니다."));

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!comment.getUser().getUsername().equals(username)) {
            throw new SecurityException("삭제 권한이 없습니다.");
        }

        commentRepository.delete(comment);
    }

    @Transactional(readOnly = true)
    public List<CommentResponseDto> getMyComments() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username).orElseThrow();

        return commentRepository.findAllByUser(user).stream()
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
    }
}
