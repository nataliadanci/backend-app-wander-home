package demo.service;

import demo.dto.*;
import demo.errorhandling.*;

import java.util.Set;

public interface ClientService {
    CreateClientDTO saveClient(CreateClientDTO createClientDTO);
    Iterable<DisplayClientDTO> findAllClients();
    DisplayClientDTO findClientByID(Integer id) throws ClientNotFoundException;
    DisplayClientDTO findClientByEmail(String email) throws ClientNotFoundException;
    DisplayClientDTO login(String email, String password) throws ClientAccountNotFoundException, InvalidLoginPasswordException, InactiveClientException;
    ReviewDTO addReview(ReviewDTO reviewDTO) throws ClientNotFoundException, RealEstateNotFoundException;
    Set<ReviewDTO> getReviewsByClient(Integer clientId) throws ClientNotFoundException;
    DisplayClientDTO addRealEstateToFavorites(Integer clientId, Integer realEstateId) throws ClientNotFoundException, RealEstateNotFoundException;
    DisplayClientDTO removeRealEstateFromFavorites(Integer clientId, Integer realEstateId) throws ClientNotFoundException, RealEstateNotFoundException;
    Set<RentalDTO> getRentals(Integer clientId) throws ClientNotFoundException;
    Set<RentalDTO> getPastRentals(Integer clientId) throws ClientNotFoundException;
    Set<RentalDTO> getUpcomingRentals(Integer clientId) throws ClientNotFoundException;
    Set<RentalDTO> getOngoingRentals(Integer clientId) throws ClientNotFoundException;
    boolean hasClientReviewedRealEstate(Integer clientId, Integer realEstateId) throws ClientNotFoundException, RealEstateNotFoundException;
    Set<RealEstateDTO> getOwnedRealEstates(Integer clientId) throws ClientNotFoundException;
    Set<RealEstateDTO> getRealEstatesForClient(Integer clientId) throws ClientNotFoundException;

    CreateClientDTO updateClient (Integer id, CreateClientDTO clientDTO) throws ClientNotFoundException;

}
