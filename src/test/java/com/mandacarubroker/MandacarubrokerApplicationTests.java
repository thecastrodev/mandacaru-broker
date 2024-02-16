package com.mandacarubroker;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
@AutoConfigureMockMvc
@SpringBootTest
class MandacarubrokerApplicationTests {

	@Autowired
	private MockMvc mock;

	@Test
	void shouldGetAllStocks() throws Exception {
		mock.perform(MockMvcRequestBuilders.get("/stocks"))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	void shouldCreateStock() throws Exception {
		mock.perform(MockMvcRequestBuilders.post("/stocks")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{ \"symbol\": \"BBAS3\", \"companyName\": \"Banco do Brasil SA\", \"price\":  56.97}"))
				.andExpect(MockMvcResultMatchers.status().isCreated());
	}

	@Test
	void shouldReturnSuccessfulCodeAfterGetAllStocks() throws Exception {
		mock.perform(MockMvcRequestBuilders.get("/stocks"))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	void shouldDeleteStockById() throws Exception {
		mock.perform(MockMvcRequestBuilders.delete("/stocks/{id}", "474a654d-0e9f-4b6c-b08c-68aafc07b857"))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}
}
