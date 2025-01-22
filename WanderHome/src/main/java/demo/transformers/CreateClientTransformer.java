package demo.transformers;

import demo.dto.CreateClientDTO;
import demo.dto.DisplayClientDTO;
import demo.entity.Client;
import org.springframework.stereotype.Component;

@Component
public class CreateClientTransformer implements Transformer<Client, CreateClientDTO>{
    @Override
    public CreateClientDTO fromEntity(Client client) {
        CreateClientDTO clientDTO = new CreateClientDTO();

        clientDTO.setClientId(client.getClientId());
        clientDTO.setUsername(client.getUsername());
        clientDTO.setEmail(client.getEmail());
        clientDTO.setFirstName(client.getFirstName());
        clientDTO.setLastName(client.getLastName());
        clientDTO.setPhone(client.getPhone());
        return clientDTO;
    }

    @Override
    public Client fromDTO(CreateClientDTO createClientDTO) {
        Client clientEntity = new Client();

        clientEntity.setClientId(createClientDTO.getClientId());
        clientEntity.setUsername(createClientDTO.getUsername());
        clientEntity.setPassword(createClientDTO.getPassword());
        clientEntity.setEmail(createClientDTO.getEmail());
        clientEntity.setFirstName(createClientDTO.getFirstName());
        clientEntity.setLastName(createClientDTO.getLastName());
        clientEntity.setPhone(createClientDTO.getPhone());
        return clientEntity;
    }
}
