package com.mitocode.repo;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.mitocode.document.Usuario;

import reactor.core.publisher.Mono;

public interface IUsuarioRepo extends ReactiveMongoRepository<Usuario, String> {

	Mono<Usuario> findOneByUsuario(String usuario);
	
}
