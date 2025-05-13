// ===== UserDetailService.java =====
package alpha.chupchup.service;

import alpha.chupchup.dto.user.*;
import alpha.chupchup.entity.User;
import alpha.chupchup.entity.UserDetail;
import alpha.chupchup.repository.UserDetailRepository;
import alpha.chupchup.repository.UserRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailService {
    private final UserRepository userRepository;
    private final UserDetailRepository userDetailRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

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
        detail.setHeight(dto.getHeight());
        detail.setWeight(dto.getWeight());
        detail.setTargetCalories(dto.getTargetCalories());
        detail.setHealthGoal(dto.getHealthGoal());

        try {
            detail.setMealCount(objectMapper.writeValueAsString(dto.getMealCount()));
            detail.setUserDietInfo(objectMapper.writeValueAsString(dto.getUserDietInfo()));
        } catch (Exception e) {
            throw new RuntimeException("직렬화 실패", e);
        }

        userDetailRepository.save(detail);
    }

    public UserDetailResponseDto getDietInfo() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("사용자 정보가 없습니다."));

        UserDetail detail = userDetailRepository.findByUser(user)
                .orElseThrow(() -> new IllegalStateException("식단 정보가 없습니다."));

        List<String> mealCount;
        UserDietInfoDto dietInfo;
        try {
            mealCount = objectMapper.readValue(detail.getMealCount(), new TypeReference<>() {});
            dietInfo = objectMapper.readValue(detail.getUserDietInfo(), UserDietInfoDto.class);
        } catch (Exception e) {
            throw new RuntimeException("역직렬화 실패", e);
        }
        return new UserDetailResponseDto(
                detail.getAge(),
                detail.getHeight(),
                detail.getWeight(),
                detail.getGender(),
                mealCount,
                detail.getTargetCalories(),
                dietInfo,
                detail.getHealthGoal()
        );
    }

    @Transactional
    public void updateDietInfo(DietInfoRequestDto dto) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("사용자 정보가 없습니다."));

        UserDetail detail = userDetailRepository.findByUser(user)
                .orElseThrow(() -> new IllegalStateException("식단 정보가 없습니다."));

        detail.setAge(dto.getAge());
        detail.setGender(dto.getGender());
        detail.setHeight(dto.getHeight());
        detail.setWeight(dto.getWeight());
        detail.setTargetCalories(dto.getTargetCalories());
        detail.setHealthGoal(dto.getHealthGoal());

        try {
            detail.setMealCount(objectMapper.writeValueAsString(dto.getMealCount()));
            detail.setUserDietInfo(objectMapper.writeValueAsString(dto.getUserDietInfo()));
        } catch (Exception e) {
            throw new RuntimeException("직렬화 실패", e);
        }
    }
}
