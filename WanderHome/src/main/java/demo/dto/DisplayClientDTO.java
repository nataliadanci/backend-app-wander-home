package demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
//this is a data transfer object
public class DisplayClientDTO implements Serializable {
    @JsonProperty("client_id")
    private int clientId;

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

    //we are not including password in this DTO because we will use this DTO to get and display a client
    // or a list of clients and the password is confidential
  /*@JsonProperty("password")
    private String password;*/
}
