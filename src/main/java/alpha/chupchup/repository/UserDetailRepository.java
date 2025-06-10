package alpha.chupchup.repository;

import alpha.chupchup.entity.user.User;
import alpha.chupchup.entity.user.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDetailRepository extends JpaRepository<UserDetail, Long> {
    Optional<UserDetail> findByUser(User user);
}
