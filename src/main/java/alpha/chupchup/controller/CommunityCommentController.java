package alpha.chupchup.controller;

import alpha.chupchup.dto.community.CommentCreateRequestDto;
import alpha.chupchup.service.CommunityCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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
}
