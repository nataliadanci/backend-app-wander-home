package demo.service;

import demo.dto.RealEstateDTO;
import demo.dto.RentalDTO;
import demo.entity.Rental;
import demo.errorhandling.*;

import java.util.Set;

public interface RentalService {
    RentalDTO createRental(RentalDTO rentalDTO) throws ClientNotFoundException, RealEstateNotFoundException, RealEstateAlreadyRentedException, RentalDateException;
    RentalDTO updateRental (Integer id, RentalDTO rentalDTO) throws RentalNotFoundException;
}
