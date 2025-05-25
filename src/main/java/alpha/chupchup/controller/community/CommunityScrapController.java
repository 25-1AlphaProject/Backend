package alpha.chupchup.controller.community;

import alpha.chupchup.dto.community.response.PostListItemDto;
import alpha.chupchup.service.community.CommunityScrapService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/community/posts")
@RequiredArgsConstructor
public class CommunityScrapController {

    private final CommunityScrapService scrapService;

    @Operation(summary = "스크랩 토글", description = "게시글 ID에 대해 스크랩을 누르거나 취소합니다.")
    @PostMapping("/{postId}/scraps")
    public ResponseEntity<Map<String, Object>> toggleScrap(@PathVariable Long postId) {
        int scrapCount = scrapService.toggleScrap(postId);
        return ResponseEntity.ok(Map.of(
                "status", "success",
                "message", "스크랩 토글 완료",
                "scrapCount", scrapCount
        ));
    }
    @Operation(summary = "내가 스크랩한 게시글 목록 조회", description = "현재 로그인한 사용자가 스크랩한 게시글들을 조회합니다.")
    @GetMapping("/scrapped")
    public ResponseEntity<List<PostListItemDto>> getScrappedPosts() {
        return ResponseEntity.ok(scrapService.getScrappedPostsByCurrentUser());
    }
}
