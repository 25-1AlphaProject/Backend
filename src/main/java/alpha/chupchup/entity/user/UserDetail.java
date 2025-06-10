package alpha.chupchup.entity.user;

import alpha.chupchup.entity.enums.Gender;
import alpha.chupchup.entity.enums.HealthGoal;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_details")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class UserDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    @Column(nullable = false)
    private int age;

    @Column(nullable = false)
    private double height;

    @Column(nullable = false)
    private double weight;

    @Column(columnDefinition = "JSON", nullable = false)
    private String mealCount;

    @Column(nullable = false)
    private int targetCalories;

    @Column(columnDefinition = "JSON", nullable = false)
    private String userDietInfo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private HealthGoal healthGoal;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
