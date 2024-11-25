package com.compass.ms_client.services;

import com.compass.ms_client.entities.Client;
import com.compass.ms_client.exceptions.ErrorNotFoundException;
import com.compass.ms_client.exceptions.ErrorNotNullViolation;
import com.compass.ms_client.repositories.ClientRepository;
import com.compass.ms_client.web.dto.ClientCreateDTO;
import com.compass.ms_client.web.dto.ClientResponseDTO;
import com.compass.ms_client.web.dto.mapper.ClientMapper;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Log4j2
public class ClientService {

    private final ClientRepository repo;

    public ClientResponseDTO createClient(ClientCreateDTO create) {
        if(create == null) {
            throw new ErrorNotNullViolation("Client can't be null");
        }
        Client client = ClientMapper.toClient(create);

        log.info("creating a client");
        client = repo.save(client);
        return ClientMapper.toDto(client);
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

    public ClientResponseDTO updateClientById(ClientCreateDTO update, Long id) {
        Client correntClient = findClientById(id);
        correntClient.setName(update.getName());
        correntClient.setEmail(update.getEmail());

        log.info("updating a client by id");
        Client client = repo.save(correntClient);
        return ClientMapper.toDto(client);
    }

    public ClientResponseDTO updateClientByEmail(ClientCreateDTO update, String email) {
        Client correntClient = findClientByEmail(email);
        correntClient.setName(update.getName());
        correntClient.setEmail(update.getEmail());

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
