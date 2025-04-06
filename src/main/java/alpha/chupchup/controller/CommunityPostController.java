package alpha.chupchup.controller;

import alpha.chupchup.dto.community.*;
import alpha.chupchup.service.CommunityPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

}
