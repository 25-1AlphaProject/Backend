package alpha.chupchup.controller.community;

import alpha.chupchup.dto.community.request.PostCreateRequestDto;
import alpha.chupchup.dto.community.request.PostUpdateRequestDto;
import alpha.chupchup.dto.community.response.PostCreateResponseDto;
import alpha.chupchup.dto.community.response.PostDetailResponseDto;
import alpha.chupchup.dto.community.response.PostListItemDto;
import alpha.chupchup.service.community.CommunityPostService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/community/posts")
@RequiredArgsConstructor
public class CommunityPostController {

    private final CommunityPostService postService;

    @Operation(summary = "게시글 작성", description = "제목, 내용, 이미지 URL 리스트를 포함한 게시글을 작성합니다.")
    @PostMapping
    public ResponseEntity<PostCreateResponseDto> createPost(@RequestBody PostCreateRequestDto dto) {
        return ResponseEntity.ok(postService.createPost(dto));
    }
    @Operation(summary = "게시글 상세 조회", description = "게시글 ID로 게시글 상세 내용을 조회합니다.")
    @GetMapping("/{postId}")
    public ResponseEntity<PostDetailResponseDto> getPostDetail(@PathVariable Long postId) {
        return ResponseEntity.ok(postService.getPostDetail(postId));
    }
    @Operation(summary = "게시글 수정", description = "게시글 ID와 수정할 제목 및 내용을 입력받아 게시글을 수정합니다.")
    @PutMapping("/{postId}")
    public ResponseEntity<Map<String, Object>> updatePost(
            @PathVariable Long postId, @RequestBody PostUpdateRequestDto dto) {
        postService.updatePost(postId, dto);
        return ResponseEntity.ok(Map.of("status", "success", "message", "게시글 수정 완료"));
    }
    @Operation(summary = "게시글 삭제", description = "게시글 ID로 해당 게시글을 삭제합니다.")
    @DeleteMapping("/{postId}")
    public ResponseEntity<Map<String, Object>> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return ResponseEntity.ok(Map.of("status", "success", "message", "게시글 삭제 완료"));
    }
    @Operation(summary = "게시글 목록 조회", description = "정렬 조건 (recent, popular, scrapped)과 페이징 정보에 따라 게시글 목록을 조회합니다.")
    @GetMapping
    public ResponseEntity<Map<String, Object>> getPostList(
            @RequestParam(defaultValue = "recent") String sort,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(postService.getPostList(sort, page, size));
    }
    @Operation(summary = "내가 쓴 게시글 조회", description = "현재 로그인한 사용자가 작성한 게시글 목록을 조회합니다.")
    @GetMapping("/myposts")
    public ResponseEntity<List<PostListItemDto>> getMyPosts() {
        return ResponseEntity.ok(postService.getMyPosts());
    }
}
