package alpha.chupchup.repository;

import alpha.chupchup.entity.RealEat;
import alpha.chupchup.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface RealEatRepository extends JpaRepository<RealEat, Long> {
    List<RealEat> findAllByUserAndMealDateOrderByIdAsc(User user, LocalDateTime dateTime);

    Optional<RealEat> findByIdAndUser(Long realEatId, User user);
}
