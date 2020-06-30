package com.mitocode.repo;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.mitocode.document.Plato;

import reactor.core.publisher.Flux;

public interface IPlatoRepo extends ReactiveMongoRepository<Plato, String> {

	@Query("{ 'nombre': {$regex : ?0, '$options': 'i' }}")
	Flux<Plato> findByNombre(String nombre);
	
}
