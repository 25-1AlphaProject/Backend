package alpha.chupchup.repository;

import alpha.chupchup.entity.recipe.WeeklyMeal;
import alpha.chupchup.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface MealRepository extends JpaRepository<WeeklyMeal, Long> {
    List<WeeklyMeal> findByUserAndMealDateBetweenOrderByMealDateAsc(User user, LocalDate startDate, LocalDate endDate);

    List<WeeklyMeal> findAllByUserIdAndMealDate(Long userId, LocalDate date);
}
