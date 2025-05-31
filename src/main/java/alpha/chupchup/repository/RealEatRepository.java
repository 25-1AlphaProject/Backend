package alpha.chupchup.repository;

import alpha.chupchup.entity.recipe.RealEat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface RealEatRepository extends JpaRepository<RealEat, Long> {

    Optional<RealEat> findByIdAndUserId(Long realEatId, Long userId);

    List<RealEat> findAllByUser_IdAndMealDate(Long userId, LocalDate mealDate);
}
