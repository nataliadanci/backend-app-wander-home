package demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
//this is  dt transfer object
public class CreateClientDTO implements Serializable {

    @JsonProperty("client_id")
    private int clientId;

    @JsonProperty("username")
    private String username;

    //we keep password in this DTO because we use it to create a client and at creation we need to set a password
    @JsonProperty("password")
    private String password;

    @JsonProperty("email")
    private String email;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("phone")
    private String phone;

    //trebuie si timestamp urile de createdAt si updatedAt?

}
