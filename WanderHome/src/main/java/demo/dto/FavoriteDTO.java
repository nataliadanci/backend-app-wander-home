package demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
public class FavoriteDTO implements Serializable {

    @JsonProperty("favorite_id")
    private int favoriteId;

    //TODO:CLIENT_ID & PROPERTY_ID -- CREATED_AT ?
}
