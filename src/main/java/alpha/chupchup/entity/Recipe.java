package alpha.chupchup.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "recipe")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 255)
    private String recipeImage;

    @Column(columnDefinition = "TEXT")
    private String recipeText;

    private Float calories;
    private Float carbohydrates;
    private Float protein;
    private Float fat;
    private Float sodium;

    @Column(length = 255)
    private String foodImage;

    @Column(length = 255)
    private String ingredient;

    @Column(length = 50)
    private String foodType;
}
