package alpha.chupchup.controller;

import alpha.chupchup.dto.user.*;
import alpha.chupchup.service.UserDetailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users/diet-info")
@RequiredArgsConstructor
public class UserDetailController {

    private final UserDetailService userDetailService;

    @Operation(summary = "식단 정보 저장", description = "최초 입력 시 사용자의 식단 설정 정보를 저장합니다.", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping
    public ResponseEntity<String> saveDietInfo(@RequestBody DietInfoRequestDto dto) {
        userDetailService.saveDietInfo(dto);
        return ResponseEntity.ok("식단 정보 저장 완료");
    }

    @Operation(summary = "식단 정보 조회", description = "나이, 키, 몸무게, 성별, 식사 횟수, 건강 목표 등 사용자의 식단 설정을 조회합니다.", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping
    public ResponseEntity<UserDetailResponseDto> getDietInfo() {
        return ResponseEntity.ok(userDetailService.getDietInfo());
    }

    @Operation(summary = "식단 정보 수정", description = "사용자의 기존 식단 설정 정보를 수정합니다.", security = @SecurityRequirement(name = "bearerAuth"))
    @PutMapping
    public ResponseEntity<String> updateDietInfo(@RequestBody DietInfoRequestDto dto) {
        userDetailService.updateDietInfo(dto);
        return ResponseEntity.ok("식단 정보 수정 완료");
    }
}