package com.mitocode;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RouterFunctions.route; 

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.mitocode.handler.ClienteHandler;
import com.mitocode.handler.PlatoHandler;

//Functional Endpoints
@Configuration
public class RouterConfig {

	@Bean
	public RouterFunction<ServerResponse> rutasPlatos(PlatoHandler platoHandler) {
		return route(GET("/v2/platos"), platoHandler::listarHandler)
				.andRoute(GET("/v2/platos/{id}"), platoHandler::listarPorIdHandler)
				.andRoute(POST("/v2/platos"), platoHandler::registrarHandler)
				.andRoute(PUT("/v2/platos"), platoHandler::modificarHandler)
				.andRoute(DELETE("/v2/platos/{id}"), platoHandler::eliminarHandler);
	}
	
	@Bean
	public RouterFunction<ServerResponse> rutasClientes(ClienteHandler clienteHandler) {
		return route(GET("/v2/clientes"), clienteHandler::listarHandler)
				.andRoute(GET("/v2/clientes/{id}"), clienteHandler::listarPorIdHandler)
				.andRoute(POST("/v2/clientes"), clienteHandler::registrarHandler)
				.andRoute(PUT("/v2/clientes"), clienteHandler::modificarHandler)
				.andRoute(DELETE("/v2/clientes/{id}"), clienteHandler::eliminarHandler);
	}
	
}
