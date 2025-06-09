package demo.transformers;

import demo.dto.CreateClientDTO;
import demo.entity.Client;
import org.springframework.stereotype.Component;

@Component
public class CreateClientTransformer implements Transformer<Client, CreateClientDTO>{
    @Override
    public CreateClientDTO fromEntity(Client client) {
        CreateClientDTO clientDTO = new CreateClientDTO();

        clientDTO.setId(client.getId());
        clientDTO.setUsername(client.getUsername());
        clientDTO.setEmail(client.getEmail());
        clientDTO.setFirstName(client.getFirstName());
        clientDTO.setLastName(client.getLastName());
        clientDTO.setPhone(client.getPhone());
        clientDTO.setDescription(client.getDescription());
        clientDTO.setImage(client.getImage());
        clientDTO.setProfileStatus(client.getProfileStatus());
        clientDTO.setRole(client.getRole());
        return clientDTO;
    }

    @Override
    public Client fromDTO(CreateClientDTO createClientDTO) {
        Client clientEntity = new Client();

        clientEntity.setId(createClientDTO.getId());
        clientEntity.setUsername(createClientDTO.getUsername());
        clientEntity.setPassword(createClientDTO.getPassword());
        clientEntity.setEmail(createClientDTO.getEmail());
        clientEntity.setFirstName(createClientDTO.getFirstName());
        clientEntity.setLastName(createClientDTO.getLastName());
        clientEntity.setPhone(createClientDTO.getPhone());
        clientEntity.setDescription(createClientDTO.getDescription());
        clientEntity.setImage(createClientDTO.getImage());
        clientEntity.setProfileStatus(createClientDTO.getProfileStatus());
        clientEntity.setRole(createClientDTO.getRole());
        return clientEntity;
    }
}
