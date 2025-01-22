package demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name="photos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "photo_id")
    private int photoId;

    @Column(name = "url", nullable = false)
    private String url;

    @Column(name = "uploaded_at", nullable = false, updatable = false)
    private LocalDateTime uploadedAt;

    //TODO: FOREIGN KEY property_id + deleted_at??
}
