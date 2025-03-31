package alpha.chupchup.repository;

import alpha.chupchup.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    @Query("SELECT r FROM Recipe r WHERE " +
            "LOWER(r.name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(r.recipeText) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(r.ingredient) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Recipe> searchRecipes(String keyword);
}
