package alpha.chupchup.controller;

import alpha.chupchup.dto.user.*;
import alpha.chupchup.service.UserService;
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

    //회원가입, 로그인 엔드포인트
    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> signup(@RequestBody SignupRequestDto requestDto) {
        UserResponseDto response = userService.signup(requestDto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto requestDto) {
        LoginResponseDto response = userService.login(requestDto);
        return ResponseEntity.ok(response);
    }

    // 사용자 정보 관련 엔드포인트
    @GetMapping("/info")
    public ResponseEntity<Map<String, Object>> getUserInfo() {
        UserInfoResponseDto userInfo = userService.getMyInfo();
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "회원 정보 조회 성공");
        response.put("data", userInfo);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/info")
    public ResponseEntity<Map<String, Object>> updateUserInfo(@RequestBody UserInfoUpdateRequestDto dto) {
        userService.updateMyInfo(dto);
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "회원 정보 수정 완료");
        response.put("data", null);
        return ResponseEntity.ok(response);
    }

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