package com.mitocode.service;

import com.mitocode.document.Usuario;
import com.mitocode.security.User;

import reactor.core.publisher.Mono;

public interface IUsuarioService extends ICRUD<Usuario, String> {

	Mono<User> buscarPorUsuarioService(String usuario);
	
}
