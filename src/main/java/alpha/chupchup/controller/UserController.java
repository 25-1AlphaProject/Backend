package alpha.chupchup.controller;

import alpha.chupchup.dto.user.*;
import alpha.chupchup.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

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
}