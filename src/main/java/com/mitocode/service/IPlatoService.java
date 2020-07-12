package com.mitocode.service;

import org.springframework.data.domain.Pageable;

import com.mitocode.document.Plato;
import com.mitocode.pagination.PageSupport;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IPlatoService extends ICRUD<Plato, String> {

	Flux<Plato> buscarPorNombreService(String nombre);
	
	Mono<PageSupport<Plato>> listarPageService(Pageable page);
	
}
