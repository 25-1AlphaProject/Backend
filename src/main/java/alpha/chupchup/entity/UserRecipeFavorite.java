package alpha.chupchup.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_recipe_favorite")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class UserRecipeFavorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id", nullable = false)
    private Recipe recipe;

    @CreationTimestamp
    private LocalDateTime createdAt;

//    public void setRecipe(Recipe recipe) {
//        this.recipe = recipe;
//        if (recipe != null && !recipe.getFavorites().contains(this)) {
//            recipe.getFavorites().add(this);
//        }
//    }
}

