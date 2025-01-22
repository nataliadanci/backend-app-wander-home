package demo.transformers;

import demo.dto.CreateClientDTO;
import demo.dto.DisplayClientDTO;
import org.springframework.stereotype.Component;

@Component
public class CreateToDisplayClientTransformer implements DTOTransformer<CreateClientDTO, DisplayClientDTO>{


    @Override
    public DisplayClientDTO transform(CreateClientDTO createClientDTO) {
        DisplayClientDTO displayClientDTO = new DisplayClientDTO();

        displayClientDTO.setClientId(createClientDTO.getClientId());
        displayClientDTO.setUsername(createClientDTO.getUsername());
        displayClientDTO.setEmail(createClientDTO.getEmail());
        displayClientDTO.setFirstName(createClientDTO.getFirstName());
        displayClientDTO.setLastName(createClientDTO.getLastName());
        displayClientDTO.setPhone(createClientDTO.getPhone());

        return displayClientDTO;
    }
}
