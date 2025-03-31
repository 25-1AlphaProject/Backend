package alpha.chupchup.repository;

import alpha.chupchup.entity.Recipe;
import alpha.chupchup.entity.User;
import alpha.chupchup.entity.UserRecipeFavorite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRecipeFavoriteRepository extends JpaRepository<UserRecipeFavorite, Long> {
    Optional<UserRecipeFavorite> findByUserAndRecipe(User user, Recipe recipe);
    List<UserRecipeFavorite> findAllByUser(User user);
}
