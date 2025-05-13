package alpha.chupchup.entity.recipe;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "recipe")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipe_id")
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String recipeText1;

    @Column(columnDefinition = "TEXT")
    private String recipeText2;

    @Column(columnDefinition = "TEXT")
    private String recipeText3;

    @Column(columnDefinition = "TEXT")
    private String recipeText4;

    @Column(columnDefinition = "TEXT")
    private String recipeText5;

    @Column(columnDefinition = "TEXT")
    private String recipeText6;

    private Float calories;
    private Float carbohydrates;
    private Float protein;
    private Float fat;
    private Float sodium;

    private String foodImage;

    @Column(columnDefinition = "TEXT")
    private String ingredient;

    @Column(length = 50)
    private String foodType;

    @Builder.Default
    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserRecipeFavorite> favorites = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WeeklyMeal> weeklyMeals = new ArrayList<>();

    @CreationTimestamp
    private LocalDateTime createdAt;
}
