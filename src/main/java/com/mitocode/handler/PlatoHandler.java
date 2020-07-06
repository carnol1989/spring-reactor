package com.mitocode.handler;

import static org.springframework.web.reactive.function.BodyInserters.fromValue;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.mitocode.document.Plato;
import com.mitocode.service.IPlatoService;

import reactor.core.publisher.Mono;

@Component
public class PlatoHandler {

	@Autowired
	private IPlatoService service;
	
	public Mono<ServerResponse> listarHandler(ServerRequest req) {
		return ServerResponse.ok()
				.contentType(MediaType.APPLICATION_STREAM_JSON)
				.body(service.listarService(), Plato.class);
	}
	
	public Mono<ServerResponse> listarPorIdHandler(ServerRequest req) {
		String id = req.pathVariable("id");
		return service.listarPorIdService(id)
//				.onErrorMap(error -> new ArithmeticException("Ups"))
				.flatMap(p -> ServerResponse.ok()
						.contentType(MediaType.APPLICATION_STREAM_JSON)
						.body(fromValue(p)))
				.switchIfEmpty(ServerResponse
						.notFound()
						.build());
	}
	
	public Mono<ServerResponse> registrarHandler(ServerRequest req) {
		Mono<Plato> platoMono = req.bodyToMono(Plato.class);
		return platoMono.flatMap(p -> {
			return service.registrarService(p);
		}).flatMap(p -> ServerResponse.created(URI.create(req.uri().toString().concat("/").concat(p.getId())))
				.contentType(MediaType.APPLICATION_STREAM_JSON)
				.body(fromValue(p)));
	}
	
	public Mono<ServerResponse> modificarHandler(ServerRequest req) {
		Mono<Plato> platoMono = req.bodyToMono(Plato.class);
		return platoMono.flatMap(p -> {
			return service.modificarService(p);
		}).flatMap(p -> ServerResponse.ok()
				.contentType(MediaType.APPLICATION_STREAM_JSON)
				.body(fromValue(p))
		).switchIfEmpty(ServerResponse
						.notFound()
						.build());
	}
	
	public Mono<ServerResponse> eliminarHandler(ServerRequest req) {
		String id = req.pathVariable("id");
		return service.listarPorIdService(id)
				.flatMap(p -> service.eliminarService(p.getId())
						.then(ServerResponse
								.noContent()
								.build()))
				.switchIfEmpty(ServerResponse
						.notFound()
						.build());
	}
	
}
