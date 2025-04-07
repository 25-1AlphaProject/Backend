package alpha.chupchup.repository;

import alpha.chupchup.entity.WeeklyMeal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface MealRepository extends JpaRepository<WeeklyMeal, Long> {
    List<WeeklyMeal> findByUserIdAndCreatedAtBetween(Long userId, LocalDateTime startDay, LocalDateTime startDayTomorrow);
}
