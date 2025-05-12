package alpha.chupchup.repository;

import alpha.chupchup.entity.recipe.WeeklyMeal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface MealRepository extends JpaRepository<WeeklyMeal, Long> {
    List<WeeklyMeal> findByUserIdAndMealDateBetweenOrderByMealDateAsc(Long userId, LocalDate startDay, LocalDate startDayTomorrow);

    List<WeeklyMeal> findAllByUserIdAndMealDate(Long userId, LocalDate date);
}
