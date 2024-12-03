package com.compass.ms_client.web.controller;

import com.compass.ms_client.entities.Client;
import com.compass.ms_client.services.ClientService;
import com.compass.ms_client.web.dto.ClientCreateDTO;
import com.compass.ms_client.web.dto.ClientResponseDTO;
import com.compass.ms_client.web.dto.mapper.ClientMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/client")
@Validated
public class ClientController {

    private final ClientService clientService;

    @PostMapping
    public ResponseEntity<ClientResponseDTO> createClient(@Valid @RequestBody ClientCreateDTO create) {
        ClientResponseDTO client = clientService.createClient(create);
        client.add(linkTo(methodOn(ClientController.class).findClientById(client.getId())).withRel("find your account by id"));
        client.add(linkTo(methodOn(ClientController.class).findClientByEmail(client.getEmail())).withRel("find your account by email"));
        return ResponseEntity.status(HttpStatus.CREATED).body(client);
    }

    @GetMapping
    public ResponseEntity<List<ClientResponseDTO>> findAllClients() {
        List<ClientResponseDTO> clients = clientService.findAllClients();
        return ResponseEntity.ok(clients);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientResponseDTO> findClientById(@PathVariable Long id) {
        Client client = clientService.findClientById(id);
        return ResponseEntity.ok(ClientMapper.toDto(client));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<ClientResponseDTO> findClientByEmail(@PathVariable String email) {
        Client client = clientService.findClientByEmail(email);
        return ResponseEntity.ok(ClientMapper.toDto(client));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientResponseDTO> updateClientById(@Valid @RequestBody ClientCreateDTO client, @PathVariable Long id) {
        ClientResponseDTO updatedClient = clientService.updateClientById(client, id);
        updatedClient.add(linkTo(methodOn(ClientController.class).findClientById(updatedClient.getId())).withRel("find your account by id"));
        updatedClient.add(linkTo(methodOn(ClientController.class).findClientByEmail(updatedClient.getEmail())).withRel("find your account by email"));
        return ResponseEntity.ok(updatedClient);
    }

    @PutMapping("/email/{email}")
    public ResponseEntity<ClientResponseDTO> updateClientByEmail(@Valid @RequestBody ClientCreateDTO client, @PathVariable String email) {
        ClientResponseDTO updatedClient = clientService.updateClientByEmail(client, email);
        updatedClient.add(linkTo(methodOn(ClientController.class).findClientById(updatedClient.getId())).withRel("find your account by id"));
        updatedClient.add(linkTo(methodOn(ClientController.class).findClientByEmail(updatedClient.getEmail())).withRel("find your account by email"));
        return ResponseEntity.ok(updatedClient);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteClientById(@PathVariable Long id) {
        clientService.deleteClientById(id);
        return ResponseEntity.ok("Client deleted successfully");
    }
}

