package alpha.chupchup.repository;

import alpha.chupchup.entity.User;
import alpha.chupchup.entity.UserRecipeFavorite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRecipeFavoriteRepository extends JpaRepository<UserRecipeFavorite, Long> {
    List<UserRecipeFavorite> findAllByUser(User user);

    Optional<UserRecipeFavorite> findByUserAndRecipeId(User user, Long recipeId);
}
