package demo.controller;

import demo.dto.*;
import demo.errorhandling.*;
import demo.service.ClientService;
import demo.transformers.CreateToDisplayClientTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController //This means that this class is a controller (also a bean)
@RequestMapping(path = "/clients/")
@CrossOrigin(origins = "", allowedHeaders = "") //Allows any requests from browser
public class ClientController {
    @Autowired
    private ClientService clientService;

    @Autowired
    private CreateToDisplayClientTransformer createToDisplayClientTransformer;

    @PostMapping(path = "/add")
    public ResponseEntity<DisplayClientDTO> createClient(@RequestBody CreateClientDTO createClientDTO){
        try{
            CreateClientDTO savedClientDTO = clientService.saveClient(createClientDTO);
            DisplayClientDTO displayClientDTO = createToDisplayClientTransformer.transform(savedClientDTO);
            return ResponseEntity.created(new URI("/clients/" + savedClientDTO.getId())).body(displayClientDTO);
        } catch (URISyntaxException e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(path = "/all")
    public @ResponseBody Set<DisplayClientDTO> getAllClients() {
        Iterable<DisplayClientDTO> clients = clientService.findAllClients();
        return StreamSupport.stream(clients.spliterator(), false)
                .collect(Collectors.toSet());
    }

    @GetMapping(path = "/id/{client_id}")
    public @ResponseBody DisplayClientDTO getClientByID(@PathVariable("client_id") Integer clientID) {
        try {
            return clientService.findClientByID(clientID);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @GetMapping(path = "/email/{client_email}")
    public @ResponseBody DisplayClientDTO getClientByEmail(@PathVariable("client_email") String clientEmail) {
        try {
            return clientService.findClientByEmail(clientEmail);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @PostMapping(path = "/login")
    public @ResponseBody DisplayClientDTO login(@RequestBody LoginClientDTO loginClientDTO){
        try{
            return clientService.login(loginClientDTO.getEmail(), loginClientDTO.getPassword());
        } catch (ClientAccountNotFoundException e){
            throw  new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (InvalidLoginPasswordException | InactiveClientException exception){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, exception.getMessage(),exception);
        }
    }

    @PostMapping(path = "/addReview")
    public @ResponseBody ReviewDTO addReview(@RequestBody ReviewDTO reviewDTO){
        try {
            return clientService.addReview(reviewDTO);
        } catch (ClientNotFoundException | RealEstateNotFoundException e){
            throw  new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @GetMapping(path = "/getReviewsByClient/{client_id}")
    public @ResponseBody Set<ReviewDTO> getReviewsByClient(@PathVariable("client_id") Integer clientId){
        try{
            return  clientService.getReviewsByClient(clientId);
        } catch (ClientNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @PutMapping(path = "/addRealEstateToFavorites/{client_id}/{real_estate_id}")
    public @ResponseBody DisplayClientDTO addRealEstateToFavorites(@PathVariable ("client_id") Integer clientId, @PathVariable("real_estate_id") Integer realEstateId){
        try{
            return clientService.addRealEstateToFavorites(clientId, realEstateId);
        } catch (ClientNotFoundException | RealEstateNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @PutMapping(path = "/removeRealEstateFromFavorites/{client_id}/{real_estate_id}")
    public @ResponseBody DisplayClientDTO removeRealEstateFromFavorites(@PathVariable ("client_id") Integer clientId, @PathVariable("real_estate_id") Integer realEstateId){
        try{
            return clientService.removeRealEstateFromFavorites(clientId, realEstateId);
        } catch (ClientNotFoundException | RealEstateNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @GetMapping(path = "/getRentals/{client_id}")
    public @ResponseBody Set<RentalDTO> getRentals(@PathVariable("client_id") Integer clientId){
        try {
            return clientService.getRentals(clientId);
        } catch (ClientNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @GetMapping(path = "/getPastRentals/{client_id}")
    public @ResponseBody Set<RentalDTO> getPastRentals(@PathVariable("client_id") Integer clientId){
        try {
            return clientService.getPastRentals(clientId);
        } catch (ClientNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @GetMapping(path = "/getUpcomingRentals/{client_id}")
    public @ResponseBody Set<RentalDTO> getUpcomingRentals(@PathVariable("client_id") Integer clientId){
        try {
            return clientService.getUpcomingRentals(clientId);
        } catch (ClientNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @GetMapping(path = "/getOngoingRentals/{client_id}")
    public @ResponseBody Set<RentalDTO> getOngoingRentals(@PathVariable("client_id") Integer clientId){
        try {
            return clientService.getOngoingRentals(clientId);
        } catch (ClientNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @GetMapping(path="/hasClientReviewedRealEstate/{client_id}/{real_estate_id}")
    public boolean hasClientReviewedRealEstate(@PathVariable ("client_id") Integer clientId, @PathVariable("real_estate_id") Integer realEstateId){
        try{
            return clientService.hasClientReviewedRealEstate(clientId,realEstateId);
        } catch (ClientNotFoundException | RealEstateNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @GetMapping(path = "/getOwnedRealEstates/{client_id}")
    public @ResponseBody Set<RealEstateDTO> getOwnedRealEstates(@PathVariable("client_id") Integer clientId){
        try{
            return clientService.getOwnedRealEstates(clientId);
        } catch (ClientNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage(), e);
        }
    }

    @GetMapping(path = "/getRealEstatesForClient/{client_id}")
    public @ResponseBody Set<RealEstateDTO> getRealEstatesForClient(@PathVariable("client_id") Integer clientId){
        try{
            return clientService.getRealEstatesForClient(clientId);
        } catch (ClientNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage(), e);
        }
    }

    @PutMapping(path = "/update/{id}")
    public ResponseEntity<DisplayClientDTO> updateClient(@RequestBody CreateClientDTO updatedClientDTO,
                                                         @PathVariable("id") Integer id) {
        try {
            CreateClientDTO dbUpdatedClientDTO = clientService.updateClient(id, updatedClientDTO);
            DisplayClientDTO displayClientDTO = createToDisplayClientTransformer.transform(dbUpdatedClientDTO);
            return ResponseEntity.created(new URI("/clients/" + dbUpdatedClientDTO.getId())).body(displayClientDTO);
        } catch (URISyntaxException | ClientNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }



}

//TODO NATALIA:

//TODO:
// 1. FIX SETUP FOR accessing-data-mysql + FIX SETUP FOR REACT APP AND TEST
// 2.EXPORT POSTMAN REQUESTS TO A FILE
// -delete status from all tables OR add validations for active/non-active real
// -start the frontend app + start with project structure in react
// -think how to store the images

