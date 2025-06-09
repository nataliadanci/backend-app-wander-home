package demo.controller;

import demo.dto.*;
import demo.errorhandling.ClientNotFoundException;
import demo.errorhandling.RealEstateNotFoundException;
import demo.errorhandling.RealEstateOwnerNotFoundException;
import demo.service.RealEstateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController //This means that this class is a controller (also a bean)
@RequestMapping(path = "/real_estates/")
@CrossOrigin(origins = "", allowedHeaders = "") //Allows any requests from browser
public class RealEstateController {

    @Autowired
    private RealEstateService realEstateService;

    @PostMapping(path = "/add")
    public ResponseEntity<RealEstateDTO> createRealEstate(@RequestBody RealEstateDTO realEstateDTO){
        try{
            RealEstateDTO savedRealEstateDTO = realEstateService.saveRealEstate(realEstateDTO);
            return ResponseEntity.created(new URI("/real_estates/" + savedRealEstateDTO.getId())).body(savedRealEstateDTO);
        } catch (URISyntaxException e){
            return ResponseEntity.notFound().build();
        } catch (ClientNotFoundException exception){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage(),exception);
        }
    }

    @GetMapping(path = "/id/{real_estate_id}")
    public @ResponseBody RealEstateDTO getRealEstateByID(@PathVariable("real_estate_id") Integer realEstateID) {
        try {
            return realEstateService.findRealEstateById(realEstateID);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @GetMapping(path = "/all")
    public @ResponseBody Set<RealEstateDTO> getAllRealEstates() {
         Iterable<RealEstateDTO> realEstates = realEstateService.findAllRealEstates();

         return StreamSupport.stream(realEstates.spliterator(), false)
                         .collect(Collectors.toSet());
    }

    @GetMapping(path = "/getReviewsForRealEstate/{real_estate_id}")
    public @ResponseBody List<ReviewDTO> getReviewsForRealEstate(@PathVariable("real_estate_id") Integer realEstateId){
        try{
            return realEstateService.getReviewsForRealEstate(realEstateId);
        } catch (RealEstateNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @GetMapping(path="/getClientsFavoriteRealEstates/{client_id}")
    public @ResponseBody Set<RealEstateDTO> getClientsFavoriteRealEstates(@PathVariable("client_id") Integer clientId){
        try{
            return realEstateService.getClientsFavoriteRealEstates(clientId);
        } catch (ClientNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(),e);
        }
    }

    @GetMapping(path = "/getNumberOfInterestedClients/{real_estate_id}")
    public int getNumberOfInterestedClients(@PathVariable("real_estate_id") Integer realEstateId){
        try {
            return realEstateService.getNumberOfInterestedClients(realEstateId);
        } catch (RealEstateNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage(),e);
        }
    }

    @GetMapping(path = "/getRealEstateOwner/{real_estate_id}")
    public @ResponseBody DisplayClientDTO getRealEstateOwner(@PathVariable("real_estate_id") Integer realEstateId){
        try{
            return realEstateService.getRealEstateOwner(realEstateId);
        } catch (RealEstateOwnerNotFoundException  e){
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, e.getMessage(), e);
        } catch (RealEstateNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @GetMapping(path = "/getRentals/{real_estate_id}")
    public @ResponseBody Set<RentalDTO> getRentals(@PathVariable("real_estate_id") Integer realEstateId){
        try {
            return realEstateService.getRentals(realEstateId);
        } catch (RealEstateNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @PutMapping(path = "/update/{id}")
    public ResponseEntity<RealEstateDTO> updateRealEstate(@RequestBody RealEstateDTO updatedRealEstateDTO,
                                                         @PathVariable("id") Integer id) {
        try {
            RealEstateDTO realEstateDTO = realEstateService.updateRealEstate(id, updatedRealEstateDTO);
            return ResponseEntity.created(new URI("/real_estates/" + realEstateDTO.getId())).body(realEstateDTO);
        } catch (URISyntaxException | RealEstateNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
