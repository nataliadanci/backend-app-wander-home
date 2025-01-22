package demo.transformers;

import demo.dto.PropertyDTO;
import demo.entity.Property;
import org.springframework.stereotype.Component;

@Component
public class PropertyTransformer implements Transformer<Property, PropertyDTO> {

    @Override
    public PropertyDTO fromEntity(Property property){
        //trebuie if(property==null) ??

        PropertyDTO propertyDTO = new PropertyDTO();

        propertyDTO.setPropertyId(property.getPropertyId());
        propertyDTO.setTitle(property.getTitle());
        propertyDTO.setDescription(property.getDescription());
        propertyDTO.setLocation(property.getLocation());
        propertyDTO.setPropertyType(property.getPropertyType());

        return propertyDTO;
    }

    @Override
    public Property fromDTO(PropertyDTO propertyDTO){
        //trebuie if(property==null) ??

        Property propertyEntity = new Property();

        propertyEntity.setPropertyId(propertyDTO.getPropertyId());
        propertyEntity.setTitle(propertyDTO.getTitle());
        propertyEntity.setDescription(propertyDTO.getDescription());
        propertyEntity.setLocation(propertyDTO.getLocation());
        propertyEntity.setPropertyType(propertyDTO.getPropertyType());

        //created_at ; uploaded_at?

        return propertyEntity;
    }
}
