package demo.transformers;

import demo.dto.DisplayClientDTO;
import demo.entity.Client;
import org.springframework.stereotype.Component;

@Component
public class DisplayClientTransformer implements Transformer<Client, DisplayClientDTO> {

    @Override
    public DisplayClientDTO fromEntity(Client client) {

        DisplayClientDTO clientDTO = new DisplayClientDTO();

        clientDTO.setClientId(client.getClientId());
        clientDTO.setUsername(client.getUsername());
        clientDTO.setEmail(client.getEmail());
        clientDTO.setFirstName(client.getFirstName());
        clientDTO.setLastName(client.getLastName());
        clientDTO.setPhone(client.getPhone());

        return clientDTO;
    }

    @Override
    public Client fromDTO(DisplayClientDTO displayClientDTO) {

        Client clientEntity = new Client();

        clientEntity.setClientId(displayClientDTO.getClientId());
        clientEntity.setUsername(displayClientDTO.getUsername());
        clientEntity.setEmail(displayClientDTO.getEmail());
        clientEntity.setFirstName(displayClientDTO.getFirstName());
        clientEntity.setLastName(displayClientDTO.getLastName());
        clientEntity.setPhone(displayClientDTO.getPhone());

        return clientEntity;
    }
}
