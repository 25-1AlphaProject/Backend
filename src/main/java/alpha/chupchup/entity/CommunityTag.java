package alpha.chupchup.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "community_tags")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class CommunityTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 100)
    private String name;
}
