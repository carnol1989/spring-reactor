package com.mitocode.service;

import com.mitocode.document.Plato;

import reactor.core.publisher.Flux;

public interface IPlatoService extends ICRUD<Plato, String> {

	Flux<Plato> buscarPorNombreService(String nombre);
	
}
