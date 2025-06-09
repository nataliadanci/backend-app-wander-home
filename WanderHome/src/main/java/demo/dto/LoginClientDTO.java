package demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class LoginClientDTO implements Serializable {

    @JsonProperty("email")
    private String email;

    @JsonProperty("password")
    private String password;
}
