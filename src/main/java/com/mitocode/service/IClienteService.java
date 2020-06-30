package com.mitocode.service;

import org.springframework.data.domain.Pageable;

import com.mitocode.document.Cliente;
import com.mitocode.pagination.PageSupport;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IClienteService extends ICRUD<Cliente, String> {

	Flux<Cliente> listarDemoradoService();
	
	Flux<Cliente> listarSobreCargadoService();
	
	Mono<PageSupport<Cliente>> listarPaginaService(Pageable page);
	
}
