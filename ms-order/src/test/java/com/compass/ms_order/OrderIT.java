package com.compass.ms_order;

import com.compass.ms_order.exeptions.handler.ErrorMessage;
import com.compass.ms_order.web.controller.clients.StockClient;
import com.compass.ms_order.web.controller.clients.UserClient;
import com.compass.ms_order.web.dto.OrderCreateDTO;
import com.compass.ms_order.web.dto.OrderResponseDTO;
import com.compass.ms_order.web.dto.ProductCreateDTO;
import com.compass.ms_order.web.dto.ProductResponseDTO;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Sql(scripts = "/sql-Order/InsertOrder.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql-Order/DeleteOrder.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OrderIT {

	@Autowired
	WebTestClient testClient;

	@MockitoBean
	private UserClient userClient;

	@MockitoBean
	private StockClient stockClient;

	private OrderIT orderIT;

	String RESPONSE_CLIENT = "[{\n" +
			"    \"name\": \"Rodrigo\",\n" +
			"    \"email\": \"RODRIGO.SILVA@GMAIL.COM\"\n" +
			"}]";

	String NOTFOUND_EXCEPTION = "[{\n" +
			"    \"path\": \"/api/v1/client/email/TET@gmail.com\",\n" +
			"    \"method\": \"GET\",\n" +
			"    \"status\": 404,\n" +
			"    \"statusText\": \"Not Found\",\n" +
			"    \"message\": \"error: not found client by email TET@gmail.com\",\n" +
			"    \"errors\": null\n" +
			"}]";

	String RESPONSE_STOCK_NAME = "[{\n" +
			"    \"name\": \"Computador\",\n" +
			"    \"quantity\": \"5\"\n" +
			"}]";

	@Test
	public void createClient_WithValidData_ReturnsStatus201() {

		List<ProductCreateDTO> productList = List.of(new ProductCreateDTO( "Computador", 1));

		Mockito.when(userClient.consultEmailUser("RODRIGO.SILVA@GMAIL.COM")).thenReturn(ResponseEntity.ok(RESPONSE_CLIENT));
		Mockito.when(stockClient.findProductByName("Computador")).thenReturn(ResponseEntity.ok(RESPONSE_STOCK_NAME));

		OrderResponseDTO responseBody = testClient
				.post()
				.uri("/api/v1/order")
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(new OrderCreateDTO( "RODRIGO.SILVA@GMAIL.COM", productList))
				.exchange()
				.expectStatus().isCreated()
				.expectBody(OrderResponseDTO.class)
				.returnResult().getResponseBody();

		assertThat(responseBody).isNotNull();
		assertThat(responseBody.getClientEmail()).isEqualTo("RODRIGO.SILVA@GMAIL.COM");
		assertThat(responseBody.getProducts().getFirst().getName()).isEqualTo("Computador");
		assertThat(responseBody.getProducts().getFirst().getQuantity()).isEqualTo(1);
	}

	@Test
	public void updateClient_WithValidData_ReturnsStatus201() {

		List<ProductCreateDTO> productList = List.of(new ProductCreateDTO( "Computador", 1));

		Mockito.when(userClient.consultEmailUser("RODRIGO.SILVA@GMAIL.COM")).thenReturn(ResponseEntity.ok(RESPONSE_CLIENT));
		Mockito.when(stockClient.findProductByName("Computador")).thenReturn(ResponseEntity.ok(RESPONSE_STOCK_NAME));

		OrderResponseDTO responseBody = testClient
				.put()
				.uri("/api/v1/order/update/2")
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(new OrderCreateDTO( "RODRIGO.SILVA@GMAIL.COM", productList))
				.exchange()
				.expectStatus().isCreated()
				.expectBody(OrderResponseDTO.class)
				.returnResult().getResponseBody();

		assertThat(responseBody).isNotNull();
		assertThat(responseBody.getClientEmail()).isEqualTo("RODRIGO.SILVA@GMAIL.COM");
		assertThat(responseBody.getProducts().getFirst().getName()).isEqualTo("Computador");
		assertThat(responseBody.getProducts().getFirst().getQuantity()).isEqualTo(1);
	}

	@Test
	public void findOrderByEmail_WithValidData_ReturnsStatus200() {

		List<ProductCreateDTO> productList = List.of(new ProductCreateDTO( "Computador", 1));

		Mockito.when(userClient.consultEmailUser("cristiane@example.com")).thenReturn(ResponseEntity.ok(RESPONSE_CLIENT));

		List<OrderResponseDTO> responseBody = testClient
				.get()
				.uri("/api/v1/order/historic/cristiane@example.com")
				.exchange()
				.expectStatus().isOk()
				.expectBodyList(OrderResponseDTO.class)
				.returnResult().getResponseBody();

		assertThat(responseBody).isNotNull();
		assertThat(responseBody.getFirst().getClientEmail()).isEqualTo("cristiane@example.com");
		assertThat(responseBody.getFirst().getProducts().getFirst().getName()).isEqualTo("Smartphone");
		assertThat(responseBody.getFirst().getProducts().getFirst().getQuantity()).isEqualTo(10);
	}

	@Test
	public void findOrderByEmail_WithInvalidData_ReturnsStatus404() {

		List<ProductCreateDTO> productList = List.of(new ProductCreateDTO( "Computador", 1));

		Mockito.when(userClient.consultEmailUser("leandro@example.com")).thenReturn(ResponseEntity.ok(NOTFOUND_EXCEPTION));

		ErrorMessage responseBody = testClient
				.get()
				.uri("/api/v1/order/historic/leandro@example.com")
				.exchange()
				.expectStatus().isNotFound()
				.expectBody(ErrorMessage.class)
				.returnResult().getResponseBody();

		assertThat(responseBody).isNotNull();
		assertThat(responseBody.getStatus()).isEqualTo(404);
	}

	@Test
	public void findProductById_WithValidData_ReturnsStatus200() {

		ProductResponseDTO responseBody = testClient
				.get()
				.uri("/api/v1/order/product/1")
				.exchange()
				.expectStatus().isOk()
				.expectBody(ProductResponseDTO.class)
				.returnResult().getResponseBody();

		assertThat(responseBody).isNotNull();
		assertThat(responseBody.getName()).isEqualTo("Smartphone");
		assertThat(responseBody.getQuantity()).isEqualTo(10);

	}


}
