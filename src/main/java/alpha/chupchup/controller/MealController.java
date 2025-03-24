package alpha.chupchup.controller;

import alpha.chupchup.dto.MealDto;
import alpha.chupchup.dto.ResponseDto;
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
}
