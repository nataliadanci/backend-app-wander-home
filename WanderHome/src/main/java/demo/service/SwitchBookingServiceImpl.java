package demo.service;

import demo.dto.SwitchBookingDTO;
import demo.entity.Client;
import demo.entity.RealEstate;
import demo.entity.SwitchBooking;
import demo.errorhandling.*;
import demo.repository.ClientRepository;
import demo.repository.RealEstateRepository;
import demo.repository.SwitchBookingRepository;
import demo.transformers.SwitchBookingTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class SwitchBookingServiceImpl implements SwitchBookingService{

    @Autowired
    private RealEstateRepository realEstateRepository;
    @Autowired
    private SwitchBookingRepository switchBookingRepository;
    @Autowired
    private SwitchBookingTransformer switchBookingTransformer;
    @Autowired
    private ClientRepository clientRepository;
    @Override
    public SwitchBookingDTO createSwitchBooking(SwitchBookingDTO switchBookingDTO) throws RealEstateNotFoundException, SwitchBookingDateException, RealEstateAlreadySwitchedException, SwitchBookingException {
        Optional<RealEstate> optionalPrimaryRealEstate = realEstateRepository.findById(switchBookingDTO.getPrimaryRealEstateId());
        RealEstate foundPrimaryRealEstate= optionalPrimaryRealEstate.orElseThrow(()->new RealEstateNotFoundException("No real estate found with this id."));

        Optional<RealEstate> optionalSecondaryRealEstate = realEstateRepository.findById(switchBookingDTO.getSecondaryRealEstateId());
        RealEstate foundSecondaryRealEstate= optionalSecondaryRealEstate.orElseThrow(()->new RealEstateNotFoundException("No real estate found with this id."));

        if(foundPrimaryRealEstate.getOwner().getId() == foundSecondaryRealEstate.getOwner().getId()){
            throw new SwitchBookingException("Both real estates have the same owner, the switch cannot be made.");
        }

        LocalDate localDateNow = LocalDate.now();
        if(switchBookingDTO.getStartDate().isBefore(localDateNow) || switchBookingDTO.getEndDate().isBefore(localDateNow)){
            throw new SwitchBookingDateException("Dates cannot be in the past.");
        }

        if(switchBookingDTO.getEndDate().isBefore(switchBookingDTO.getStartDate())){
            throw new SwitchBookingDateException("The end date cannot be before the start date.");
        }

        Set<SwitchBookingDTO> existingSwitchBookingsPrimary = getSwitchBookingsForRealEstate(foundPrimaryRealEstate.getId());
        for (SwitchBookingDTO existingSwitchBooking : existingSwitchBookingsPrimary) {
            if (switchBookingDTO.getStartDate().isBefore(existingSwitchBooking.getEndDate()) &&
                    switchBookingDTO.getEndDate().isAfter(existingSwitchBooking.getStartDate())) {
                throw new RealEstateAlreadySwitchedException("Primary real estate is not available for this period.");
            }
        }

        Set<SwitchBookingDTO> existingSwitchBookingsSecondary = getSwitchBookingsForRealEstate(foundSecondaryRealEstate.getId());
        for (SwitchBookingDTO existingSwitchBooking : existingSwitchBookingsSecondary) {
            if (switchBookingDTO.getStartDate().isBefore(existingSwitchBooking.getEndDate()) &&
                    switchBookingDTO.getEndDate().isAfter(existingSwitchBooking.getStartDate())) {
                throw new RealEstateAlreadySwitchedException("Secondary real estate is not available for this period.");
            }
        }

        SwitchBooking switchBookingEntity = switchBookingTransformer.fromDTO(switchBookingDTO);
        LinkedHashSet<RealEstate> twoSwitchedRealEstates = new LinkedHashSet<>();
        twoSwitchedRealEstates.add(foundPrimaryRealEstate);
        twoSwitchedRealEstates.add(foundSecondaryRealEstate);
        switchBookingEntity.setRealEstatesSwitched(twoSwitchedRealEstates);

        if(switchBookingEntity.getStatus() == null || switchBookingEntity.getStatus().isEmpty()){
            switchBookingEntity.setStatus("active");
        }

        SwitchBooking savedSwitchBooking = switchBookingRepository.save(switchBookingEntity);

        return switchBookingTransformer.fromEntity(savedSwitchBooking);
    }

    @Override
    public SwitchBookingDTO getSwitchBookingById(Integer id) throws SwitchBookingNotFoundException{
        Optional<SwitchBooking> optionalSwitchBooking = switchBookingRepository.findById(id);
        SwitchBooking foundSwitchBooking = optionalSwitchBooking.orElseThrow(()-> new SwitchBookingNotFoundException("No switch booking found with this id."));
        return switchBookingTransformer.fromEntity(foundSwitchBooking);
    }

    @Override
    public Set<SwitchBookingDTO> getSwitchBookingsForRealEstate(Integer realEstateId) throws RealEstateNotFoundException{
        Optional<RealEstate> optionalRealEstate = realEstateRepository.findById(realEstateId);
        RealEstate foundRealEstate= optionalRealEstate.orElseThrow(()->new RealEstateNotFoundException("No real estate found with this id."));

        Set<SwitchBooking> realEstateSwitchBookings = foundRealEstate.getSwitchBookings();
        return realEstateSwitchBookings.stream()
                .map(switchBookingEntity -> switchBookingTransformer.fromEntity(switchBookingEntity))
                .collect(Collectors.toSet());
    }

    @Override
    public Set<SwitchBookingDTO> getSwitchBookingsForClient(Integer clientId) throws ClientNotFoundException {
        Optional<Client> optionalClient = clientRepository.findById(clientId);
        Client foundClient = optionalClient.orElseThrow(()->new ClientNotFoundException("No client found with this id."));

        Set<RealEstate> ownedRealEstates = foundClient.getRealEstatesOwned();
        Iterable<SwitchBooking> allSwitchBookings = switchBookingRepository.findAll();

        return StreamSupport.stream(allSwitchBookings.spliterator(), false)
                .filter(switchBooking -> checkSwitchBookingContainsOwnedRealEstate(switchBooking,ownedRealEstates))
                .map(switchBookingTransformer::fromEntity)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<SwitchBookingDTO> getPastSwitchBookingsForClient(Integer clientId) throws ClientNotFoundException {
        Optional<Client> optionalClient = clientRepository.findById(clientId);
        Client foundClient = optionalClient.orElseThrow(()->new ClientNotFoundException("No client found with this id."));

        Set<RealEstate> ownedRealEstates = foundClient.getRealEstatesOwned();
        Iterable<SwitchBooking> allSwitchBookings = switchBookingRepository.findAll();

        LocalDate currentDate = LocalDate.now();

        return StreamSupport.stream(allSwitchBookings.spliterator(), false)
                .filter(switchBooking -> checkSwitchBookingContainsOwnedRealEstate(switchBooking,ownedRealEstates))
                .filter(switchBooking -> switchBooking.getEndDate().isBefore(currentDate))
                .map(switchBookingTransformer::fromEntity)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<SwitchBookingDTO> getUpcomingSwitchBookingsForClient(Integer clientId) throws ClientNotFoundException {
        Optional<Client> optionalClient = clientRepository.findById(clientId);
        Client foundClient = optionalClient.orElseThrow(()->new ClientNotFoundException("No client found with this id."));

        Set<RealEstate> ownedRealEstates = foundClient.getRealEstatesOwned();
        Iterable<SwitchBooking> allSwitchBookings = switchBookingRepository.findAll();

        LocalDate currentDate = LocalDate.now();

        return StreamSupport.stream(allSwitchBookings.spliterator(), false)
                .filter(switchBooking -> checkSwitchBookingContainsOwnedRealEstate(switchBooking,ownedRealEstates))
                .filter(switchBooking -> switchBooking.getStartDate().isAfter(currentDate))
                .map(switchBookingTransformer::fromEntity)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<SwitchBookingDTO> getOngoingSwitchBookingsForClient(Integer clientId) throws ClientNotFoundException {
        Optional<Client> optionalClient = clientRepository.findById(clientId);
        Client foundClient = optionalClient.orElseThrow(()->new ClientNotFoundException("No client found with this id."));

        Set<RealEstate> ownedRealEstates = foundClient.getRealEstatesOwned();
        Iterable<SwitchBooking> allSwitchBookings = switchBookingRepository.findAll();

        LocalDate currentDate = LocalDate.now();

        return StreamSupport.stream(allSwitchBookings.spliterator(), false)
                .filter(switchBooking -> checkSwitchBookingContainsOwnedRealEstate(switchBooking,ownedRealEstates))
                .filter(switchBooking ->
                        (switchBooking.getStartDate().isEqual(currentDate) || switchBooking.getStartDate().isBefore(currentDate)) &&
                                (switchBooking.getEndDate().isEqual(currentDate) || switchBooking.getEndDate().isAfter(currentDate))
                )
                .map(switchBookingTransformer::fromEntity)
                .collect(Collectors.toSet());
    }



    @Override
    public SwitchBookingDTO updateSwitchBooking(Integer id, SwitchBookingDTO updatedSwitchBookingDTO) throws SwitchBookingNotFoundException {
        Optional<SwitchBooking> switchBookingToUpdate = switchBookingRepository.findById(id);
        SwitchBooking foundSwitchBookingToUpdate = switchBookingToUpdate.orElseThrow(()-> new SwitchBookingNotFoundException("No switch booking found with this id."));

        foundSwitchBookingToUpdate.setStartDate(updatedSwitchBookingDTO.getStartDate());
        foundSwitchBookingToUpdate.setEndDate(updatedSwitchBookingDTO.getEndDate());

        if(updatedSwitchBookingDTO.getStatus() != null && !updatedSwitchBookingDTO.getStatus().isEmpty()){
            foundSwitchBookingToUpdate.setStatus(updatedSwitchBookingDTO.getStatus());
        }

        SwitchBooking updatedSwitchBooking = switchBookingRepository.save(foundSwitchBookingToUpdate);

        return switchBookingTransformer.fromEntity(updatedSwitchBooking);
    }

    private boolean checkSwitchBookingContainsOwnedRealEstate(SwitchBooking selectedSwitchBooking, Set<RealEstate> ownedRealEstates){

        Set<RealEstate> realEstatesSwitched = selectedSwitchBooking.getRealEstatesSwitched();
        List<RealEstate> realEstatesSwitchedList = new ArrayList<>(realEstatesSwitched);

        if(ownedRealEstates.contains(realEstatesSwitchedList.get(0)) || ownedRealEstates.contains(realEstatesSwitchedList.get(1))){
            return true;
        }

        return false;
    }
}
