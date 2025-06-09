package demo.transformers;

import demo.dto.CreateClientDTO;
import demo.dto.DisplayClientDTO;
import org.springframework.stereotype.Component;

@Component
public class CreateToDisplayClientTransformer implements DTOTransformer<CreateClientDTO, DisplayClientDTO>{


    @Override
    public DisplayClientDTO transform(CreateClientDTO createClientDTO) {
        DisplayClientDTO displayClientDTO = new DisplayClientDTO();

        displayClientDTO.setId(createClientDTO.getId());
        displayClientDTO.setUsername(createClientDTO.getUsername());
        displayClientDTO.setEmail(createClientDTO.getEmail());
        displayClientDTO.setFirstName(createClientDTO.getFirstName());
        displayClientDTO.setLastName(createClientDTO.getLastName());
        displayClientDTO.setPhone(createClientDTO.getPhone());
        displayClientDTO.setDescription(createClientDTO.getDescription());
        displayClientDTO.setImage(createClientDTO.getImage());
        displayClientDTO.setProfileStatus(createClientDTO.getProfileStatus());
        displayClientDTO.setRole(createClientDTO.getRole());

        return displayClientDTO;
    }
}
