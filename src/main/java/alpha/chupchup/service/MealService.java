package alpha.chupchup.service;

import alpha.chupchup.dto.*;
import alpha.chupchup.entity.*;
import alpha.chupchup.repository.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MealService {
    private final MealRepository mealRepository;
    private final RealEatRepository realEatRepository;
    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;
    private final RestTemplate restTemplate;
    private final UserDetailRepository userDetailRepository;
    private final ObjectMapper objectMapper;
    @Value("{fast-api.url}")
    private String fastApiUrl;

    public List<MealDto> getOneDayMealsByDate(Long userId, LocalDate date) {
        List<WeeklyMeal> weeklyMeals = mealRepository.findAllByUserIdAndMealDate(
                userId, date
        );

        return weeklyMeals.stream()
                .map(meal -> {
                    Recipe recipe = meal.getRecipe();
                    return MealDto.builder()
                            .name(recipe.getName())
                            .recipeTexts(getRecipeTexts(recipe))
                            .calories(recipe.getCalories())
                            .carbohydrates(recipe.getCarbohydrates())
                            .protein(recipe.getProtein())
                            .fat(recipe.getFat())
                            .sodium(recipe.getSodium())
                            .foodImage(recipe.getFoodImage())
                            .ingredient(recipe.getIngredient())
                            .foodType(recipe.getFoodType())
                            .mealType(meal.getMealType())
                            .dateTime(meal.getCreatedAt())
                            .build();
                })
                .toList();
    }

    public List<MealDto> getOneDayRealEatsByDate(Long userId, LocalDate date) {

        List<RealEat> realEatList = realEatRepository.findAllByUserIdAndMealDate(
                userId, date
        );

        return realEatList.stream()
                .map(meal -> {
                    Recipe recipe = meal.getRecipe();
                    return MealDto.builder()
                            .name(recipe.getName())
                            .recipeTexts(getRecipeTexts(recipe))
                            .calories(recipe.getCalories())
                            .carbohydrates(recipe.getCarbohydrates())
                            .protein(recipe.getProtein())
                            .fat(recipe.getFat())
                            .sodium(recipe.getSodium())
                            .foodImage(recipe.getFoodImage())
                            .ingredient(recipe.getIngredient())
                            .foodType(recipe.getFoodType())
                            .mealType(meal.getMealType())
                            .dateTime(meal.getCreatedAt())
                            .build();
                })
                .toList();
    }

    @Transactional
    public void registerPreference(PreferenceRequestDto requestDto, Long userId) {
        RealEat realEat = realEatRepository.findByIdAndUserId(requestDto.getRealEatId(), userId)
                .orElseThrow(() -> new RuntimeException("해당 RealEat 기록이 존재하지 않습니다."));
        realEat.setPreference(requestDto.getPreference());
    }

    @Transactional
    public void deletePreference(Long userId, Long realEatId) {
        RealEat realEat = realEatRepository.findByIdAndUserId(realEatId, userId)
                .orElseThrow(() -> new RuntimeException("해당 RealEat 기록이 존재하지 않습니다."));
        realEat.setPreference(null);
    }

    public CookeryResponseDto getCookery(Long mealId) {
        WeeklyMeal meal = mealRepository.findById(mealId)
                .orElseThrow(() -> new RuntimeException("해당 식단이 존재하지 않습니다."));
        List<String> recipeText = getRecipeTexts(meal.getRecipe());
        return new CookeryResponseDto(recipeText);
    }

    @Transactional
    public void postRealEat(RealEatPostRequestDto requestDto, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        WeeklyMeal weeklyMeal = mealRepository.findById(requestDto.getMealId())
                .orElseThrow(() -> new RuntimeException("식단을 찾을 수 없습니다."));

        Recipe recipe = weeklyMeal.getRecipe();

        RealEat realEat = RealEat.builder()
                .user(user)
                .recipe(recipe)
                .weeklyMeal(weeklyMeal)
                .mealDate(requestDto.getMealDate())
                .customFoodCalories(requestDto.getFoodCalories())
                .mealPhoto(recipe.getFoodImage())
                .mealType(requestDto.getMealType())
                .build();

        realEatRepository.save(realEat);
    }

    @Transactional
    public CustomRealEatResponseDto postCustomRealEat(RealEatCustomRequestDto requestDto, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        FastApiCustomMealRequestDto request = FastApiCustomMealRequestDto.builder()
                .mealPhoto(requestDto.getMealPhoto())
                .amount(requestDto.getAmount())
                .build();

        ResponseEntity<FastApiCustomMealResponseDto> response = sendMealToFastApi(request);

        if (response.getStatusCode().is2xxSuccessful()) {
            FastApiCustomMealResponseDto responseDto = response.getBody();

            String mealName = responseDto.getMealName();
            float foodCalories = responseDto.getFoodCalories();

            RealEat realEat = RealEat.builder()
                    .user(user)
                    .mealPhoto(requestDto.getMealPhoto())
                    .customFoodCalories(foodCalories)
                    .customFoodName(mealName)
                    .mealDate(requestDto.getMealDate())
                    .mealType(requestDto.getMealType())
                    .build();

            realEatRepository.save(realEat);
        } else {
            throw new RuntimeException("FastApi 식단 요청 실패");
        }
        return CustomRealEatResponseDto.builder()
                .mealName(response.getBody().getMealName())
                .foodCalories(response.getBody().getFoodCalories())
                .build();
    }

    public ResponseEntity<FastApiCustomMealResponseDto> sendMealToFastApi(FastApiCustomMealRequestDto request) {
        HttpEntity<FastApiCustomMealRequestDto> entity = new HttpEntity<>(request);
        return restTemplate.exchange(
                fastApiUrl, HttpMethod.POST, entity, FastApiCustomMealResponseDto.class
        );
    }

    @Transactional
    public void deleteRealEatByRealEatId(User user, Long realEatId) {
        RealEat realEat = realEatRepository.findById(realEatId)
                .orElseThrow(() -> new RuntimeException("해당 실제 먹은 식단을 찾을 수 없습니다."));

        if (realEat.getUser().equals(user)) {
            realEatRepository.delete(realEat);
        } else {
            throw new RuntimeException("해당 유저의 식단이 아닌 것을 삭제하려고 합니다.");
        }
    }

    public List<MealDto> generateWeeklyMeal(User user) throws JsonProcessingException {
        UserDetail userDetail = userDetailRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("해당 유저 아이디의 유저디테일을 가져올 수 없습니다."));

        List<String> mealCount = objectMapper.readValue(userDetail.getMealCount(), new TypeReference<>() {});

        FastApiMealRequestDto fastApiRequest = FastApiMealRequestDto.builder()
                .user_id(user.getId())
                .gender(userDetail.getGender())
                .age(userDetail.getAge())
                .weight(userDetail.getWeight())
                .meal_count(mealCount)
                .target_calories(userDetail.getTargetCalories())
                .user_diet_info(userDetail.getUserDietInfo())
                .build();

        HttpEntity<FastApiMealRequestDto> entity = new HttpEntity<>(fastApiRequest);
        ResponseEntity<FastApiResponseDto> response = restTemplate.postForEntity(fastApiUrl, entity, FastApiResponseDto.class);

        if (response.getStatusCode() == HttpStatus.OK && response.getBody().isSuccess()) {
            LocalDate startDate = LocalDate.now();
            LocalDate endDate   = startDate.plusDays(6);

            List<WeeklyMeal> weeklyMeals = mealRepository.findByUserIdAndMealDateBetweenOrderByMealDateAsc(user.getId(), startDate, endDate);

            return weeklyMeals.stream()
                    .map(meal -> {
                        Recipe recipe = meal.getRecipe();
                        return MealDto.builder()
                                .name(recipe.getName())
                                .recipeTexts(getRecipeTexts(recipe))
                                .calories(recipe.getCalories())
                                .carbohydrates(recipe.getCarbohydrates())
                                .protein(recipe.getProtein())
                                .fat(recipe.getFat())
                                .sodium(recipe.getSodium())
                                .foodImage(recipe.getFoodImage())
                                .ingredient(recipe.getIngredient())
                                .foodType(recipe.getFoodType())
                                .mealType(meal.getMealType())
                                .dateTime(meal.getCreatedAt())
                                .build();
                    })
                    .toList();
        } else {
            throw new RuntimeException("FastAPI 식단 생성 실패");
        }
    }

    public List<IngredientLinksResponseDto> getIngredientLinks(Long recipeId) {
        String requestUrl = fastApiUrl + "/ingredient-links?recipeId=" + recipeId;

        HttpEntity<IngredientLinksRequestDto> body = new HttpEntity<>(getIngredientLinksRequest(recipeId));

        ResponseEntity<IngredientLinksResponseDto[]> responseEntity =
                restTemplate.postForEntity(requestUrl, body, IngredientLinksResponseDto[].class);

        if(responseEntity.getStatusCode() == HttpStatus.OK && responseEntity.getBody() != null) {
            return Arrays.asList(responseEntity.getBody());
        } else {
            throw new RuntimeException("재료 링크 조회에 실패했습니다.");
        }
    }

    public IngredientLinksRequestDto getIngredientLinksRequest(Long recipeId) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RuntimeException("레시피를 찾을 수 없습니다."));

        List<String> ingredients = Arrays.stream(recipe.getIngredient().split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .toList();

        return new IngredientLinksRequestDto(ingredients);
    }

    List<String> getRecipeTexts(Recipe recipe) {
        return Stream.of(
                        recipe.getRecipeText1(),
                        recipe.getRecipeText2(),
                        recipe.getRecipeText3(),
                        recipe.getRecipeText4(),
                        recipe.getRecipeText5(),
                        recipe.getRecipeText6()
                )
                .filter(Objects::nonNull)
                .toList();
    }
}
