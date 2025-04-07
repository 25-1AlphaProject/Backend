package alpha.chupchup.entity;

import alpha.chupchup.entity.enums.MealType;
import alpha.chupchup.entity.enums.Preference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

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

    @Column(length = 255)
    private String mealPhoto;

    private Float customFoodCalories;

    @Column(length = 255)
    private String customFoodName;

    private LocalDateTime mealDate;

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

//    public void setWeeklyMeal(WeeklyMeal weeklyMeal) {
//        this.weeklyMeal = weeklyMeal;
//        if (weeklyMeal != null && !weeklyMeal.getRealEats().contains(this)) {
//            weeklyMeal.getRealEats().add(this);
//        }
//    }
}
