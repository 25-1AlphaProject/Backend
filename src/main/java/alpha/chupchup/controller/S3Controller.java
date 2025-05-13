package alpha.chupchup.controller;

import alpha.chupchup.dto.ResponseDto;
import alpha.chupchup.service.S3Service;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/s3")
@RequiredArgsConstructor
public class S3Controller {
    private final S3Service s3Service;

    @GetMapping("/presigned-url")
    @Operation(summary = "s3 presigned url 발급받기", description = "presigned url을 발급합니다.")
    public ResponseEntity<ResponseDto<String>> presignedUrl(
            @RequestParam String objectKey
    ) {
        String url = s3Service.generatePresignedPutUrl(objectKey);
        return ResponseEntity.ok(ResponseDto.success(url));
    }
}
