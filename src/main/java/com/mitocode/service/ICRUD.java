package com.mitocode.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICRUD<T, V> {

	Mono<T> registrarService(T t);
	
	Mono<T> modificarService(T t);
	
	Flux<T> listarService();
	
	Mono<T> listarPorIdService(V v);
	
	Mono<Void> eliminarService(V v);
	
}
