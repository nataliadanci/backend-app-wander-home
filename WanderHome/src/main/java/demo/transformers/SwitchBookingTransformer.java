package demo.transformers;

import demo.dto.ReviewDTO;
import demo.dto.SwitchBookingDTO;
import demo.entity.RealEstate;
import demo.entity.Review;
import demo.entity.SwitchBooking;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class SwitchBookingTransformer implements Transformer<SwitchBooking, SwitchBookingDTO>{

    @Override
    public SwitchBookingDTO fromEntity(SwitchBooking switchBooking) {
        SwitchBookingDTO switchBookingDTO = new SwitchBookingDTO();

        switchBookingDTO.setId(switchBooking.getId());
        switchBookingDTO.setStartDate(switchBooking.getStartDate());
        switchBookingDTO.setEndDate(switchBooking.getEndDate());
        switchBookingDTO.setStatus(switchBooking.getStatus());

        Set<RealEstate> realEstatesSwitchedSet = switchBooking.getRealEstatesSwitched();
        List<RealEstate> realEstateList = new ArrayList<>(realEstatesSwitchedSet);
        switchBookingDTO.setPrimaryRealEstateId(realEstateList.get(0).getId());
        switchBookingDTO.setSecondaryRealEstateId(realEstateList.get(1).getId());

        return switchBookingDTO;
    }

    @Override
    public SwitchBooking fromDTO(SwitchBookingDTO switchBookingDTO) {
        SwitchBooking switchBooking = new SwitchBooking();

        switchBooking.setId(switchBookingDTO.getId());
        switchBooking.setStartDate(switchBookingDTO.getStartDate());
        switchBooking.setEndDate(switchBookingDTO.getEndDate());
        switchBooking.setStatus(switchBookingDTO.getStatus());
        //when we use this method we need to manually transform the realEstate ids to realEstate objects


        return switchBooking;
    }
}
