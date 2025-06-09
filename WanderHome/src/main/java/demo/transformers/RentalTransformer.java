package demo.transformers;

import demo.dto.RentalDTO;
import demo.entity.Rental;
import org.springframework.stereotype.Component;

@Component
public class RentalTransformer implements Transformer<Rental, RentalDTO>{

    @Override
    public RentalDTO fromEntity(Rental rental){

        RentalDTO rentalDTO = new RentalDTO();

        rentalDTO.setId(rental.getId());
        rentalDTO.setStartDate(rental.getStartDate());
        rentalDTO.setEndDate(rental.getEndDate());
        rentalDTO.setStatus(rental.getStatus());
        rentalDTO.setGuestId(rental.getRentalGuest().getId());
        rentalDTO.setRealEstateId(rental.getRentalRealEstate().getId());

        return rentalDTO;
    }

    @Override
    public Rental fromDTO(RentalDTO rentalDTO){

        Rental rentalEntity = new Rental();

        rentalEntity.setId(rentalDTO.getId());
        rentalEntity.setStartDate(rentalDTO.getStartDate());
        rentalEntity.setEndDate(rentalDTO.getEndDate());
        rentalEntity.setStatus(rentalDTO.getStatus());
        //when we use this method we need to also set the guest and real estate objects based on ids

        return rentalEntity;
    }


}
