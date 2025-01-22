package demo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import demo.dto.CreateClientDTO;
import demo.dto.DisplayClientDTO;
import demo.errorhandling.ClientNotFoundException;
import demo.errorhandling.DuplicatedClientUsernameException;
import demo.service.ClientService;
import demo.transformers.CreateToDisplayClientTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.net.URI;
import java.net.URISyntaxException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;

@RestController //This means that this class is a controller (also a bean)
@RequestMapping(path = "/demo/clients/") //This means URLs with /demo
@CrossOrigin(origins = "", allowedHeaders = "") //Allows any requests from browser
public class ClientController {
    @Autowired
    private ClientService clientService;

    @Autowired
    private CreateToDisplayClientTransformer createToDisplayClientTransformer;

    //GetMapping annotation is used to mark a Java method which
    //represents GET HTTP verb (read operation from CRUD database operations)
    @GetMapping(path = "/all")
    public @ResponseBody Iterable<DisplayClientDTO> getAllClients(){
        return clientService.findAllClients();
    }

    //A PathVariable is a part of a request URL and can be used when you want to be able to fetch a different resource
    //based on a parameter
    @GetMapping(path = "/id/{clientId}")
    public @ResponseBody DisplayClientDTO getClientByID(@PathVariable("clientId") Integer clientID){
        try{
            return clientService.findClientByID(clientID);
        } catch(Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage(),e);
        }
    }

    @GetMapping(path = "/firstname/{firstName}")
    public @ResponseBody DisplayClientDTO getClientByName(@PathVariable("firstName") String clientName){
        try{
            return clientService.findClientByFirstName(clientName);
        } catch(Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage(),e);
        }
    }

    @DeleteMapping(path = "/delete/id/{clientId}")
    public @ResponseBody DisplayClientDTO deleteClientById(@PathVariable("clientId") Integer id){
        try{
            return clientService.deleteClientById(id);
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage(),e);
        }

    }

    @DeleteMapping(path = "/delete/firstname/{firstName}")
    public @ResponseBody DisplayClientDTO deleteClientByName(@PathVariable("firstName") String firstName){
        try{
            return clientService.deleteClientByFirstName(firstName);
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage(),e);
        }

    }

    //This is the unique correct implementation for a POST request - using a @RequestBody to send a data
    @PostMapping(path = "/add")
    public ResponseEntity<DisplayClientDTO> createClient(@RequestBody CreateClientDTO createClientDTO){
        try{
            CreateClientDTO savedClientDTO = clientService.saveClient(createClientDTO);
            DisplayClientDTO displayClientDTO = createToDisplayClientTransformer.transform(savedClientDTO);
            return ResponseEntity.created(new URI("/clients/" + savedClientDTO.getClientId())).body(displayClientDTO);
        } catch (URISyntaxException e){
            return ResponseEntity.notFound().build();
        } catch (DuplicatedClientUsernameException exception){
            System.out.println(exception.getMessage());
            return ResponseEntity.unprocessableEntity().build();
            //return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(exception.getMessage());
        }
    }


    //Using @PathVariables to send data for a post mapping is not a valid option (even if it's possible to do it)
    //because it can expose sensitive data in the URL
    @PostMapping(path = "/add/path-variable/{username}/{password}/{email}/{firstName}/{lastName}")
    public ResponseEntity<DisplayClientDTO> createClientWithPathVariables(@PathVariable("username") String username,
                                                                          @PathVariable("password") String password,
                                                                          @PathVariable("email") String email,
                                                                          @PathVariable("lastName") String lastName,
                                                                          @PathVariable("firstName") String firstName){
        CreateClientDTO clientDTO = CreateClientDTO.builder()
                                    .username(username)
                                    .password(password)
                                    .email(email)
                                    .lastName(lastName)
                                    .firstName(firstName)
                                    .build();
        try{
            CreateClientDTO savedClientDTO = clientService.saveClient(clientDTO);
            DisplayClientDTO displayClientDTO = createToDisplayClientTransformer.transform(savedClientDTO);
            return ResponseEntity.created(new URI("/clients/" + savedClientDTO.getClientId())).body(displayClientDTO);
        } catch (URISyntaxException | DuplicatedClientUsernameException e){
            return ResponseEntity.notFound().build();
        }
    }


/*    //Sending data with @RequestParam on a POST request is not a valid option because, as in the case of @PathVariable,
    //it can expose sensitive data in the URL
    @PostMapping(path = "/add/request-params")
    public ResponseEntity<DisplayClientDTO> createClientWithRequestParams(@RequestParam String name,
                                                                          @RequestParam String address,
                                                                          @RequestParam Integer age,
                                                                          @RequestParam String username,
                                                                          @RequestParam String password) {

        CreateClientDTO clientDTO = CreateClientDTO.builder()
                .name(name)
                .clientAddress(address)
                .age(age)
                .username(username)
                .password(password)
                .build();

        try{
            CreateClientDTO savedClientDTO = clientService.saveClient(clientDTO);
            DisplayClientDTO displayClientDTO = createToDisplayClientTransformer.transform(savedClientDTO);
            return ResponseEntity.created(new URI("/clients/" + savedClientDTO.getId())).body(displayClientDTO);
        } catch (URISyntaxException | DuplicatedClientUsernameException e){
            return ResponseEntity.notFound().build();
        }
    }

    // @PutMapping is for updating an existing resource, but we need to send the entire object on the request
    // (containing both the modified fields and the unmodified fields)
    @PutMapping(path = "/update/{id}")
    public ResponseEntity<DisplayClientDTO> updateClient(@RequestBody CreateClientDTO updatedClientDTO,
                                                         @PathVariable("id") Integer id) {
        try{
            CreateClientDTO dbUpdatedClientDTO = clientService.updateClient(id, updatedClientDTO);
            DisplayClientDTO displayClientDTO = createToDisplayClientTransformer.transform(dbUpdatedClientDTO);
            return ResponseEntity.created(new URI("/clients/" + dbUpdatedClientDTO.getId())).body(displayClientDTO);
        } catch (URISyntaxException | ClientNotFoundException e ){
            return ResponseEntity.notFound().build();
        } catch (DuplicatedClientUsernameException exception){
            System.out.println(exception.getMessage());
            return ResponseEntity.unprocessableEntity().build();
        }
    }*/

    @PatchMapping(path = "/patch/{id}", consumes = "application/json-patch+json")
    public @ResponseBody DisplayClientDTO patchClient(@PathVariable("id") Integer id,
                                                      @RequestBody JsonPatch jsonPatch){
        try{
            return clientService.patchClient(id, jsonPatch);
        } catch (ClientNotFoundException e){
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (JsonPatchException | JsonProcessingException exception){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage(), exception);
        }
    }


    //TODO: PATCH examples in POSTMAN
}
