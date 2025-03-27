package alpha.chupchup.repository;

import alpha.chupchup.entity.RealEat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface RealEatRepository extends JpaRepository<RealEat, Long> {
    List<RealEat> findAllByMealDateOrderByIdAsc(LocalDateTime dateTime);
}
