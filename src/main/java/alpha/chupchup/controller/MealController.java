package alpha.chupchup.controller;

import alpha.chupchup.dto.*;
import alpha.chupchup.dto.CookeryResponseDto;
import alpha.chupchup.service.MealService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/meal")
@Tag(name = "Meal-Controller", description = "Meal-관련-API")
public class MealController {

    private final MealService mealService;

    @GetMapping("/{date}")
    @Operation(summary = "해당 날짜 식단 조회", description = "해당 날짜의 식단을 조회합니다.")
    public ResponseEntity<ResponseDto<?>> getMealsByDate(
            @Parameter(
                    description = "조회할 날짜",
                    required = true
            )
            @PathVariable("date") LocalDateTime dateTime,
            HttpServletRequest servletRequest
    ) {
        try {
            Long userId = 0L;
            List<MealDto> meals = mealService.getOneDayMealByDate(userId, dateTime);
            return ResponseEntity.ok(ResponseDto.success(meals));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseDto.error("식단 조회에 실패했습니다."));
        }
    }

    @PostMapping("/preference")
    @Operation(summary = "선호도 등록하기", description = "식단에 선호도를 등록합니다.")
    public ResponseEntity<ResponseDto<String>> registerPreference(
            @RequestBody PreferenceRequestDto requestDto,
            HttpServletRequest servletRequest
    ) {
        try {
            Long userId = 0L;
            mealService.registerPreference(requestDto, userId);
            return ResponseEntity.ok(ResponseDto.success("선호도가 정상적으로 등록되었습니다."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseDto.error("선호도 등록에 실패했습니다."));
        }
    }

    @DeleteMapping("/preference/{mealId}")
    @Operation(summary = "선호도 삭제하기", description = "식단에 선호도를 제거합니다.")
    public ResponseEntity<ResponseDto<String>> deletePreference(
            @Parameter(
                    description = "삭제할 실제 식단 아이디",
                    required = true
            )
            @PathVariable("mealId") Long realEatId,
            HttpServletRequest servletRequest
    ) {
        try {
            Long userId = 0L;
            mealService.deletePreference(userId, realEatId);
            return ResponseEntity.ok(ResponseDto.success("선호도가 정상적으로 삭제되었습니다."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseDto.error("선호도 제거에 실패했습니다."));
        }
    }

    @GetMapping("/cookery/{mealId}")
    @Operation(summary = "조리법 조회하기", description = "조리법을 조회합니다.")
    public ResponseEntity<ResponseDto<?>> getCookery(
            @Parameter(
                    description = "조리법을 조회할 식단 아이디",
                    required = true
            )
            @PathVariable Long mealId
    ) {
        try {
            CookeryResponseDto cookeryResponseDto = mealService.getCookery(mealId);
            return ResponseEntity.ok(ResponseDto.success(cookeryResponseDto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseDto.error("조리법 조회에 실패했습니다."));
        }
    }

    @PostMapping("/real-eat")
    @Operation(summary = "실제 먹은 식단 추가하기", description = "실제로 먹은 식단을 추가합니다.")
    public ResponseEntity<ResponseDto<String>> postRealEat(@RequestBody RealEatPostRequestDto requestDto) {
        try {
            mealService.postRealEat(requestDto);
            return ResponseEntity.ok(ResponseDto.success("식단이 정상적으로 등록되었습니다."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseDto.error("식단 등록에 실패했습니다."));
        }
    }

    @DeleteMapping("/{realEatId}")
    @Operation(summary = "실제 먹은 식단 제거하기", description = "실제로 먹은 식단을 제거합니다.")
    public ResponseEntity<ResponseDto<String>> deleteRealEat(
            @Parameter(
                    description = "삭제할 실제 식단 아이디",
                    required = true
            )
            @PathVariable Long realEatId,
            HttpServletRequest servletRequest
    ) {
        try {
            Long userId = 0L;
            mealService.deleteRealEatByRealEatId(userId, realEatId);
            return ResponseEntity.ok(ResponseDto.success("식단이 정상적으로 삭제되었습니다."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseDto.error("식단 삭제에 실패했습니다."));
        }
    }

    @GetMapping("/weekly")
    @Operation(summary = "일주일 식단 생성하기", description = "일주일 식단을 생성합니다.")
    public ResponseEntity<ResponseDto<?>> generateWeeklyMeal(HttpServletRequest servletRequest) {
        try {
            Long userId = 0L;
            return ResponseEntity.ok(ResponseDto.success(mealService.generateWeeklyMeal(userId)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseDto.error("식단 생성에 실패했습니다."));
        }
    }

    @GetMapping("/ingredient-links/{recipeId}")
    @Operation(summary = "재료 링크 조회하기", description = "레시피에 해당되는 재료들의 링크를 조회합니다.")
    public ResponseEntity<ResponseDto<?>> getIngredientLinks(
            @Parameter(
                    description = "조회할 레시피 아이디",
                    required = true
            )
            @PathVariable Long recipeId
    ) {
        try {
            return ResponseEntity.ok(ResponseDto.success(mealService.getIngredientLinks(recipeId)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseDto.error("재료 링크 조회에 실패했습니다."));
        }
    }
}
