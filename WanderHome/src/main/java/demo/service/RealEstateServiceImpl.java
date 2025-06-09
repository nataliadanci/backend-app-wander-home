package demo.service;

import demo.dto.*;
import demo.entity.Client;
import demo.entity.RealEstate;
import demo.entity.Rental;
import demo.entity.Review;
import demo.errorhandling.ClientNotFoundException;
import demo.errorhandling.RealEstateNotFoundException;
import demo.errorhandling.RealEstateOwnerNotFoundException;
import demo.repository.ClientRepository;
import demo.repository.RealEstateRepository;
import demo.transformers.DisplayClientTransformer;
import demo.transformers.RealEstateTransformer;
import demo.transformers.RentalTransformer;
import demo.transformers.ReviewTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class RealEstateServiceImpl implements RealEstateService{

    @Autowired
    private  RealEstateTransformer realEstateTransformer;
    @Autowired
    private RealEstateRepository realEstateRepository;
    @Autowired
    private ReviewTransformer reviewTransformer;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private DisplayClientTransformer displayClientTransformer;
    @Autowired
    private RentalTransformer rentalTransformer;
    @Override
    public RealEstateDTO saveRealEstate(RealEstateDTO realEstateDTO) throws ClientNotFoundException{
        RealEstate realEstateToBeSaved = realEstateTransformer.fromDTO(realEstateDTO);
        Optional<Client> optionalClient = clientRepository.findById(realEstateDTO.getOwner_id());
        Client foundClient = optionalClient.orElseThrow(()->new ClientNotFoundException("No client found with this id."));
        realEstateToBeSaved.setOwner(foundClient);
        if(realEstateToBeSaved.getStatus() == null || realEstateToBeSaved.getStatus().isEmpty()){
            realEstateToBeSaved.setStatus("active");
        }
        RealEstate savedRealEstate = realEstateRepository.save(realEstateToBeSaved);
        return realEstateTransformer.fromEntity(savedRealEstate);
    }

    @Override
    public Iterable<RealEstateDTO> findAllRealEstates(){
        Iterable<RealEstate> realEstates = realEstateRepository.findAll();
        return StreamSupport.stream(realEstates.spliterator(), false)
                .map(realEstateTransformer::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public RealEstateDTO findRealEstateById(Integer realEstateId) throws RealEstateNotFoundException {
        Optional<RealEstate> optionalRealEstate = realEstateRepository.findById(realEstateId);
        RealEstate foundRealEstate = optionalRealEstate.orElseThrow(()->new RealEstateNotFoundException("No real estate found with this ID."));
        return realEstateTransformer.fromEntity(foundRealEstate);
    }

    @Override
    public List<ReviewDTO> getReviewsForRealEstate(Integer realEstateId) throws RealEstateNotFoundException{
        Optional<RealEstate> optionalRealEstate = realEstateRepository.findById(realEstateId);
        RealEstate foundRealEstate= optionalRealEstate.orElseThrow(()->new RealEstateNotFoundException("No real estate found with this id."));

        List<Review> reviewsForRealEstate = foundRealEstate.getReviewsReceived();
        return reviewsForRealEstate.stream()
                .map(reviewEntity -> reviewTransformer.fromEntity(reviewEntity))
                .collect(Collectors.toList());

    }

    @Override
    public Set<RealEstateDTO> getClientsFavoriteRealEstates(Integer clientId) throws ClientNotFoundException{
        Optional<Client> optionalClient = clientRepository.findById(clientId);
        Client foundClient = optionalClient.orElseThrow(()->new ClientNotFoundException("No client found with this id."));

        Set<RealEstate> clientFavorites = foundClient.getFavoriteRealEstates();
        return clientFavorites.stream()
                .map(realEstateEntity -> realEstateTransformer.fromEntity(realEstateEntity))
                .collect(Collectors.toSet());
    }

    @Override
    public int getNumberOfInterestedClients(Integer realEstateId) throws RealEstateNotFoundException{
        Optional<RealEstate> optionalRealEstate = realEstateRepository.findById(realEstateId);
        RealEstate foundRealEstate= optionalRealEstate.orElseThrow(()->new RealEstateNotFoundException("No real estate found with this id."));

        return foundRealEstate.getInterestedClients().size();
    }

    @Override
    public DisplayClientDTO getRealEstateOwner(Integer realEstateId) throws RealEstateNotFoundException, RealEstateOwnerNotFoundException{
        Optional<RealEstate> optionalRealEstate = realEstateRepository.findById(realEstateId);
        RealEstate foundRealEstate= optionalRealEstate.orElseThrow(()->new RealEstateNotFoundException("No real estate found with this id."));

        Client owner = foundRealEstate.getOwner();
        if(owner==null){
            throw new RealEstateOwnerNotFoundException("This owner does not have a real estate listed.");
        }
        return displayClientTransformer.fromEntity(owner);
    }

    @Override
    public Set<RentalDTO> getRentals(Integer realEstateId) throws RealEstateNotFoundException{
        Optional<RealEstate> optionalRealEstate = realEstateRepository.findById(realEstateId);
        RealEstate foundRealEstate= optionalRealEstate.orElseThrow(()->new RealEstateNotFoundException("No real estate found with this id."));

        Set<Rental> foundRentals = foundRealEstate.getRentals();
        return foundRentals.stream()
                .map(rentalEntity -> rentalTransformer.fromEntity(rentalEntity))
                .collect(Collectors.toSet());
    }

    @Override
    public RealEstateDTO updateRealEstate(Integer id, RealEstateDTO updatedRealEstateDTO) throws RealEstateNotFoundException{
        Optional<RealEstate> realEstateToUpdate = realEstateRepository.findById(id);
        RealEstate foundRealEstateToUpdate = realEstateToUpdate.orElseThrow(()-> new RealEstateNotFoundException("No real estate found with this ID."));

        foundRealEstateToUpdate.setTitle(updatedRealEstateDTO.getTitle());
        foundRealEstateToUpdate.setDescription(updatedRealEstateDTO.getDescription());
        foundRealEstateToUpdate.setLocation(updatedRealEstateDTO.getLocation());
        foundRealEstateToUpdate.setRealEstateType(updatedRealEstateDTO.getRealEstateType());


        if(updatedRealEstateDTO.getImage() != null && !updatedRealEstateDTO.getImage().isEmpty()){
            foundRealEstateToUpdate.setImage(updatedRealEstateDTO.getImage());
        }
        if(updatedRealEstateDTO.getStatus() != null && !updatedRealEstateDTO.getStatus().isEmpty()){
            foundRealEstateToUpdate.setStatus(updatedRealEstateDTO.getStatus());
        }

        RealEstate updatedRealEstate = realEstateRepository.save(foundRealEstateToUpdate);

        return realEstateTransformer.fromEntity(updatedRealEstate);
    }
}
