package alpha.chupchup.controller;

import alpha.chupchup.dto.community.request.CommentCreateRequestDto;
import alpha.chupchup.dto.community.response.CommentResponseDto;
import alpha.chupchup.dto.community.request.CommentUpdateRequestDto;
import alpha.chupchup.service.CommunityCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

@RestController
@RequestMapping("/api/community/posts/{postId}/comments")
@RequiredArgsConstructor
public class CommunityCommentController {

    private final CommunityCommentService commentService;

    // (대)댓글 작성
    @PostMapping
    public ResponseEntity<Map<String, Object>> createComment(
            @PathVariable Long postId,
            @RequestBody CommentCreateRequestDto dto) {

        commentService.createComment(postId, dto);

        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "댓글 작성 완료");

        return ResponseEntity.ok(response);
    }
    // (대)댓글 조회
    @GetMapping
    public ResponseEntity<List<CommentResponseDto>> getComments(@PathVariable Long postId) {
        List<CommentResponseDto> response = commentService.getCommentsByPost(postId);
        return ResponseEntity.ok(response);
    }
    // (대)댓글 수정
    @PutMapping("/comments/{commentId}")
    public ResponseEntity<Map<String, Object>> updateComment(
            @PathVariable Long commentId,
            @RequestBody CommentUpdateRequestDto dto) {

        commentService.updateComment(commentId, dto);

        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "댓글 수정 완료");

        return ResponseEntity.ok(response);
    }
    // (대)댓글 삭제
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<Map<String, Object>> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);

        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "댓글 삭제 완료");

        return ResponseEntity.ok(response);
    }

}
