package demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name="properties")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "property_id")
    private int propertyId;

    @Column(name = "title", nullable = false, length = 50)
    private String title;

    @Column(name = "description", nullable = false, length = 300)
    private String description;

    @Column(name = "location", nullable = false, length = 300)
    private String location;

    @Column(name = "property_type", nullable = false, length = 50)
    private String propertyType;

    @Column(name = "status", nullable = false, length = 50) //active, inactive
    private String status;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    //TODO: FOREIGN KEY client_id & MAYBE CHANGE THE NAME? REAL ESTATES/ ESTATES?
    //TODO: ADD COLUMN "STATUS" IN THE DATABASE
}
