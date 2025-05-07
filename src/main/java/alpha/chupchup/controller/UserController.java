package alpha.chupchup.controller;

import alpha.chupchup.dto.user.*;
import alpha.chupchup.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "회원가입", description = "사용자 정보를 입력받아 회원가입을 진행합니다.")
    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> signup(@RequestBody SignupRequestDto requestDto) {
        UserResponseDto response = userService.signup(requestDto);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "로그인", description = "아이디와 비밀번호로 로그인하고 JWT 토큰을 반환합니다.")
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto requestDto) {
        LoginResponseDto response = userService.login(requestDto);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "내 정보 조회", description = "로그인된 사용자의 기본 정보를 조회합니다.", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/info")
    public ResponseEntity<Map<String, Object>> getUserInfo() {
        UserInfoResponseDto userInfo = userService.getMyInfo();
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "회원 정보 조회 성공");
        response.put("data", userInfo);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "내 정보 수정", description = "닉네임, 비밀번호, 프로필 이미지를 수정합니다.", security = @SecurityRequirement(name = "bearerAuth"))
    @PutMapping("/info")
    public ResponseEntity<Map<String, Object>> updateUserInfo(@RequestBody UserInfoUpdateRequestDto dto) {
        userService.updateMyInfo(dto);
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "회원 정보 수정 완료");
        response.put("data", null);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "회원 탈퇴", description = "현재 로그인된 계정을 즉시 삭제합니다.", security = @SecurityRequirement(name = "bearerAuth"))
    @DeleteMapping("/info")
    public ResponseEntity<Map<String, Object>> deleteUserInfo() {
        userService.deleteMyAccount();
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "회원 탈퇴 완료");
        response.put("data", null);
        return ResponseEntity.ok(response);
    }
}