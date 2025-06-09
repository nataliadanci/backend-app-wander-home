package demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import demo.entity.Client;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
public class ReviewDTO implements Serializable {

    @JsonProperty("id")
    private int id;

    @JsonProperty("rating")
    private int rating;

    @JsonProperty("comment")
    private String comment;

    @JsonProperty("client_name")
    private String clientName;

    @JsonProperty("date_added")
    private LocalDate dateAdded;

    @JsonProperty("client_id")
    private int clientId;

    @JsonProperty("real_estate_id")
    private int realEstateId;

}
