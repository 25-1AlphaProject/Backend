package alpha.chupchup.entity;

import alpha.chupchup.entity.enums.Gender;
import alpha.chupchup.entity.enums.MealCount;
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

    @Column(nullable = false, precision = 5, scale = 2)
    private double height;

    @Column(nullable = false, precision = 5, scale = 2)
    private double weight;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MealCount mealCount;

    @Column(nullable = false)
    private int targetCalories;

    @Column(columnDefinition = "JSON", nullable = false)
    private String userDietInfo; // JSON 문자열 저장

    @Column(nullable = false, updatable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt = LocalDateTime.now();
}
