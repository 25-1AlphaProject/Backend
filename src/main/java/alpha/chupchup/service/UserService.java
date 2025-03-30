package alpha.chupchup.service;

import alpha.chupchup.dto.user.*;
import alpha.chupchup.entity.User;
import alpha.chupchup.repository.UserRepository;
import alpha.chupchup.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UserResponseDto signup(SignupRequestDto requestDto) {
        if (userRepository.existsByUsername(requestDto.getUsername())) {
            return new UserResponseDto("error", "중복된 아이디입니다.", null);
        }

        User user = new User();
        user.setUsername(requestDto.getUsername());
        user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        user.setName(requestDto.getName());
        user.setNickname(requestDto.getNickname());
        user.setPhoneNumber(requestDto.getPhoneNumber());

        userRepository.save(user);

        return new UserResponseDto("success", "회원가입 완료", user.getId());
    }

    public LoginResponseDto login(LoginRequestDto requestDto) {
        User user = userRepository.findByUsername(requestDto.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("아이디 또는 비밀번호 오류"));

        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("아이디 또는 비밀번호 오류");
        }

        String token = jwtUtil.createToken(user.getUsername());
        return new LoginResponseDto("success", "로그인 성공", token);
    }
}
