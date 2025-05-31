package alpha.chupchup.controller;

import alpha.chupchup.dto.ResponseDto;
import alpha.chupchup.dto.meal.*;
import alpha.chupchup.dto.recipe.CookeryResponseDto;
import alpha.chupchup.entity.user.User;
import alpha.chupchup.security.CustomUserDetails;
import alpha.chupchup.service.MealService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/meal")
@Tag(name = "Meal-Controller", description = "Meal-관련-API")
public class MealController {

    private final MealService mealService;

    @GetMapping("/{date}")
    @Operation(summary = "해당 날짜의 생성된 식단 조회", description = "해당 날짜의 생성된 식단을 조회합니다.")
    public ResponseEntity<ResponseDto<?>> getMealsByDate(
            @Parameter(
                    description = "조회할 날짜",
                    required = true
            )
            @PathVariable("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        try {
            Long userId = userDetails.getUser().getId();
            List<MealDto> meals = mealService.getOneDayMealsByDate(userId, date);
            return ResponseEntity.ok(ResponseDto.success(meals));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseDto.error("식단 조회에 실패했습니다."));
        }
    }

    @GetMapping("/real-eat/{date}")
    @Operation(summary = "해당 날짜 실제로 먹은 식단 조회", description = "해당 날짜의 실제로 먹은 식단을 조회합니다.")
    public ResponseEntity<ResponseDto<?>> getRealEatsByDate(
            @Parameter(
                    description = "조회할 날짜",
                    required = true
            )
            @PathVariable("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        try {
            Long userId = userDetails.getUser().getId();
            List<RealEatResponseDto> meals = mealService.getOneDayRealEatsByDate(userId, date);
            return ResponseEntity.ok(ResponseDto.success(meals));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseDto.error("실제로 먹은 식단 조회에 실패했습니다."));
        }
    }

    @PostMapping("/preference")
    @Operation(summary = "선호도 등록하기", description = "식단에 선호도를 등록합니다.")
    public ResponseEntity<ResponseDto<String>> registerPreference(
            @RequestBody PreferenceRequestDto requestDto,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        try {
            Long userId = userDetails.getUser().getId();
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
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        try {
            Long userId = userDetails.getUser().getId();
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
    @Operation(summary = "실제 먹은 식단 추가하기[추천받은 식단을 추가하는 경우]", description = "추천받은 식단 중에서 실제로 먹은 식단을 추가합니다.")
    public ResponseEntity<ResponseDto<String>> postRealEat(
            @RequestBody RealEatPostRequestDto requestDto,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        try {
            Long userId = userDetails.getUser().getId();
            mealService.postRealEat(requestDto, userId);
            return ResponseEntity.ok(ResponseDto.success("식단이 정상적으로 등록되었습니다."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseDto.error("식단 등록에 실패했습니다."));
        }
    }

    @PostMapping("/real-eat/custom")
    @Operation(summary = "실제 먹은 식단 추가하기[자신이 먹은 음식을 추가하는 경우]", description = "추천 받은 식단 외에 실제로 먹은 식단을 추가합니다.")
    public ResponseEntity<ResponseDto<?>> postCustomRealEat(
            @RequestBody RealEatCustomRequestDto requestDto,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        try {
            Long userId = userDetails.getUser().getId();
            CustomRealEatResponseDto response = mealService.postCustomRealEat(requestDto, userId);
            return ResponseEntity.ok(ResponseDto.success(response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseDto.error("커스텀 식단 등록에 실패했습니다."));
        }
    }

    @PostMapping("/real-eat/write")
    @Operation(summary = "실제 먹은 식단 추가하기[수기로 추가하는 경우]", description = "수기로 등록한 식단을 추가합니다.")
    public ResponseEntity<ResponseDto<String>> postRealEatWrite(
            @RequestBody RealEatWriteRequestDto requestDto,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        try {
            Long userId = userDetails.getUser().getId();
            mealService.postWriteRealEat(requestDto, userId);
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
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        try {
            User user = userDetails.getUser();
            mealService.deleteRealEatByRealEatId(user, realEatId);
            return ResponseEntity.ok(ResponseDto.success("식단이 정상적으로 삭제되었습니다."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseDto.error("식단 삭제에 실패했습니다."));
        }
    }

    @GetMapping("/weekly")
    @Operation(summary = "일주일 식단 생성하기", description = "일주일 식단을 생성합니다.")
    public ResponseEntity<ResponseDto<?>> generateWeeklyMeal(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        try {
            User user = userDetails.getUser();
            return ResponseEntity.ok(ResponseDto.success(mealService.generateWeeklyMeal(user)));
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

    @PutMapping("/real-eat/edit/{realEatId}")
    @Operation(summary = "실제로 먹은 식단 수정하기", description = "실제로 먹은 식단 정보를 수정합니다.")
    public ResponseEntity<ResponseDto<String>> editRealEat(
            @RequestBody RealEatEditRequestDto requestDto,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Parameter(
                    description = "수정할 실제 식단 아이디",
                    required = true
            )
            @PathVariable Long realEatId
    ) {
        try {
            Long userId = userDetails.getUser().getId();
            mealService.editRealEat(userId, realEatId, requestDto);
            return ResponseEntity.ok(ResponseDto.success("실제로 먹은 식단이 정상적으로 수정되었습니다."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseDto.error("실제로 먹은 식단 수정에 실패했습니다."));
        }
    }
}
