package demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
public class BookingDTO implements Serializable {

    @JsonProperty("booking_id")
    private int bookingId;

    @JsonProperty("start_date")
    private LocalDateTime startDate;

    @JsonProperty("end_date")
    private LocalDateTime endDate;

    @JsonProperty("status")
    private String status;

    //TODO: FOREIGN KEYS CLIENT_ID & PROPERTY_ID
    //TODO: created_at ? updated_at ?
}
