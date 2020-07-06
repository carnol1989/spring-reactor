package com.mitocode.handler;

import static org.springframework.web.reactive.function.BodyInserters.fromValue;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.mitocode.document.Cliente;
import com.mitocode.service.IClienteService;

import reactor.core.publisher.Mono;

@Component
public class ClienteHandler {

	@Autowired
	private IClienteService service;
	
	public Mono<ServerResponse> listarHandler(ServerRequest req) {
		return ServerResponse.ok()
				.contentType(MediaType.APPLICATION_STREAM_JSON)
				.body(service.listarService(), Cliente.class);
	}
	
	public Mono<ServerResponse> listarPorIdHandler(ServerRequest req) {
		String id = req.pathVariable("id");
		return service.listarPorIdService(id)
				.flatMap(p -> ServerResponse.ok()
						.contentType(MediaType.APPLICATION_STREAM_JSON)
						.body(fromValue(p)))
				.switchIfEmpty(ServerResponse
						.notFound()
						.build());
	}
	
	public Mono<ServerResponse> registrarHandler(ServerRequest req) {
		Mono<Cliente> ClienteMono = req.bodyToMono(Cliente.class);
		return ClienteMono.flatMap(p -> {
			return service.registrarService(p);
		}).flatMap(p -> ServerResponse.created(URI.create(req.uri().toString().concat("/").concat(p.getId())))
				.contentType(MediaType.APPLICATION_STREAM_JSON)
				.body(fromValue(p)));
	}
	
	public Mono<ServerResponse> modificarHandler(ServerRequest req) {
		Mono<Cliente> ClienteMono = req.bodyToMono(Cliente.class);
		return ClienteMono.flatMap(p -> {
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
