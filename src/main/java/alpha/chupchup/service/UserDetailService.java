package alpha.chupchup.service;

import alpha.chupchup.dto.user.*;
import alpha.chupchup.entity.User;
import alpha.chupchup.entity.UserDetail;
import alpha.chupchup.repository.UserDetailRepository;
import alpha.chupchup.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailService {

    private final UserRepository userRepository;
    private final UserDetailRepository userDetailRepository;

    public void saveDietInfo(DietInfoRequestDto dto) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("사용자 정보가 없습니다."));

        if (userDetailRepository.findByUser(user).isPresent()) {
            throw new IllegalStateException("이미 식단 정보가 등록되어 있습니다.");
        }

        UserDetail detail = new UserDetail();
        detail.setUser(user);
        detail.setAge(dto.getAge());
        detail.setGender(dto.getGender());
        detail.setMealCount(dto.getMealCount());
        detail.setHeight(dto.getHeight());
        detail.setWeight(dto.getWeight());
        detail.setTargetCalories(dto.getTargetCalories());
        detail.setUserDietInfo(dto.getUserDietInfo());

        userDetailRepository.save(detail);
    }

    public UserDetailResponseDto getDietInfo() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("사용자 정보가 없습니다."));

        UserDetail detail = userDetailRepository.findByUser(user)
                .orElseThrow(() -> new IllegalStateException("식단 정보가 없습니다."));

        return new UserDetailResponseDto(
                detail.getAge(),
                detail.getHeight(),
                detail.getWeight(),
                detail.getGender(),
                detail.getMealCount(),
                detail.getTargetCalories(),
                detail.getUserDietInfo()
        );
    }

    public void updateDietInfo(DietInfoRequestDto dto) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("사용자 정보가 없습니다."));

        UserDetail detail = userDetailRepository.findByUser(user)
                .orElseThrow(() -> new IllegalStateException("식단 정보가 없습니다."));

        detail.setAge(dto.getAge());
        detail.setGender(dto.getGender());
        detail.setMealCount(dto.getMealCount());
        detail.setHeight(dto.getHeight());
        detail.setWeight(dto.getWeight());
        detail.setTargetCalories(dto.getTargetCalories());
        detail.setUserDietInfo(dto.getUserDietInfo());
    }
}
