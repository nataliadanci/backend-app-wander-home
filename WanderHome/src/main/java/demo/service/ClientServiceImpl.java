package demo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import demo.dto.CreateClientDTO;
import demo.dto.DisplayClientDTO;
import demo.entity.Client;
import demo.errorhandling.ClientNotFoundException;
import demo.errorhandling.DuplicatedClientUsernameException;
import demo.repository.ClientRepository;
import demo.transformers.CreateClientTransformer;
import demo.transformers.DisplayClientTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ClientServiceImpl implements ClientService {

    //With the Autowired annotation we inject in this class a bean (an instance object which was already created)
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private DisplayClientTransformer displayClientTransformer;

    @Autowired
    private CreateClientTransformer createClientTransformer;

    @Override
    public Iterable<DisplayClientDTO> findAllClients(){
        Iterable<Client> clientEntities = clientRepository.findAll();
        return StreamSupport.stream(clientEntities.spliterator(), false)
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
    public DisplayClientDTO findClientByFirstName(String firstName) throws ClientNotFoundException {
        Optional<Client> optionalClient = clientRepository.findByFirstName(firstName);
        Client foundClient = optionalClient.orElseThrow(()->new ClientNotFoundException("No client found with this name."));
        return displayClientTransformer.fromEntity(foundClient);
    }
    @Override
    public DisplayClientDTO deleteClientById(Integer id) throws ClientNotFoundException{
        Optional<Client> clientToDelete = clientRepository.findById(id);
        Client foundClientDeleted = clientToDelete.orElseThrow(()-> new ClientNotFoundException("No client found with this ID."));
        clientRepository.delete(foundClientDeleted);
        return displayClientTransformer.fromEntity(foundClientDeleted);
    }

    @Override
    public DisplayClientDTO deleteClientByFirstName(String name) throws ClientNotFoundException{
        Optional<Client> clientToDelete = clientRepository.findByFirstName(name);
        Client foundClientDeleted = clientToDelete.orElseThrow(()-> new ClientNotFoundException("No client found with this name."));
        clientRepository.delete(foundClientDeleted);
        return displayClientTransformer.fromEntity(foundClientDeleted);
    }

    @Override
    public CreateClientDTO saveClient(CreateClientDTO createClientDTO) throws DuplicatedClientUsernameException{
        Client clientToBeSaved = createClientTransformer.fromDTO(createClientDTO);
        Integer duplicatedClientId = isUsernameDuplicated(createClientDTO.getUsername(), null);

        if(duplicatedClientId != null){
            throw new DuplicatedClientUsernameException("Username already exists. Client with id "
                    + duplicatedClientId + " has this username.");
        }
        Client clientSaved = clientRepository.save(clientToBeSaved);
        return createClientTransformer.fromEntity(clientSaved);
    }

    @Override
    public CreateClientDTO updateClient(Integer id, CreateClientDTO updatedClientDTO) throws ClientNotFoundException,DuplicatedClientUsernameException {
        Optional<Client> clientToUpdate = clientRepository.findById(id);
        Client foundClientToUpdate = clientToUpdate.orElseThrow(()-> new ClientNotFoundException("No client found with this ID."));
        Integer duplicatedClientId = isUsernameDuplicated(updatedClientDTO.getUsername(), id);

        if(duplicatedClientId != null){
            throw new DuplicatedClientUsernameException("Username already exists. Client with id: "
                    + duplicatedClientId + " already has this username.");
        }

        //We need to Override the entity retrieved from the Database (foundClientToUpdate)
        // with the entire object (with all the fields) received from the request

        //We don't need to set the id because it's set on the object retrieved from the Database
        //foundClientToUpdate.setId(updatedClientDTO.getId());


        foundClientToUpdate.setUsername(updatedClientDTO.getUsername());
        foundClientToUpdate.setPassword(updatedClientDTO.getPassword());
        foundClientToUpdate.setEmail(updatedClientDTO.getEmail());
        foundClientToUpdate.setFirstName(updatedClientDTO.getFirstName());
        foundClientToUpdate.setLastName(updatedClientDTO.getLastName());
        foundClientToUpdate.setPhone(updatedClientDTO.getPhone());


        //For PUT (update) we use the same CRUD method from the repository (save) as we do for POST (create)
        Client dbUpdatedClient = clientRepository.save(foundClientToUpdate);

        CreateClientDTO dbUpdatedClientDTO = createClientTransformer.fromEntity(dbUpdatedClient);

        return dbUpdatedClientDTO;
    }

    private Integer isUsernameDuplicated(String username, Integer currentClientId){
        Iterable<DisplayClientDTO> allClients = findAllClients();
        List<DisplayClientDTO> allClientsList = StreamSupport
                .stream(allClients.spliterator(), false)
                .toList();

        //In case of PUT the currentClientId is never null because we already have found in the database the
        //client to be updated
        if(currentClientId != null){
            allClientsList = allClientsList
                    .stream()
                    .filter(clientDTO -> clientDTO.getClientId() != currentClientId )
                    .toList();
        } else{
            //In case of POST currentClientId is always null before performing the database operation
            //We don't need to exclude teh previous version of the client object
        }
        Optional<DisplayClientDTO> optionalDuplicatedClient = allClientsList.stream()
                .filter(displayClientDTO -> displayClientDTO.getUsername().equals(username))
                .findFirst();
        if(optionalDuplicatedClient.isPresent()){
            DisplayClientDTO client = optionalDuplicatedClient.get();
            return client.getClientId();
        }
        return null;
    }

    @Override
    public DisplayClientDTO patchClient(Integer id, JsonPatch jsonPatch) throws ClientNotFoundException, JsonPatchException, JsonProcessingException{
        Optional<Client> clientToUpdate = clientRepository.findById(id);
        Client foundClientToUpdate = clientToUpdate.orElseThrow(()-> new ClientNotFoundException("No client found with this ID."));

        Client patchedClient = applyPatchToClient(foundClientToUpdate, jsonPatch);
        Client updatedClient = clientRepository.save(patchedClient);
        return displayClientTransformer.fromEntity(updatedClient);
    }

    private Client applyPatchToClient(Client foundClientToUpdate, JsonPatch jsonPatch) throws JsonPatchException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode patched = jsonPatch.apply(objectMapper.convertValue(foundClientToUpdate, JsonNode.class));
        return objectMapper.treeToValue(patched, Client.class);
    }
}

