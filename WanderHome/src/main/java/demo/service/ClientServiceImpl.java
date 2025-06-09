package demo.service;

import demo.dto.*;
import demo.entity.Client;
import demo.entity.RealEstate;
import demo.entity.Rental;
import demo.entity.Review;
import demo.errorhandling.*;
import demo.repository.ClientRepository;
import demo.repository.RealEstateRepository;
import demo.repository.ReviewRepository;
import demo.transformers.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ClientServiceImpl implements ClientService {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private CreateClientTransformer createClientTransformer;

    @Autowired
    private RealEstateRepository realEstateRepository;
    @Autowired
    private RealEstateTransformer realEstateTransformer;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired DisplayClientTransformer displayClientTransformer;
    @Autowired
    private ReviewTransformer reviewTransformer;
    @Autowired
    private RentalTransformer rentalTransformer;

    @Override
    public CreateClientDTO saveClient(CreateClientDTO createClientDTO) {
        Client clientToBeSaved = createClientTransformer.fromDTO(createClientDTO);
        if(clientToBeSaved.getRole() == null || clientToBeSaved.getRole().isEmpty()){
            clientToBeSaved.setRole("user");
        }
        if(clientToBeSaved.getProfileStatus() == null || clientToBeSaved.getProfileStatus().isEmpty()){
            clientToBeSaved.setProfileStatus("active");
        }
        Client clientSaved = clientRepository.save(clientToBeSaved);
        return createClientTransformer.fromEntity(clientSaved);
    }

    @Override
    public Iterable<DisplayClientDTO> findAllClients(){
        Iterable<Client> clients = clientRepository.findAll();
        return StreamSupport.stream(clients.spliterator(), false)
                .map(displayClientTransformer::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public DisplayClientDTO findClientByID(Integer id) throws ClientNotFoundException {
        Optional<Client> optionalClient = clientRepository.findById(id);
        Client foundClient = optionalClient.orElseThrow(()->new ClientNotFoundException("No client found with this ID."));
        return displayClientTransformer.fromEntity(foundClient);
    }
    @Override
    public DisplayClientDTO findClientByEmail(String email) throws ClientNotFoundException {
        Optional<Client> optionalClient = clientRepository.findByEmail(email);
        Client foundClient = optionalClient.orElseThrow(()->new ClientNotFoundException("No client found with this email."));
        return displayClientTransformer.fromEntity(foundClient);
    }

    @Override
    public DisplayClientDTO login(String email, String password) throws ClientAccountNotFoundException, InvalidLoginPasswordException, InactiveClientException {
        Optional<Client> optionalClient = clientRepository.findByEmail(email);

        if(optionalClient.isEmpty()){
            throw new ClientAccountNotFoundException("Client's account not found");
        }

        Client client = optionalClient.get();
        if(!password.equals(client.getPassword())){
            throw new InvalidLoginPasswordException("Invalid password for login");
        }
        if(client.getProfileStatus().equals("inactive")){
            throw new InactiveClientException("Account inactive");
        }
        return displayClientTransformer.fromEntity(client);
    }

    @Override
    public ReviewDTO addReview(ReviewDTO reviewDTO) throws ClientNotFoundException, RealEstateNotFoundException{
        Optional<Client> optionalClient = clientRepository.findById(reviewDTO.getClientId());
        Client foundClient = optionalClient.orElseThrow(()->new ClientNotFoundException("No client found with this id."));

        Optional<RealEstate> optionalRealEstate = realEstateRepository.findById(reviewDTO.getRealEstateId());
        RealEstate foundRealEstate= optionalRealEstate.orElseThrow(()->new RealEstateNotFoundException("No real estate found with this id."));

        Review reviewToBeSaved = new Review();
        reviewToBeSaved.setClient(foundClient);
        reviewToBeSaved.setRealEstate(foundRealEstate);
        reviewToBeSaved.setRating(reviewDTO.getRating());
        reviewToBeSaved.setComment(reviewDTO.getComment());
        reviewToBeSaved.setDateAdded(LocalDate.now());

        //get all reviews for real estate
        //search if there is already one for foundClient.getId()
        //if no, save the review, otherwise throw exception and catch it in controller

        Review savedReview = reviewRepository.save(reviewToBeSaved);
        ReviewDTO reviewToBeReturned = new ReviewDTO();

        reviewToBeReturned.setId(savedReview.getId());
        reviewToBeReturned.setClientId(savedReview.getClient().getId());
        reviewToBeReturned.setClientName(savedReview.getClient().getFirstName() + " " + savedReview.getClient().getLastName());
        reviewToBeReturned.setRealEstateId(savedReview.getRealEstate().getId());
        reviewToBeReturned.setRating(savedReview.getRating());
        reviewToBeReturned.setComment(savedReview.getComment());

        return reviewToBeReturned;
    }

    @Override
    public Set<ReviewDTO> getReviewsByClient(Integer clientId) throws ClientNotFoundException{
        Optional<Client> optionalClient = clientRepository.findById(clientId);
        Client foundClient = optionalClient.orElseThrow(()->new ClientNotFoundException("No client found with this id."));

        Set<Review> reviewsByClient = foundClient.getReviewsWritten();
        return reviewsByClient.stream()
                .map(reviewEntity -> reviewTransformer.fromEntity(reviewEntity))
                .collect(Collectors.toSet());
    }

    @Override
    public DisplayClientDTO addRealEstateToFavorites(Integer clientId, Integer realEstateId) throws ClientNotFoundException, RealEstateNotFoundException{
        Optional<Client> optionalClient = clientRepository.findById(clientId);
        Client foundClient = optionalClient.orElseThrow(()->new ClientNotFoundException("No client found with this id."));

        Optional<RealEstate> optionalRealEstate = realEstateRepository.findById(realEstateId);
        RealEstate foundRealEstate= optionalRealEstate.orElseThrow(()->new RealEstateNotFoundException("No real estate found with this id."));

        Set<RealEstate> realEstateFavorites = foundClient.getFavoriteRealEstates();
        realEstateFavorites.add(foundRealEstate);

        Client updatedClient = clientRepository.save(foundClient);

        return displayClientTransformer.fromEntity(updatedClient);
    }

    @Override
    public DisplayClientDTO removeRealEstateFromFavorites(Integer clientId, Integer realEstateId) throws ClientNotFoundException, RealEstateNotFoundException{
        Optional<Client> optionalClient = clientRepository.findById(clientId);
        Client foundClient = optionalClient.orElseThrow(()->new ClientNotFoundException("No client found with this id."));

        Optional<RealEstate> optionalRealEstate = realEstateRepository.findById(realEstateId);
        RealEstate foundRealEstate= optionalRealEstate.orElseThrow(()->new RealEstateNotFoundException("No real estate found with this id."));

        Set<RealEstate> realEstateFavorites = foundClient.getFavoriteRealEstates();
        realEstateFavorites.remove(foundRealEstate);

        Client updatedClient = clientRepository.save(foundClient);

        return displayClientTransformer.fromEntity(updatedClient);
    }

    @Override
    public Set<RentalDTO> getRentals(Integer clientId) throws ClientNotFoundException{
        Optional<Client> optionalClient = clientRepository.findById(clientId);
        Client foundClient = optionalClient.orElseThrow(()->new ClientNotFoundException("No client found with this id."));

        Set<Rental> foundRentals = foundClient.getRentals();
        return foundRentals.stream()
                .map(rentalEntity -> rentalTransformer.fromEntity(rentalEntity))
                .collect(Collectors.toSet());
    }

    @Override
    public Set<RentalDTO> getPastRentals(Integer clientId) throws ClientNotFoundException{
        Optional<Client> optionalClient = clientRepository.findById(clientId);
        Client foundClient = optionalClient.orElseThrow(()->new ClientNotFoundException("No client found with this id."));

        LocalDate currentDate = LocalDate.now();

        Set<Rental> foundRentals = foundClient.getRentals();
        return foundRentals.stream()
                .filter(rental -> rental.getEndDate()!=null && rental.getEndDate().isBefore(currentDate))
                .map(rentalEntity -> rentalTransformer.fromEntity(rentalEntity))
                .collect(Collectors.toSet());
    }

    @Override
    public Set<RentalDTO> getUpcomingRentals(Integer clientId) throws ClientNotFoundException{
        Optional<Client> optionalClient = clientRepository.findById(clientId);
        Client foundClient = optionalClient.orElseThrow(()->new ClientNotFoundException("No client found with this id."));

        LocalDate currentDate = LocalDate.now();

        Set<Rental> foundRentals = foundClient.getRentals();
        return foundRentals.stream()
                .filter(rental -> rental.getStartDate()!=null && rental.getStartDate().isAfter(currentDate))
                .map(rentalEntity -> rentalTransformer.fromEntity(rentalEntity))
                .collect(Collectors.toSet());
    }
    @Override
    public Set<RentalDTO> getOngoingRentals(Integer clientId) throws ClientNotFoundException{
        Optional<Client> optionalClient = clientRepository.findById(clientId);
        Client foundClient = optionalClient.orElseThrow(()->new ClientNotFoundException("No client found with this id."));

        LocalDate currentDate = LocalDate.now();

        Set<Rental> foundRentals = foundClient.getRentals();
        return foundRentals.stream()
                .filter(rental -> rental.getStartDate() != null &&
                        rental.getEndDate() != null &&
                        (rental.getStartDate().isEqual(currentDate) || rental.getStartDate().isBefore(currentDate)) &&
                                (rental.getEndDate().isEqual(currentDate) || rental.getEndDate().isAfter(currentDate))
                )
                .map(rentalEntity -> rentalTransformer.fromEntity(rentalEntity))
                .collect(Collectors.toSet());
    }

    @Override
    public boolean hasClientReviewedRealEstate(Integer clientId, Integer realEstateId) throws ClientNotFoundException, RealEstateNotFoundException {
        Optional<Client> optionalClient = clientRepository.findById(clientId);
        Client foundClient = optionalClient.orElseThrow(()->new ClientNotFoundException("No client found with this id."));

        Optional<RealEstate> optionalRealEstate = realEstateRepository.findById(realEstateId);
        RealEstate foundRealEstate= optionalRealEstate.orElseThrow(()->new RealEstateNotFoundException("No real estate found with this id."));

        return reviewRepository.existsByClientIdAndRealEstateId(clientId,realEstateId);
    }

    @Override
    public Set<RealEstateDTO> getOwnedRealEstates(Integer clientId) throws ClientNotFoundException {
        Optional<Client> optionalClient = clientRepository.findById(clientId);
        Client foundClient = optionalClient.orElseThrow(()->new ClientNotFoundException("No client found with this id."));

        Set<RealEstate> ownedRealEstates = foundClient.getRealEstatesOwned();
        return ownedRealEstates.stream()
                .map(realEstateEntity -> realEstateTransformer.fromEntity(realEstateEntity))
                .collect(Collectors.toSet());

    }

    @Override
    public Set<RealEstateDTO> getRealEstatesForClient(Integer clientId) throws ClientNotFoundException {
        Optional<Client> optionalClient = clientRepository.findById(clientId);
        Client foundClient = optionalClient.orElseThrow(()->new ClientNotFoundException("No client found with this id."));

        Set<RealEstate> ownedRealEstates = foundClient.getRealEstatesOwned();
        Iterable<RealEstate> allRealEstates = realEstateRepository.findAll();

        return StreamSupport.stream(allRealEstates.spliterator(), false)
                .filter(realEstate -> !ownedRealEstates.contains(realEstate))
                .map(realEstateEntity -> realEstateTransformer.fromEntity(realEstateEntity))
                .collect(Collectors.toSet());

    }

    @Override
    public CreateClientDTO updateClient(Integer id, CreateClientDTO updatedClientDTO) throws ClientNotFoundException{
        Optional<Client> clientToUpdate = clientRepository.findById(id);
        Client foundClientToUpdate = clientToUpdate.orElseThrow(()-> new ClientNotFoundException("No client found with this ID."));

        foundClientToUpdate.setFirstName(updatedClientDTO.getFirstName());
        foundClientToUpdate.setLastName(updatedClientDTO.getLastName());
        foundClientToUpdate.setEmail(updatedClientDTO.getEmail());
        foundClientToUpdate.setPhone(updatedClientDTO.getPhone());
        foundClientToUpdate.setUsername(updatedClientDTO.getUsername());
        foundClientToUpdate.setDescription(updatedClientDTO.getDescription());
        foundClientToUpdate.setRole(updatedClientDTO.getRole());

        if(updatedClientDTO.getPassword() != null && !updatedClientDTO.getPassword().isEmpty()){
            foundClientToUpdate.setPassword(updatedClientDTO.getPassword());
        }

        if(updatedClientDTO.getProfileStatus() !=null && !updatedClientDTO.getProfileStatus().isEmpty()){
            foundClientToUpdate.setProfileStatus(updatedClientDTO.getProfileStatus());
        }


        Client dbUpdatedClient = clientRepository.save(foundClientToUpdate);

        return createClientTransformer.fromEntity(dbUpdatedClient);
    }


}


