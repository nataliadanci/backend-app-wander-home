package demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name="messages")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private int messageId;

    @Column(name = "content", nullable = false, length = 1000)
    private String content;

    @Column(name = "sent_at", nullable = false, updatable = false)
    private LocalDateTime sentAt;

    //TODO: sender_id, receiver_id + is it necessary to also have received_at / created_at / edit maybe?
}
