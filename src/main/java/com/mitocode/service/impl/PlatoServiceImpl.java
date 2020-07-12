package com.mitocode.service.impl;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mitocode.document.Plato;
import com.mitocode.pagination.PageSupport;
import com.mitocode.repo.IPlatoRepo;
import com.mitocode.service.IPlatoService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PlatoServiceImpl implements IPlatoService {

	@Autowired
	private IPlatoRepo repo;
	
	@Override
	public Mono<Plato> registrarService(Plato t) {
		return repo.save(t);
	}

	@Override
	public Mono<Plato> modificarService(Plato t) {
		return repo.save(t);
	}

	@Override
	public Flux<Plato> listarService() {
		return repo.findAll();
	}

	@Override
	public Mono<Plato> listarPorIdService(String v) {
		return repo.findById(v);
	}

	@Override
	public Mono<Void> eliminarService(String v) {
		return repo.deleteById(v);
	}

	@Override
	public Flux<Plato> buscarPorNombreService(String nombre) {
		//SELECT * FROM PLATO p WHERE p.nombre = ?
		return repo.findByNombre(nombre);
	}

	@Override
	public Mono<PageSupport<Plato>> listarPageService(Pageable page) {
		return repo.findAll()
				.collectList()
				.map(list -> new PageSupport<>(
						list.stream()
						.skip(page.getPageNumber() * page.getPageSize())
						.limit(page.getPageSize())
						.collect(Collectors.toList()), 
						page.getPageNumber(), page.getPageSize(), list.size()));
	}
	
}
