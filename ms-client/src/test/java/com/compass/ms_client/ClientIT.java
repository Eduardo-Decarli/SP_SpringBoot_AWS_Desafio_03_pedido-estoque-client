package com.compass.ms_client;

import com.compass.ms_client.exceptions.handler.ErrorMessage;
import com.compass.ms_client.web.dto.ClientCreateDTO;
import com.compass.ms_client.web.dto.ClientResponseDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.integration.IntegrationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Sql(scripts = "/sql-Client/InsertClient.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql-Client/DeleteClient.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ClientIT {

	@Autowired
	WebTestClient testClient;

	@Test
	public void createClient_WithValidData_ReturnsStatus201() {
		ClientResponseDTO responseBody = testClient
				.post()
				.uri("/api/v1/client")
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(new ClientCreateDTO("Rodrigo Silva", "teste@example.com"))
				.exchange()
				.expectStatus().isCreated()
				.expectBody(ClientResponseDTO.class)
				.returnResult().getResponseBody();

		assertThat(responseBody).isNotNull();
		assertThat(responseBody.getName()).isNotNull();
		assertThat(responseBody.getName()).isEqualTo("Rodrigo Silva");
		assertThat(responseBody.getEmail()).isNotNull();
		assertThat(responseBody.getEmail()).isEqualTo("teste@example.com".toUpperCase());
	}

	@Test
	public void createClient_WithUniqueValidationEmail_ReturnsStatus404() {
		ErrorMessage responseBody = testClient
				.post()
				.uri("/api/v1/client")
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(new ClientCreateDTO("Rodrigo Silva", "joao.silva@example.com"))
				.exchange()
				.expectStatus().isBadRequest()
				.expectBody(ErrorMessage.class)
				.returnResult().getResponseBody();

		assertThat(responseBody).isNotNull();
		assertThat(responseBody.getStatus()).isEqualTo(400);
	}

	@Test
	public void createClient_WithInvalidEmail_ReturnsStatus422() {
		ErrorMessage responseBody = testClient
				.post()
				.uri("/api/v1/client")
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(new ClientCreateDTO("Rodrigo Silva", ""))
				.exchange()
				.expectStatus().isBadRequest()
				.expectBody(ErrorMessage.class)
				.returnResult().getResponseBody();

		assertThat(responseBody).isNotNull();
		assertThat(responseBody.getStatus()).isEqualTo(400);
	}

	@Test
	public void createClient_WithInvalidName_ReturnsStatus422() {
		ErrorMessage responseBody = testClient
				.post()
				.uri("/api/v1/client")
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(new ClientCreateDTO("", "teste@teste.com"))
				.exchange()
				.expectStatus().isEqualTo(400)
				.expectBody(ErrorMessage.class)
				.returnResult().getResponseBody();

		assertThat(responseBody).isNotNull();
		assertThat(responseBody.getStatus()).isEqualTo(400);
	}

	@Test
	public void findAllClients_WithClientsOnDB_ReturnsStatus200() {
		List<ClientResponseDTO> responseBody = testClient
				.get()
				.uri("/api/v1/client")
				.exchange()
				.expectStatus().isOk()
				.expectBodyList(ClientResponseDTO.class)
				.returnResult().getResponseBody();

		assertThat(responseBody).isNotNull();
	}

	@Test
	public void findClientById_WithValidId_ReturnsStatus200() {
		ClientResponseDTO responseBody = testClient
				.get()
				.uri("/api/v1/client/1")
				.exchange()
				.expectStatus().isOk()
				.expectBody(ClientResponseDTO.class)
				.returnResult().getResponseBody();

		assertThat(responseBody).isNotNull();
		assertThat(responseBody.getName()).isEqualTo("João Silva");
		assertThat(responseBody.getEmail()).isEqualTo("joao.silva@example.com".toUpperCase());
	}

	@Test
	public void findClientById_WithInvalidId_ReturnsStatus404() {
		ErrorMessage responseBody = testClient
				.get()
				.uri("/api/v1/client/100")
				.exchange()
				.expectStatus().isNotFound()
				.expectBody(ErrorMessage.class)
				.returnResult().getResponseBody();

		assertThat(responseBody).isNotNull();
		assertThat(responseBody.getStatus()).isEqualTo(404);
	}

	@Test
	public void findClientByEmail_WithValidEmail_ReturnsStatus200() {
		ClientResponseDTO responseBody = testClient
				.get()
				.uri("/api/v1/client/email/joao.silva@example.com")
				.exchange()
				.expectStatus().isOk()
				.expectBody(ClientResponseDTO.class)
				.returnResult().getResponseBody();

		assertThat(responseBody).isNotNull();
		assertThat(responseBody.getEmail()).isEqualTo("joao.silva@example.com".toUpperCase());
	}

	@Test
	public void findClientByEmail_WithInvalidEmail_ReturnsStatus404() {
		ErrorMessage responseBody = testClient
				.get()
				.uri("/api/v1/client/email/marcos.silva@example.com")
				.exchange()
				.expectStatus().isNotFound()
				.expectBody(ErrorMessage.class)
				.returnResult().getResponseBody();

		assertThat(responseBody).isNotNull();
		assertThat(responseBody.getStatus()).isEqualTo(404);

	}

	@Test
	@Sql(scripts = "/sql-Client/DeleteClient.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	public void findAllClients_WithInvalidClientsOnDB_ReturnsStatus422() {
		ErrorMessage responseBody = testClient
				.get()
				.uri("/api/v1/client")
				.exchange()
				.expectStatus().isNotFound()
				.expectBody(ErrorMessage.class)
				.returnResult().getResponseBody();

		assertThat(responseBody).isNotNull();
		assertThat(responseBody.getStatus()).isEqualTo(404);
	}

	@Test
	public void updateClientById_WithValidData_ReturnsStatus200() {
		ClientResponseDTO responseBody = testClient
				.put()
				.uri("/api/v1/client/1")
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(new ClientCreateDTO("Rodrigo Silva", "teste@example.com"))
				.exchange()
				.expectStatus().isOk()
				.expectBody(ClientResponseDTO.class)
				.returnResult().getResponseBody();

		assertThat(responseBody).isNotNull();
		assertThat(responseBody.getName()).isEqualTo("Rodrigo Silva");
		assertThat(responseBody.getEmail()).isEqualTo("teste@example.com".toUpperCase());
	}

	@Test
	public void updateClientById_WithUniqueViolationEmail_ReturnsStatus400() {
		ErrorMessage responseBody = testClient
				.put()
				.uri("/api/v1/client/1")
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(new ClientCreateDTO("Rodrigo Silva", "carlos.santos@example.com"))
				.exchange()
				.expectStatus().isBadRequest()
				.expectBody(ErrorMessage.class)
				.returnResult().getResponseBody();

		assertThat(responseBody).isNotNull();
		assertThat(responseBody.getStatus()).isEqualTo(400);
	}

	@Test
	public void updateClientByEmail_WithValidData_ReturnsStatus200() {
		ClientResponseDTO responseBody = testClient
				.put()
				.uri("/api/v1/client/email/joao.silva@example.com")
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(new ClientCreateDTO("Rodrigo Silva", "teste@example.com"))
				.exchange()
				.expectStatus().isOk()
				.expectBody(ClientResponseDTO.class)
				.returnResult().getResponseBody();

		assertThat(responseBody).isNotNull();
		assertThat(responseBody.getName()).isEqualTo("Rodrigo Silva");
		assertThat(responseBody.getEmail()).isEqualTo("teste@example.com".toUpperCase());
	}

	@Test
	public void updateClientByEmail_WithUniqueViolationEmail_ReturnsStatus400() {
		ErrorMessage responseBody = testClient
				.put()
				.uri("/api/v1/client/email/joao.silva@example.com")
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(new ClientCreateDTO("Rodrigo Silva", "carlos.santos@example.com"))
				.exchange()
				.expectStatus().isBadRequest()
				.expectBody(ErrorMessage.class)
				.returnResult().getResponseBody();

		assertThat(responseBody).isNotNull();
		assertThat(responseBody.getStatus()).isEqualTo(400);
	}

	@Test
	public void deleteClientById_ReturnsStatus204() {
		Void responseBody = testClient
				.delete()
				.uri("/api/v1/client/1")
				.exchange()
				.expectStatus().isOk()
				.expectBody(Void.class)
				.returnResult().getResponseBody();

		assertThat(responseBody).isNull();
	}
}
