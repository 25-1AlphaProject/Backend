package alpha.chupchup.controller.community;

import alpha.chupchup.dto.community.response.CommentResponseDto;
import alpha.chupchup.dto.community.request.CommentCreateRequestDto;
import alpha.chupchup.dto.community.request.CommentUpdateRequestDto;
import alpha.chupchup.service.community.CommunityCommentService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/community")
@RequiredArgsConstructor
public class CommunityCommentController {

    private final CommunityCommentService commentService;

    @Operation(summary = "댓글 작성", description = "게시글 ID에 댓글 또는 대댓글을 작성합니다.")
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<Map<String, Object>> createComment(
            @PathVariable Long postId,
            @RequestBody CommentCreateRequestDto dto) {

        commentService.createComment(postId, dto);
        return ResponseEntity.ok(Map.of("status", "success", "message", "댓글 작성 완료"));
    }
    @Operation(summary = "댓글 목록 조회", description = "게시글 ID로 해당 게시글의 댓글 전체를 조회합니다.")
    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<List<CommentResponseDto>> getComments(@PathVariable Long postId) {
        return ResponseEntity.ok(commentService.getCommentsByPost(postId));
    }
    @Operation(summary = "내가 쓴 댓글 조회", description = "현재 로그인한 사용자가 작성한 댓글 목록을 조회합니다.")
    @GetMapping("/comments/mycomments")
    public ResponseEntity<List<CommentResponseDto>> getMyComments() {
        return ResponseEntity.ok(commentService.getMyComments());
    }
    @Operation(summary = "댓글 수정", description = "댓글 ID로 댓글 내용을 수정합니다.")
    @PutMapping("/comments/{commentId}")
    public ResponseEntity<Map<String, Object>> updateComment(
            @PathVariable Long commentId,
            @RequestBody CommentUpdateRequestDto dto) {

        commentService.updateComment(commentId, dto);
        return ResponseEntity.ok(Map.of("status", "success", "message", "댓글 수정 완료"));
    }
    @Operation(summary = "댓글 삭제", description = "댓글 ID로 댓글을 삭제합니다.")
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<Map<String, Object>> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.ok(Map.of("status", "success", "message", "댓글 삭제 완료"));
    }
}
