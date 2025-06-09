package demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
public class RealEstateDTO implements Serializable {

    @JsonProperty("id")
    private int id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("description")
    private String description;

    @JsonProperty("location")
    private String location;

    @JsonProperty("real_estate_type")
    private String realEstateType;

    @JsonProperty("status")
    private String status;

    @JsonProperty("image_path")
    private String image;

    @JsonProperty("owner_id")
    private int owner_id;
}
