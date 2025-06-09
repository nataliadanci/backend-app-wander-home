package demo.service;

import demo.dto.RentalDTO;
import demo.dto.SwitchBookingDTO;
import demo.errorhandling.*;

import java.util.Set;

public interface SwitchBookingService {
    SwitchBookingDTO createSwitchBooking(SwitchBookingDTO switchBookingDTO) throws RealEstateNotFoundException, SwitchBookingDateException, RealEstateAlreadySwitchedException, SwitchBookingException;
    SwitchBookingDTO getSwitchBookingById(Integer id) throws SwitchBookingNotFoundException;
    Set<SwitchBookingDTO> getSwitchBookingsForRealEstate(Integer realEstateId) throws RealEstateNotFoundException;
    Set<SwitchBookingDTO> getSwitchBookingsForClient(Integer clientId) throws ClientNotFoundException;
    Set<SwitchBookingDTO> getPastSwitchBookingsForClient(Integer clientId) throws ClientNotFoundException;
    Set<SwitchBookingDTO> getUpcomingSwitchBookingsForClient(Integer clientId) throws ClientNotFoundException;
    Set<SwitchBookingDTO> getOngoingSwitchBookingsForClient(Integer clientId) throws ClientNotFoundException;
    SwitchBookingDTO updateSwitchBooking(Integer id, SwitchBookingDTO updatedSwitchBookingDTO) throws SwitchBookingNotFoundException;
}
