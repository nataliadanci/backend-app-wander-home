package demo.controller;

import demo.dto.RentalDTO;
import demo.dto.SwitchBookingDTO;
import demo.errorhandling.*;
import demo.service.SwitchBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Set;

@RestController //This means that this class is a controller (also a bean)
@RequestMapping(path = "/switchBookings/")
@CrossOrigin(origins = "", allowedHeaders = "") //Allows any requests from browser
public class SwitchBookingController {

    @Autowired
    private SwitchBookingService switchBookingService;
    @PostMapping(path = "/createSwitchBooking")
    public ResponseEntity<SwitchBookingDTO> createSwitchBooking(@RequestBody SwitchBookingDTO switchBookingDTO) {
        try{
            SwitchBookingDTO savedSwitchBookingDTO = switchBookingService.createSwitchBooking(switchBookingDTO);
            return ResponseEntity.created(new URI("/switchBookings/" + savedSwitchBookingDTO.getId())).body(savedSwitchBookingDTO);
        } catch (RealEstateNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (SwitchBookingDateException | RealEstateAlreadySwitchedException | SwitchBookingException exceptionDate){
            throw new ResponseStatusException(HttpStatus.CONFLICT, exceptionDate.getMessage(),exceptionDate);
        } catch (URISyntaxException exception){
            throw new RuntimeException(exception);
        }
    }

    @GetMapping(path = "/{id}")
    public @ResponseBody SwitchBookingDTO getSwitchBookingById(@PathVariable("id") Integer id){
        try {
            return switchBookingService.getSwitchBookingById(id);
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @GetMapping(path = "/switchBookingsForRealEstate/{real_estate_id}")
    public @ResponseBody Set<SwitchBookingDTO> getSwitchBookingsForRealEstate(@PathVariable("real_estate_id") Integer realEstateId) {
        try{
            return switchBookingService.getSwitchBookingsForRealEstate(realEstateId);
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @GetMapping(path = "/switchBookingsForClient/{client_id}")
    public @ResponseBody Set<SwitchBookingDTO> getSwitchBookingsForClient(@PathVariable("client_id") Integer clientId) {
        try{
            return switchBookingService.getSwitchBookingsForClient(clientId);
        } catch (ClientNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @GetMapping(path = "/pastSwitchBookingsForClient/{client_id}")
    public @ResponseBody Set<SwitchBookingDTO> getPastSwitchBookingsForClient(@PathVariable("client_id") Integer clientId){
        try {
            return switchBookingService.getPastSwitchBookingsForClient(clientId);
        } catch (ClientNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @GetMapping(path = "/upcomingSwitchBookingsForClient/{client_id}")
    public @ResponseBody Set<SwitchBookingDTO> getUpcomingSwitchBookingsForClient(@PathVariable("client_id") Integer clientId){
        try {
            return switchBookingService.getUpcomingSwitchBookingsForClient(clientId);
        } catch (ClientNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @GetMapping(path = "/ongoingSwitchBookingsForClient/{client_id}")
    public @ResponseBody Set<SwitchBookingDTO> getOngoingSwitchBookingsForClient(@PathVariable("client_id") Integer clientId){
        try {
            return switchBookingService.getOngoingSwitchBookingsForClient(clientId);
        } catch (ClientNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @PutMapping(path = "/update/{id}")
    public ResponseEntity<SwitchBookingDTO> updateSwitchBooking(@RequestBody SwitchBookingDTO updatedSwitchBookingDTO,
                                                  @PathVariable("id") Integer id) {
        try {
            SwitchBookingDTO switchBookingDTO = switchBookingService.updateSwitchBooking(id, updatedSwitchBookingDTO);
            return ResponseEntity.created(new URI("/switchBookings/" +switchBookingDTO.getId())).body(switchBookingDTO);
        } catch (URISyntaxException | SwitchBookingNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
