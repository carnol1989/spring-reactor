package com.mitocode.filters;

import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import reactor.core.publisher.Mono;

@Component
public class EjemploFilter implements WebFilter {

	@Override
	public Mono<Void> filter(ServerWebExchange serverWebExchange, WebFilterChain webFilterChain) {
		//logica prog.
		serverWebExchange.getResponse().getHeaders().add("filtro1", "contenido-filtro1");
		return webFilterChain.filter(serverWebExchange);
	}

}
