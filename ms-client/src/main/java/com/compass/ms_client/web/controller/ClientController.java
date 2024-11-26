package com.compass.ms_client.web.controller;

import com.compass.ms_client.entities.Client;
import com.compass.ms_client.services.ClientService;
import com.compass.ms_client.web.dto.ClientCreateDTO;
import com.compass.ms_client.web.dto.ClientResponseDTO;
import com.compass.ms_client.web.dto.mapper.ClientMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/client")
@Validated
public class ClientController {

    private final ClientService clientService;

    @Operation(summary = "Create a User",
               description = "Create a new User and save it in the sistem")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User registered sucessful")
    })
    @PostMapping
    public ResponseEntity<ClientResponseDTO> createClient(@Valid @RequestBody ClientCreateDTO create) {
        ClientResponseDTO client = clientService.createClient(create);
        return ResponseEntity.status(HttpStatus.CREATED).body(client);
    }

    @Operation(summary = "Find all clients",
            description = "Retrieve all users and their data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users found successfully"),
            @ApiResponse(responseCode = "404", description = "Users not found")
    })
    @GetMapping
    public ResponseEntity<List<ClientResponseDTO>> findAllClients() {
        List<ClientResponseDTO> clients = clientService.findAllClients();
        return ResponseEntity.ok(clients);
    }


    @Operation(summary = "Find a user by ID",
            description = "Retrieve a user's data by their unique ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ClientResponseDTO> findClientById(@PathVariable Long id) {
        Client client = clientService.findClientById(id);
        return ResponseEntity.ok(ClientMapper.toDto(client));
    }


    @Operation(summary = "Find a user by email",
            description = "Retrieve a user's data using their email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/email/{email}")
    public ResponseEntity<ClientResponseDTO> findClientByEmail(@PathVariable String email) {
        Client client = clientService.findClientByEmail(email);
        return ResponseEntity.ok(ClientMapper.toDto(client));
    }

    @Operation(summary = "Update a user by ID",
            description = "Update an existing user's data using their unique ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated successfully"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "400", description = "Invalid request payload")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ClientResponseDTO> updateClientById(@Valid @RequestBody ClientCreateDTO client, @PathVariable Long id) {
        ClientResponseDTO updatedClient = clientService.updateClientById(client, id);
        return ResponseEntity.ok(updatedClient);
    }

    @Operation(summary = "Update a user by email",
            description = "Update an existing user's data using their email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated successfully"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "400", description = "Invalid request payload")
    })
    @PutMapping("/email/{email}")
    public ResponseEntity<ClientResponseDTO> updateClientByEmail(@Valid @RequestBody ClientCreateDTO client, @PathVariable String email) {
        ClientResponseDTO updatedClient = clientService.updateClientByEmail(client, email);
        return ResponseEntity.ok(updatedClient);
    }

    @Operation(summary = "Delete a user by ID",
            description = "Delete a user from the system using their unique ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User deleted successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteClientById(@PathVariable Long id) {
        clientService.deleteClientById(id);
        return ResponseEntity.ok("Client deleted successfully");
    }
}

