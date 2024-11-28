package com.compass.ms_client.web.dto.mapper;

import com.compass.ms_client.entities.Client;
import com.compass.ms_client.web.dto.ClientCreateDTO;
import com.compass.ms_client.web.dto.ClientResponseDTO;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class ClientMapper {

    public static Client toClient(ClientCreateDTO createDTO) {
        return new ModelMapper().map(createDTO, Client.class);
    }

    public static ClientResponseDTO toDto(Client client) {
        return new ModelMapper().map(client, ClientResponseDTO.class);
    }

    public static List<ClientResponseDTO> toListDTO(List<Client> clients) {
        return clients.stream().map(client -> toDto(client)).collect(Collectors.toList());
    }
}
