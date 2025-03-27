package alpha.chupchup.controller;

import alpha.chupchup.dto.*;
import alpha.chupchup.service.MealService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MealController {

    private final MealService mealService;

    @GetMapping("/api/meal/{date}")
    public ResponseEntity<ResponseDto<?>> getMealsByDate(@PathVariable("date") LocalDateTime dateTime) {
        try {
            List<MealDto> meals = mealService.getOneDayMealByDate(dateTime);
            return ResponseEntity.ok(ResponseDto.success(meals));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseDto.error("식단 조회에 실패했습니다."));
        }
    }

    @PostMapping("/api/meal/feedback")
    public ResponseEntity<ResponseDto<String>> registerFeedback(@RequestBody PreferenceRequestDto requestDto) {
        try {
            mealService.registerPreference(requestDto);
            return ResponseEntity.ok(ResponseDto.success("피드백이 정상적으로 등록되었습니다."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseDto.error("피드백 등록에 실패했습니다."));
        }
    }

    @DeleteMapping("/api/meal/feedback/{mealId}")
    public ResponseEntity<ResponseDto<String>> deleteFeedback(@PathVariable Long realEatId) {
        try {
            mealService.deletePreference(realEatId);
            return ResponseEntity.ok(ResponseDto.success("피드백이 정상적으로 삭제되었습니다."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseDto.error("피드백 삭제에 실패했습니다."));
        }
    }

    @GetMapping("/api/meal/cookery")
    public ResponseEntity<ResponseDto<?>> getCookery(@PathVariable Long mealId) {
        try {
            CookeryResponseDto cookeryResponseDto = mealService.getCookery(mealId);
            return ResponseEntity.ok(ResponseDto.success(cookeryResponseDto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseDto.error("조리법 조회에 실패했습니다."));
        }
    }

    @PostMapping("/api/meal/real-eat")
    public ResponseEntity<ResponseDto<String>> postRealEat(@RequestBody RealEatPostRequestDto requestDto) {
        try {
            mealService.postRealEat(requestDto);
            return ResponseEntity.ok(ResponseDto.success("식단이 정상적으로 등록되었습니다."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseDto.error("식단 등록에 실패했습니다."));
        }
    }
}
