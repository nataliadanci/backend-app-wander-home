package demo.controller;

import demo.dto.RentalDTO;
import demo.errorhandling.*;
import demo.service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping(path = "/rentals/")
@CrossOrigin(origins = "", allowedHeaders = "")
public class RentalController {

    @Autowired
    private RentalService rentalService;

    @PostMapping(path = "/createRental")
    public ResponseEntity<RentalDTO> createRental(@RequestBody RentalDTO rentalDTO){
        try{
            RentalDTO createdRentalDTO = rentalService.createRental(rentalDTO);
            return ResponseEntity.created(new URI("/rentals/" + createdRentalDTO.getId())).body(createdRentalDTO);
        } catch (ClientNotFoundException | RealEstateNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (RealEstateAlreadyRentedException | RentalDateException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping(path = "/update/{id}")
    public ResponseEntity<RentalDTO> updateRental(@RequestBody RentalDTO updatedRentalDTO,
                                                          @PathVariable("id") Integer id) {
        try {
            RentalDTO rentalDTO = rentalService.updateRental(id, updatedRentalDTO);
            return ResponseEntity.created(new URI("/rentals/" + rentalDTO.getId())).body(rentalDTO);
        } catch (URISyntaxException | RentalNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
