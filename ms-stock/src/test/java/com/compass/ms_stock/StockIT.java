package com.compass.ms_stock;

import com.compass.ms_stock.exceptions.handler.ErrorMessage;
import com.compass.ms_stock.web.controller.dto.ProductCreateDTO;
import com.compass.ms_stock.web.controller.dto.ProductResponseDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Sql(scripts = "/sql-Stock/InsertProduct.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql-Stock/DeleteProduct.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StockIT {

	@Autowired
	WebTestClient testClient;

	@Test
	public void createProduct_WithValidData_ReturnsStatus201() {
		ProductResponseDTO responseBody = testClient
				.post()
				.uri("/api/v1/stock")
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(new ProductCreateDTO("Calculator", 2))
				.exchange()
				.expectStatus().isCreated()
				.expectBody(ProductResponseDTO.class)
				.returnResult().getResponseBody();

		assertThat(responseBody).isNotNull();
		assertThat(responseBody.getName()).isNotNull();
		assertThat(responseBody.getName()).isEqualTo("Calculator");
		assertThat(responseBody.getQuantity()).isNotNull();
		assertThat(responseBody.getQuantity()).isEqualTo(2);
	}

	@Test
	public void createProduct_WithInvalidQuantity_ReturnsStatus422() {
		ErrorMessage responseBody = testClient
				.post()
				.uri("/api/v1/stock")
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(new ProductCreateDTO("Rodrigo Silva", null))
				.exchange()
				.expectStatus().isEqualTo(422)
				.expectBody(ErrorMessage.class)
				.returnResult().getResponseBody();

		assertThat(responseBody).isNotNull();
		assertThat(responseBody.getStatus()).isEqualTo(422);
	}

	@Test
	public void createProduct_WithInvalidName_ReturnsStatus422() {
		ErrorMessage responseBody = testClient
				.post()
				.uri("/api/v1/stock")
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(new ProductCreateDTO("", 2))
				.exchange()
				.expectStatus().isEqualTo(422)
				.expectBody(ErrorMessage.class)
				.returnResult().getResponseBody();

		assertThat(responseBody).isNotNull();
		assertThat(responseBody.getStatus()).isEqualTo(422);
	}

	@Test
	public void findAllProducts_WithClientsOnDB_ReturnsStatus200() {
		List<ProductResponseDTO> responseBody = testClient
				.get()
				.uri("/api/v1/stock")
				.exchange()
				.expectStatus().isOk()
				.expectBodyList(ProductResponseDTO.class)
				.returnResult().getResponseBody();

		assertThat(responseBody).isNotNull();
		assertThat(responseBody).isNotEmpty();
	}

	@Test
	@Sql(scripts = "/sql-Stock/DeleteProduct.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	public void findAllProducts_WithInvalidClientsOnDB_ReturnsStatus422() {
		ErrorMessage responseBody = testClient
				.get()
				.uri("/api/v1/stock")
				.exchange()
				.expectStatus().isNotFound()
				.expectBody(ErrorMessage.class)
				.returnResult().getResponseBody();

		assertThat(responseBody).isNotNull();
		assertThat(responseBody.getStatus()).isEqualTo(404);
	}

	@Test
	public void findProductById_WithValidId_ReturnsStatus200() {
		ProductResponseDTO responseBody = testClient
				.get()
				.uri("/api/v1/stock/product/id/1")
				.exchange()
				.expectStatus().isOk()
				.expectBody(ProductResponseDTO.class)
				.returnResult().getResponseBody();

		assertThat(responseBody).isNotNull();
		assertThat(responseBody.getName()).isEqualTo("Computer");
		assertThat(responseBody.getQuantity()).isEqualTo(10);
	}

	@Test
	public void findStockById_WithInvalidId_ReturnsStatus404() {
		ErrorMessage responseBody = testClient
				.get()
				.uri("/api/v1/stock/product/id/100")
				.exchange()
				.expectStatus().isNotFound()
				.expectBody(ErrorMessage.class)
				.returnResult().getResponseBody();

		assertThat(responseBody).isNotNull();
		assertThat(responseBody.getStatus()).isEqualTo(404);
	}

	@Test
	public void findProductByName_WithValidEmail_ReturnsStatus200() {
		ProductResponseDTO responseBody = testClient
				.get()
				.uri("/api/v1/stock/product/name/Computer")
				.exchange()
				.expectStatus().isOk()
				.expectBody(ProductResponseDTO.class)
				.returnResult().getResponseBody();

		assertThat(responseBody).isNotNull();
		assertThat(responseBody.getName()).isEqualTo("Computer");
	}

	@Test
	public void findProductByEmail_WithInvalidProduct_ReturnsStatus404() {
		ErrorMessage responseBody = testClient
				.get()
				.uri("/api/v1/stock/product/name/Mouse")
				.exchange()
				.expectStatus().isNotFound()
				.expectBody(ErrorMessage.class)
				.returnResult().getResponseBody();

		assertThat(responseBody).isNotNull();
		assertThat(responseBody.getStatus()).isEqualTo(404);
	}

	@Test
	public void updateProductById_WithValidData_ReturnsStatus200() {
		ProductResponseDTO responseBody = testClient
				.put()
				.uri("/api/v1/stock/1")
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(new ProductCreateDTO("Mouse", 10))
				.exchange()
				.expectStatus().isCreated()
				.expectBody(ProductResponseDTO.class)
				.returnResult().getResponseBody();

		assertThat(responseBody).isNotNull();
		assertThat(responseBody.getName()).isEqualTo("Mouse");
		assertThat(responseBody.getQuantity()).isEqualTo(10);
	}

	@Test
	public void removeQuantityByName_WithValidData_ReturnsStatus200() {
		ProductResponseDTO responseBody = testClient
				.get()
				.uri("/api/v1/stock/quantity/remove/2/Tablet")
				.exchange()
				.expectStatus().isOk()
				.expectBody(ProductResponseDTO.class)
				.returnResult().getResponseBody();

		assertThat(responseBody).isNotNull();
		assertThat(responseBody.getName()).isEqualTo("Tablet");
		assertThat(responseBody.getQuantity()).isEqualTo(0);
	}

	@Test
	public void removeQuantityByName_WithBlowZero_ReturnsStatus200() {
		ErrorMessage responseBody = testClient
				.get()
				.uri("/api/v1/stock/quantity/remove/100/Tablet")
				.exchange()
				.expectStatus().isBadRequest()
				.expectBody(ErrorMessage.class)
				.returnResult().getResponseBody();

		assertThat(responseBody).isNotNull();
		assertThat(responseBody.getStatus()).isEqualTo(400);
	}

	@Test
	public void addQuantityByName_WithValidData_ReturnsStatus200() {
		ProductResponseDTO responseBody = testClient
				.get()
				.uri("/api/v1/stock/quantity/add/2/Tablet")
				.exchange()
				.expectStatus().isOk()
				.expectBody(ProductResponseDTO.class)
				.returnResult().getResponseBody();

		assertThat(responseBody).isNotNull();
		assertThat(responseBody.getName()).isEqualTo("Tablet");
		assertThat(responseBody.getQuantity()).isEqualTo(4);
	}

}
