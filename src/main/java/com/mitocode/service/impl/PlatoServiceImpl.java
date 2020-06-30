package com.mitocode.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mitocode.document.Plato;
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
	
}
