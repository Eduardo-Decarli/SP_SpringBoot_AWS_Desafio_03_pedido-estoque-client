package com.compass.ms_client.controller;

import com.compass.ms_client.entities.Client;
import com.compass.ms_client.services.ClientService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
@AllArgsConstructor
@RestController
@RequestMapping("/clients")
public class ClientController {

    private final ClientService clientService;

    @PostMapping
    public ResponseEntity<String> createClient(@Valid @RequestBody Client client) {
        clientService.createClient(client);
        return ResponseEntity.status(HttpStatus.CREATED).body("Client created successfully");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> findClientById(@PathVariable Long id) {
        Client client = clientService.findClientById(id);
        return ResponseEntity.ok(client);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Client> findClientByEmail(@PathVariable String email) {
        Client client = clientService.findClientByEmail(email);
        return ResponseEntity.ok(client);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Client> updateClientById(@Valid @RequestBody Client client, @PathVariable Long id) {
        Client updatedClient = clientService.updateClientById(client, id);
        return ResponseEntity.ok(updatedClient);
    }

    @PutMapping("/{email}")
    public ResponseEntity<Client> updateClientByEmail(@Valid @RequestBody Client client, @PathVariable String email) {
        Client updatedClient = clientService.updateClientByEmail(client, email);
        return ResponseEntity.ok(updatedClient);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteClientById(@PathVariable Long id) {
        clientService.deleteClientById(id);
        return ResponseEntity.ok("Client deleted successfully");
    }
}
