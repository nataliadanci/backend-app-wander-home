package demo.transformers;

import demo.dto.DisplayClientDTO;
import demo.entity.Client;
import org.springframework.stereotype.Component;

@Component
public class DisplayClientTransformer implements Transformer<Client, DisplayClientDTO> {

    @Override
    public DisplayClientDTO fromEntity(Client client) {

        DisplayClientDTO clientDTO = new DisplayClientDTO();

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
    public Client fromDTO(DisplayClientDTO displayClientDTO) {

        Client clientEntity = new Client();

        clientEntity.setId(displayClientDTO.getId());
        clientEntity.setUsername(displayClientDTO.getUsername());
        clientEntity.setEmail(displayClientDTO.getEmail());
        clientEntity.setFirstName(displayClientDTO.getFirstName());
        clientEntity.setLastName(displayClientDTO.getLastName());
        clientEntity.setPhone(displayClientDTO.getPhone());
        clientEntity.setDescription(displayClientDTO.getDescription());
        clientEntity.setImage(displayClientDTO.getImage());
        clientEntity.setRole(displayClientDTO.getRole());

        return clientEntity;
    }
}
