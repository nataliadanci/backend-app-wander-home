package demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name="favorites")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Favorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "favorite_id")
    private int favoriteId;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    //TODO: FOREIGN KEY client_id & property_id
}
