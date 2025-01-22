package demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
public class ReviewDTO implements Serializable {

    @JsonProperty("review_id")
    private int reviewId;

    @JsonProperty("rating")
    private int rating;

    @JsonProperty("comment")
    private String comment;

    //TODO: client_id ; property_id ; created_at ??
}
