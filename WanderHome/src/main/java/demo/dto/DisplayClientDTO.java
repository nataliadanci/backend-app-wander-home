package demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class DisplayClientDTO implements Serializable {
    @JsonProperty("id")
    private int id;

    @JsonProperty("username")
    private String username;

    @JsonProperty("email")
    private String email;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("description")
    private String description;

    @JsonProperty("image_path")
    private String image;

    @JsonProperty("profile_status")
    private String profileStatus;

    @JsonProperty("role")
    private String role;
}
