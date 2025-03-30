package alpha.chupchup.controller;

import alpha.chupchup.dto.user.*;
import alpha.chupchup.service.UserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users/diet-info")
@RequiredArgsConstructor
public class UserDetailController {

    private final UserDetailService userDetailService;

    @PostMapping
    public ResponseEntity<String> saveDietInfo(@RequestBody DietInfoRequestDto dto) {
        userDetailService.saveDietInfo(dto);
        return ResponseEntity.ok("식단 정보 저장 완료");
    }

    @GetMapping
    public ResponseEntity<UserDetailResponseDto> getDietInfo() {
        return ResponseEntity.ok(userDetailService.getDietInfo());
    }

    @PutMapping
    public ResponseEntity<String> updateDietInfo(@RequestBody DietInfoRequestDto dto) {
        userDetailService.updateDietInfo(dto);
        return ResponseEntity.ok("식단 정보 수정 완료");
    }
}