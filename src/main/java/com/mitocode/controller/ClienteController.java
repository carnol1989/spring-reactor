package com.mitocode.controller;

import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.linkTo;
import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.methodOn;
import static reactor.function.TupleUtils.function;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Links;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mitocode.document.Cliente;
import com.mitocode.service.IClienteService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

	@Autowired
	private IClienteService service;
	
	/*@GetMapping
	public Flux<Cliente> listarController() {
		return service.listarService();
	}*/
	
	@GetMapping
	public Mono<ResponseEntity<Flux<Cliente>>> listarController() {
		Flux<Cliente> clientesFlux = service.listarService(); 
		return Mono.just(ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_STREAM_JSON)
				.body(clientesFlux));
	}
	
	/*@GetMapping("/{id}")
	public Mono<Cliente> listarPorIdController(@PathVariable("id") String id) {
		return service.listarPorIdService(id);
	}*/
	
	@GetMapping("/{id}")
	public Mono<ResponseEntity<Cliente>> listarPorIdController(@PathVariable("id") String id) {
		return service.listarPorIdService(id)
				.map(p -> ResponseEntity.ok()
							.contentType(MediaType.APPLICATION_STREAM_JSON)
							.body(p))
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}
	
	/*@PostMapping
	public Mono<Cliente> registrarController(@RequestBody Cliente cliente) {
		return service.registrarService(cliente);
	}*/
	
	@PostMapping
	public Mono<ResponseEntity<Cliente>> registrarController(@Valid @RequestBody Cliente cliente, final ServerHttpRequest req) {
		return service.registrarService(cliente)
				.map(p -> ResponseEntity.created(URI.create(req.getURI().toString()/*.concat("/")*/.concat(p.getId())))
						.contentType(MediaType.APPLICATION_STREAM_JSON)
						.body(p));
	}
	
	/*@PutMapping
	public Mono<Cliente> modificarController(@RequestBody Cliente cliente) {
		return service.modificarService(cliente);
	}*/
	
	@PutMapping
	public Mono<ResponseEntity<Cliente>> modificarController(@Valid @RequestBody Cliente cliente) {
		return service.modificarService(cliente)
				.map(p -> ResponseEntity.ok()
						.contentType(MediaType.APPLICATION_STREAM_JSON)
						.body(p))
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}
	
	/*@DeleteMapping("/{id}")
	public Mono<Void> eliminarController(@PathVariable("id") String id) {
		return service.eliminarService(id);
	}*/
	
	@DeleteMapping("/{id}")
	public Mono<ResponseEntity<Void>> eliminarController(@PathVariable("id") String id) {
		return service.listarPorIdService(id)
				.flatMap(p -> {
					return service.eliminarService(p.getId())
							.then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)));
				}).defaultIfEmpty(ResponseEntity.notFound().build());
	}
	
	//private Cliente clienteHateoas;//para hacer el 1er método
	//https://github.com/spring-projects/spring-hateoas/blob/a89e57eed7e12819b87be7f1b977dddfd432541d/src/test/java/org/springframework/hateoas/support/WebFluxEmployeeController.java#L101-L106
	@GetMapping("/hateoas/{id}")
	public Mono<EntityModel<Cliente>> listarHateoasPorIdController(@PathVariable("id") String id) {
		//clientes/listar/1
		Mono<Link> selfLink = linkTo(methodOn(ClienteController.class).listarHateoasPorIdController(id)).withSelfRel().toMono();
		Mono<Link> selfLink2 = linkTo(methodOn(ClienteController.class).listarHateoasPorIdController(id)).withSelfRel().toMono();
		
		//PRACTICA FACIL
		//1er método
		/*return service.listarPorIdService(id).flatMap(p -> {
			this.clienteHateoas = p;
			return selfLink;
		}).map(links -> {
			return new EntityModel<>(this.clienteHateoas, links);
		});*/
		
		//PRACTICA INTERMEDIO
		/*return service.listarPorIdService(id).flatMap(p -> {
			return selfLink.map(links -> new EntityModel<>(p, links));
		});*/
		
		//PRACTICA IDEAL | 1 link
		/*return service.listarPorIdService(id).zipWith(selfLink, (p, links) -> {
			return new EntityModel<>(p, links);
		});*/
		
		//PRACTICA IDEAL | 2+ link
		return selfLink.zipWith(selfLink2)
				.map(function((izq, der) -> Links.of(izq, der)))
				.zipWith(service.listarPorIdService(id), (links, p) -> {
					return new EntityModel<>(p, links);
				});
	}
	
}
