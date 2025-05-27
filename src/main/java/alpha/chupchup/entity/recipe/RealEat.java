package alpha.chupchup.entity.recipe;

import alpha.chupchup.dto.meal.RealEatEditRequestDto;
import alpha.chupchup.entity.user.User;
import alpha.chupchup.entity.enums.MealType;
import alpha.chupchup.entity.enums.Preference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "real_eat")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class RealEat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "real_eat_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meal_id")
    private WeeklyMeal weeklyMeal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    private String mealPhoto;

    private Float customFoodCalories;

    private String customFoodName;

    private LocalDate mealDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MealType mealType;

    @Column(length = 50)
    private Preference preference;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public void setPreference(Preference preference) {
        this.preference = preference;
    }

    public void editRealEat(RealEatEditRequestDto dto) {
        this.mealPhoto = dto.getMealPhoto();
        this.customFoodName = dto.getCustomFoodName();
        this.customFoodCalories = dto.getCustomFoodCalories();
    }
}
