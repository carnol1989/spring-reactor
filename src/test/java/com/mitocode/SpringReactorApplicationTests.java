package com.mitocode;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.mitocode.document.Plato;

import reactor.core.publisher.Mono;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class SpringReactorApplicationTests {

	@Autowired
	private WebTestClient clienteWeb;
	
	@Test
	void listarTest() {
		clienteWeb.get()
		.uri("/platos")
		.accept(MediaType.APPLICATION_STREAM_JSON)
		.exchange()
		.expectStatus().isOk()
		.expectHeader().contentType(MediaType.APPLICATION_STREAM_JSON)
		.expectBodyList(Plato.class)
		.hasSize(8);
	}
	
	@Test
	void registrarTest() {
		Plato plato = new Plato();
		plato.setNombre("PACHAMANCA");
//		plato.setNombre("");
		plato.setPrecio(20);
		
		clienteWeb.post()
		.uri("/platos")
		.accept(MediaType.APPLICATION_STREAM_JSON)
		.body(Mono.just(plato), Plato.class)
		.exchange()
		.expectStatus().isCreated()
		.expectHeader().contentType(MediaType.APPLICATION_STREAM_JSON)
		.expectBody()
		.jsonPath("$.nombre").isNotEmpty()
		.jsonPath("$.precio").isNumber();
	}
	
	@Test
	void modificarTest() {
		Plato plato = new Plato();
		plato.setId("5f0bd5251aadd64e38106a07");
		plato.setNombre("CEVICHE MIXTO");
		plato.setPrecio(15);
		
		clienteWeb.put()
		.uri("/platos")
		.accept(MediaType.APPLICATION_STREAM_JSON)
		.body(Mono.just(plato), Plato.class)
		.exchange()
		.expectStatus().isOk()
		.expectHeader().contentType(MediaType.APPLICATION_STREAM_JSON)
		.expectBody()
		.jsonPath("$.id").isNotEmpty()
		.jsonPath("$.nombre").isNotEmpty()
		.jsonPath("$.precio").isNumber();
	}
	
	@Test
	void eliminarTest() {
		Plato plato = new Plato();
		plato.setId("5f0bd7d3c79bb871d1e33c0a");
		
		clienteWeb.delete()
		.uri("/platos/" + plato.getId())
		.accept(MediaType.APPLICATION_STREAM_JSON)
		.exchange()
		.expectStatus().isNoContent();
	}

}
