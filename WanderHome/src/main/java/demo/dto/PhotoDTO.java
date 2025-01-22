package demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
public class PhotoDTO implements Serializable {

    @JsonProperty("photo_id")
    private int photoId;

    @JsonProperty("url")
    private String url;

    //TODO: PROPERTY_ID ; UPLOADED_AT ??
}
