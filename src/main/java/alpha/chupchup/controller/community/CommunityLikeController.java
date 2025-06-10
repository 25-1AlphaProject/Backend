package alpha.chupchup.controller.community;

import alpha.chupchup.dto.community.response.PostListItemDto;
import alpha.chupchup.service.community.CommunityLikeService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/community/posts")
@RequiredArgsConstructor
public class CommunityLikeController {

    private final CommunityLikeService likeService;

    @Operation(summary = "좋아요 토글", description = "게시글 ID에 대해 좋아요를 누르거나 취소합니다.")
    @PostMapping("/{postId}/likes")
    public ResponseEntity<Map<String, Object>> toggleLike(@PathVariable Long postId) {
        int likeCount = likeService.toggleLike(postId);
        return ResponseEntity.ok(Map.of(
                "status", "success",
                "message", "좋아요 토글 완료",
                "likeCount", likeCount
        ));
    }
    @Operation(summary = "내가 좋아요한 게시글 목록 조회", description = "현재 로그인한 사용자가 좋아요한 게시글들을 조회합니다.")
    @GetMapping("/liked")
    public ResponseEntity<List<PostListItemDto>> getLikedPosts() {
        return ResponseEntity.ok(likeService.getLikedPostsByCurrentUser());
    }
}
