package demo.transformers;

import demo.dto.RealEstateDTO;
import demo.entity.RealEstate;
import org.springframework.stereotype.Component;

@Component
public class RealEstateTransformer implements Transformer<RealEstate, RealEstateDTO> {

    @Override
    public RealEstateDTO fromEntity(RealEstate realEstate){
        //trebuie if(property==null) ??

        RealEstateDTO realEstateDTO = new RealEstateDTO();

        realEstateDTO.setId(realEstate.getId());
        realEstateDTO.setTitle(realEstate.getTitle());
        realEstateDTO.setDescription(realEstate.getDescription());
        realEstateDTO.setLocation(realEstate.getLocation());
        realEstateDTO.setRealEstateType(realEstate.getRealEstateType());
        realEstateDTO.setStatus(realEstate.getStatus());
        realEstateDTO.setImage(realEstate.getImage());
        realEstateDTO.setOwner_id(realEstate.getOwner().getId());

        return realEstateDTO;
    }

    @Override
    public RealEstate fromDTO(RealEstateDTO realEstateDTO){
        //trebuie if(property==null) ??

        RealEstate realEstateEntity = new RealEstate();

        realEstateEntity.setId(realEstateDTO.getId());
        realEstateEntity.setTitle(realEstateDTO.getTitle());
        realEstateEntity.setDescription(realEstateDTO.getDescription());
        realEstateEntity.setLocation(realEstateDTO.getLocation());
        realEstateEntity.setRealEstateType(realEstateDTO.getRealEstateType());
        realEstateEntity.setStatus(realEstateDTO.getStatus());
        realEstateEntity.setImage(realEstateDTO.getImage());
        //after fromDTO() is used we should also update the owner based on the owner_id

        return realEstateEntity;
    }
}
