package alpha.chupchup.controller;

import alpha.chupchup.dto.community.*;
import alpha.chupchup.service.CommunityPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/community/posts")
@RequiredArgsConstructor
public class CommunityPostController {

    private final CommunityPostService postService;

    // 게시글 작성
    @PostMapping
    public ResponseEntity<PostCreateResponseDto> createPost(@RequestBody PostCreateRequestDto dto) {
        PostCreateResponseDto response = postService.createPost(dto);
        return ResponseEntity.ok(response);
    }
    // 게시글 조회
    @GetMapping("/{postId}")
    public ResponseEntity<PostDetailResponseDto> getPostDetail(@PathVariable Long postId) {
        PostDetailResponseDto response = postService.getPostDetail(postId);
        return ResponseEntity.ok(response);
    }
    // 게시글 수정
    @PutMapping("/{postId}")
    public ResponseEntity<Map<String, Object>> updatePost(
            @PathVariable Long postId,
            @RequestBody PostUpdateRequestDto dto) {
        postService.updatePost(postId, dto);

        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "게시글 수정 완료");
        return ResponseEntity.ok(response);
    }
    // 게시글 삭제
    @DeleteMapping("/{postId}")
    public ResponseEntity<Map<String, Object>> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);

        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "게시글 삭제 완료");
        return ResponseEntity.ok(response);
    }
    // 게시글 목록 조회 페이징
    @GetMapping
    public ResponseEntity<Map<String, Object>> getPostList(
            @RequestParam(defaultValue = "recent") String sort,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Map<String, Object> response = postService.getPostList(sort, page, size);
        return ResponseEntity.ok(response);
    }



}
