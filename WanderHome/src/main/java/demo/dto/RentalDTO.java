package demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
public class RentalDTO implements Serializable {

    @JsonProperty("id")
    private int id;

    @JsonProperty("start_date")
    private LocalDate startDate;

    @JsonProperty("end_date")
    private LocalDate endDate;

    @JsonProperty("status")
    private String status;

    @JsonProperty("guest_id")
    private int guestId;

    @JsonProperty("real_estate_id")
    private int realEstateId;

}
