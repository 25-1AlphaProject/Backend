package alpha.chupchup.repository;

import alpha.chupchup.entity.RealEat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface RealEatRepository extends JpaRepository<RealEat, Long> {
    List<RealEat> findAllByUserIdAndMealDateOrderByIdAsc(Long userId, LocalDateTime dateTime);

    Optional<RealEat> findByIdAndUserId(Long realEatId, Long userId);
}
