package com.compass.ms_order;

import com.compass.ms_order.entities.Order;
import com.compass.ms_order.entities.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Sql(scripts = "/sql-Client/InsertOrder.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql-Client/DeleteOrder.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@SpringBootTest
class OrderIT {

	@Autowired
	WebTestClient testClient;

	@Test
	public void createClient_WithValidData_ReturnsStatus201() {
		List<Product> productList = List.of(new Product(null, null, "Computador", 5));
		Order responseBody = testClient
				.post()
				.uri("/api/v1/order")
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(new Order(null, "Rodrigo Silva", productList))
				.exchange()
				.expectStatus().isCreated()
				.expectBody(Order.class)
				.returnResult().getResponseBody();

		assertThat(responseBody).isNotNull();
	}

}
