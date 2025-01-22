package demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
public class PropertyDTO implements Serializable {

    @JsonProperty("property_id")
    private int propertyId;

    @JsonProperty("title")
    private String title;

    @JsonProperty("description")
    private String description;

    @JsonProperty("location")
    private String location;

    @JsonProperty("property_type")
    private String propertyType;

    //TODO: client_id ? created_at ? uploaded_at?
}
