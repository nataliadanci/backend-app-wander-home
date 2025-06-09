package demo.service;

import demo.dto.RealEstateDTO;
import demo.dto.RentalDTO;
import demo.entity.Client;
import demo.entity.RealEstate;
import demo.entity.Rental;
import demo.errorhandling.*;
import demo.repository.ClientRepository;
import demo.repository.RealEstateRepository;
import demo.repository.RentalRepository;
import demo.transformers.RentalTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

@Service
public class RentalServiceImpl implements RentalService {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private RealEstateRepository realEstateRepository;
    @Autowired
    private RentalRepository rentalRepository;
    @Autowired
    private RentalTransformer rentalTransformer;
    @Override
    public RentalDTO createRental(RentalDTO rentalDTO) throws ClientNotFoundException, RealEstateNotFoundException, RealEstateAlreadyRentedException, RentalDateException {
        Optional<Client> optionalClient = clientRepository.findById(rentalDTO.getGuestId());
        Client foundClient = optionalClient.orElseThrow(()->new ClientNotFoundException("No client found with this id."));

        Optional<RealEstate> optionalRealEstate = realEstateRepository.findById(rentalDTO.getRealEstateId());
        RealEstate foundRealEstate= optionalRealEstate.orElseThrow(()->new RealEstateNotFoundException("No real estate found with this id."));

        LocalDate localDateNow = LocalDate.now();
        if(rentalDTO.getStartDate().isBefore(localDateNow) || rentalDTO.getEndDate().isBefore(localDateNow)){
            throw new RentalDateException("Rental dates cannot be in the past.");
        }

        if(rentalDTO.getEndDate().isBefore(rentalDTO.getStartDate())){
            throw new RentalDateException("The end date cannot be before the start date.");
        }

        Set<Rental> existingRentals = rentalRepository.findByRentalRealEstateId(rentalDTO.getRealEstateId());
        for (Rental existingRental : existingRentals) {
            if (rentalDTO.getStartDate().isBefore(existingRental.getEndDate()) &&
                    rentalDTO.getEndDate().isAfter(existingRental.getStartDate())) {

                throw new RealEstateAlreadyRentedException("Real estate is not available for this period.");
            }
        }

        Rental rentalToBeSaved = rentalTransformer.fromDTO(rentalDTO);
        rentalToBeSaved.setRentalGuest(foundClient);
        rentalToBeSaved.setRentalRealEstate(foundRealEstate);

        if(rentalToBeSaved.getStatus() == null || rentalToBeSaved.getStatus().isEmpty()){
            rentalToBeSaved.setStatus("active");
        }

        Rental savedRental = rentalRepository.save(rentalToBeSaved);

        return rentalTransformer.fromEntity(savedRental);
    }

    @Override
    public RentalDTO updateRental(Integer id, RentalDTO updatedRentalDTO) throws RentalNotFoundException {
        Optional<Rental> rentalToUpdate = rentalRepository.findById(id);
        Rental foundRentalToUpdate = rentalToUpdate.orElseThrow(()-> new RentalNotFoundException("No rental found with this id."));

        foundRentalToUpdate.setStartDate(updatedRentalDTO.getStartDate());
        foundRentalToUpdate.setEndDate(updatedRentalDTO.getEndDate());

        if(updatedRentalDTO.getStatus() != null && !updatedRentalDTO.getStatus().isEmpty()){
            foundRentalToUpdate.setStatus(updatedRentalDTO.getStatus());
        }

        Rental updatedRental = rentalRepository.save(foundRentalToUpdate);

        return rentalTransformer.fromEntity(updatedRental);
    }
}
