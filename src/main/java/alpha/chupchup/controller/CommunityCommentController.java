package alpha.chupchup.controller;

import alpha.chupchup.dto.community.CommentCreateRequestDto;
import alpha.chupchup.dto.community.CommentResponseDto;
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

}
