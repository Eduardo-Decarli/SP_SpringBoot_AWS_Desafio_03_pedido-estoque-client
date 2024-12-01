package com.compass.ms_client.services;

import com.compass.ms_client.entities.Client;
import com.compass.ms_client.exceptions.DataUniqueViolationException;
import com.compass.ms_client.exceptions.EntityNotFoundException;
import com.compass.ms_client.repositories.ClientFunctionsRepository;
import com.compass.ms_client.repositories.ClientRepository;
import com.compass.ms_client.web.dto.ClientCreateDTO;
import com.compass.ms_client.web.dto.ClientResponseDTO;
import com.compass.ms_client.web.dto.mapper.ClientMapper;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Log4j2
public class ClientService implements ClientFunctionsRepository {

    private final ClientRepository repo;

    public ClientResponseDTO createClient(ClientCreateDTO create) {
        if(repo.findByEmail(create.getEmail().toUpperCase()) != null) {
            throw new DataUniqueViolationException("There is already a email registered");
        }
        Client client = ClientMapper.toClient(create);
        client.setEmail(client.getEmail().toUpperCase());
        log.info("creating a client");
        client = repo.save(client);
        return ClientMapper.toDto(client);
    }

    public List<ClientResponseDTO> findAllClients() {
        List<Client> clients = repo.findAll();
        if(clients.isEmpty()) {
            throw new EntityNotFoundException("There are not users in the system");
        }
        return ClientMapper.toListDTO(clients);
    }

    public Client findClientById(Long id) {
        Client client = repo.findById(id).orElseThrow(
                () -> new EntityNotFoundException("error: not found client by id " + id)
        );
        log.info("finding a client by id");
        return client;
    }

    public Client findClientByEmail(String email) {
        log.info("finding a client by email");
        Client client = repo.findByEmail(email.toUpperCase());
        if(client == null) {
            throw new EntityNotFoundException("error: not found client by email " + email);
        }
        return client;
    }

    public ClientResponseDTO updateClientById(ClientCreateDTO update, Long id) {
        Client correntClient = findClientById(id);
        if(repo.findByEmail(update.getEmail().toUpperCase()) != null && !(update.getEmail().equalsIgnoreCase(correntClient.getEmail()))) {
            throw new DataUniqueViolationException("There is already a client registered with that email");
        }
        correntClient.setName(update.getName());
        correntClient.setEmail(update.getEmail().toUpperCase());

        log.info("updating a client by id");
        Client client = repo.save(correntClient);
        return ClientMapper.toDto(client);
    }

    public ClientResponseDTO updateClientByEmail(ClientCreateDTO update, String email) {
        Client correntClient = findClientByEmail(email);
        if(repo.findByEmail(update.getEmail().toUpperCase()) != null && !(update.getEmail().equalsIgnoreCase(correntClient.getEmail()))) {
            throw new DataUniqueViolationException("There is already a client registered with that email");
        }
        correntClient.setName(update.getName());
        correntClient.setEmail(update.getEmail().toUpperCase());

        log.info("updating a client by email");
        Client client = repo.save(correntClient);
        return ClientMapper.toDto(client);
    }

    public void deleteClientById(Long id) {
        Client client = findClientById(id);
        log.info("deleting a client");
        repo.delete(client);
    }
}
