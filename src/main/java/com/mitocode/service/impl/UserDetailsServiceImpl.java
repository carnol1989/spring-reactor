package com.mitocode.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.mitocode.document.Usuario;
import com.mitocode.repo.IUsuarioRepo;

import reactor.core.publisher.Mono;

@Service
public class UserDetailsServiceImpl { //implements ReactiveUserDetailsService {

	private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
	
	@Autowired
	private IUsuarioRepo repo;
	
//	@Override
//	public Mono<UserDetails> findByUsername(String username) {
//		Mono<Usuario> usuarioMono = repo.findOneByUsuario(username);
//		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
//		return usuarioMono.doOnNext(u -> {
//			u.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getNombre())));
//		}).flatMap(u -> {
////			return Mono.just(new User(u.getUsuario(), u.getClave(), u.getEstado(), u.getAccountNonExpired(), 
////					u.getCredentialsNonExpired(), u.getAccountNonLocked(), authorities));
//			return Mono.just(new User(u.getUsuario(), u.getClave(), u.getEstado(), true, 
//					true, true, authorities));
//		});
//	}

}
