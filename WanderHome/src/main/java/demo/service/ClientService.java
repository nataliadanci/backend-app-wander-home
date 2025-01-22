package demo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import demo.dto.CreateClientDTO;
import demo.dto.DisplayClientDTO;
import demo.errorhandling.ClientNotFoundException;
import demo.errorhandling.DuplicatedClientUsernameException;

public interface ClientService {
    Iterable<DisplayClientDTO> findAllClients();
    DisplayClientDTO findClientByID(Integer id) throws ClientNotFoundException;
    DisplayClientDTO findClientByFirstName(String name) throws ClientNotFoundException;
    DisplayClientDTO deleteClientById(Integer id) throws ClientNotFoundException;
    DisplayClientDTO deleteClientByFirstName(String name) throws ClientNotFoundException;
    CreateClientDTO saveClient(CreateClientDTO createClientDTO) throws DuplicatedClientUsernameException;
    CreateClientDTO updateClient(Integer id, CreateClientDTO createClientDTO) throws ClientNotFoundException, DuplicatedClientUsernameException;
    DisplayClientDTO patchClient(Integer id, JsonPatch jsonPatch) throws ClientNotFoundException, JsonPatchException, JsonProcessingException;

}
