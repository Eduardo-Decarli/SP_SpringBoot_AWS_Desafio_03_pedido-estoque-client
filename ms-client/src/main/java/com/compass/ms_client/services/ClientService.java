package com.compass.ms_client.services;

import com.compass.ms_client.entities.Client;
import com.compass.ms_client.exceptions.ErrorNotFoundException;
import com.compass.ms_client.exceptions.ErrorNotNullViolation;
import com.compass.ms_client.repositories.ClientRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Log4j2
public class ClientService {

    private final ClientRepository repo;

    public void createClient(Client client) {
        if(client == null) {
            throw new ErrorNotNullViolation("Client can't be null");
        }
        log.info("creating a client");
        repo.save(client);
    }

    public Client findClientById(Long id) {
        Client client = repo.findById(id).orElseThrow(
                () -> new ErrorNotFoundException("error: not found client by id " + id)
        );
        log.info("finding a client by id");
        return client;
    }

    public Client findClientByEmail(String email) {
        log.info("finding a client by email");
        Client client = repo.findByEmail(email);
        if(client == null) {
            throw new ErrorNotFoundException("error: not found client by email " + email);
        }
        return client;
    }

    public Client updateClientById(Client client, Long id) {
        Client correntClient = findClientById(id);
        correntClient.setName(client.getName());
        correntClient.setEmail(client.getEmail());
        log.info("updating a client by id");
        return repo.save(correntClient);
    }

    public Client updateClientByEmail(Client client, String email) {
        Client correntClient = findClientByEmail(email);
        correntClient.setName(client.getName());
        correntClient.setEmail(client.getEmail());
        log.info("updating a client by email");
        return repo.save(correntClient);
    }

    public void deleteClientById(Long id) {
        Client client = findClientById(id);
        log.info("deleting a client");
        repo.delete(client);
    }
}
