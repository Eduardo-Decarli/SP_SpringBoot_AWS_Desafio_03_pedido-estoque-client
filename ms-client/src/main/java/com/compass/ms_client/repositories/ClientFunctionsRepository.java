package com.compass.ms_client.repositories;

import com.compass.ms_client.entities.Client;
import com.compass.ms_client.web.dto.ClientCreateDTO;
import com.compass.ms_client.web.dto.ClientResponseDTO;

import java.util.List;

public interface ClientFunctionsRepository {
    ClientResponseDTO createClient(ClientCreateDTO create);
    List<ClientResponseDTO> findAllClients();
    Client findClientById(Long id);
    Client findClientByEmail(String email);
    ClientResponseDTO updateClientById(ClientCreateDTO update, Long id);
    ClientResponseDTO updateClientByEmail(ClientCreateDTO update, String email);
    void deleteClientById(Long id);
}
