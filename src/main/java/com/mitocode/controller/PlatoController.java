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

import com.mitocode.document.Plato;
import com.mitocode.service.IPlatoService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/platos")
public class PlatoController {

	@Autowired
	private IPlatoService service;
	
	/*@GetMapping
	public Flux<Plato> listarController() {
		return service.listarService();
	}*/
	
	@GetMapping
	public Mono<ResponseEntity<Flux<Plato>>> listarController() {
		Flux<Plato> platosFlux = service.listarService(); 
		return Mono.just(ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_STREAM_JSON)
				.body(platosFlux));
	}
	
	/*@GetMapping("/{id}")
	public Mono<Plato> listarPorIdController(@PathVariable("id") String id) {
		return service.listarPorIdService(id);
	}*/
	
	@GetMapping("/{id}")
	public Mono<ResponseEntity<Plato>> listarPorIdController(@PathVariable("id") String id) {
		return service.listarPorIdService(id)
				.map(p -> ResponseEntity.ok()
							.contentType(MediaType.APPLICATION_STREAM_JSON)
							.body(p))
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}
	
	/*@PostMapping
	public Mono<Plato> registrarController(@RequestBody Plato plato) {
		return service.registrarService(plato);
	}*/
	
	@PostMapping
	public Mono<ResponseEntity<Plato>> registrarController(@Valid @RequestBody Plato plato, final ServerHttpRequest req) {
		return service.registrarService(plato)
				.map(p -> ResponseEntity.created(URI.create(req.getURI().toString()/*.concat("/")*/.concat(p.getId())))
						.contentType(MediaType.APPLICATION_STREAM_JSON)
						.build());
	}
	
	/*@PutMapping
	public Mono<Plato> modificarController(@RequestBody Plato plato) {
		return service.modificarService(plato);
	}*/
	
	@PutMapping
	public Mono<ResponseEntity<Plato>> modificarController(@Valid @RequestBody Plato plato) {
		return service.modificarService(plato)
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
	
	//private Plato platoHateoas;//para hacer el 1er método
	//https://github.com/spring-projects/spring-hateoas/blob/a89e57eed7e12819b87be7f1b977dddfd432541d/src/test/java/org/springframework/hateoas/support/WebFluxEmployeeController.java#L101-L106
	@GetMapping("/hateoas/{id}")
	public Mono<EntityModel<Plato>> listarHateoasPorIdController(@PathVariable("id") String id) {
		//platos/listar/1
		Mono<Link> selfLink = linkTo(methodOn(PlatoController.class).listarHateoasPorIdController(id)).withSelfRel().toMono();
		Mono<Link> selfLink2 = linkTo(methodOn(PlatoController.class).listarHateoasPorIdController(id)).withSelfRel().toMono();
		
		//PRACTICA FACIL
		//1er método
		/*return service.listarPorIdService(id).flatMap(p -> {
			this.platoHateoas = p;
			return selfLink;
		}).map(links -> {
			return new EntityModel<>(this.platoHateoas, links);
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
