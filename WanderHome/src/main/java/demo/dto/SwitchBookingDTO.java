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
public class SwitchBookingDTO implements Serializable {
    @JsonProperty("id")
    private int id;

    @JsonProperty("start_date")
    private LocalDate startDate;

    @JsonProperty("end_date")
    private LocalDate endDate;

    @JsonProperty("status")
    private String status;

    @JsonProperty("primary_real_estate_id")
    private int primaryRealEstateId;

    @JsonProperty("secondary_real_estate_id")
    private int secondaryRealEstateId;

}
