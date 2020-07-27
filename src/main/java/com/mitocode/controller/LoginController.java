package com.mitocode.controller;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.mitocode.security.AuthRequest;
import com.mitocode.security.AuthResponse;
import com.mitocode.security.ErrorLogin;
import com.mitocode.security.JWTUtil;
import com.mitocode.service.IUsuarioService;

import reactor.core.publisher.Mono;

@RestController
public class LoginController {

	private static final Logger log = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	private JWTUtil jwtUtil;
	
	@Autowired
	private IUsuarioService service;
	
	@PostMapping("/login")
	public Mono<ResponseEntity<?>> loginController(@RequestBody AuthRequest ar) {
//		System.out.println("Entro a la f(x): loginController");
//		log.info(ar.getUsername());
//		log.info(ar.getPassword());
		return service.buscarPorUsuarioService(ar.getUsername()).map((userDetails) -> {
			String token = jwtUtil.generateToken(userDetails);
			Date expiracion = jwtUtil.getExpirationDateFromToken(token);
			
//			log.info(userDetails.getPassword());
			if (BCrypt.checkpw(ar.getPassword(), userDetails.getPassword())) {
				return ResponseEntity.ok(new AuthResponse(token, expiracion));
			} else {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
						.body(new ErrorLogin("Credenciales incorrectas.", new Date()));
			}
		}).defaultIfEmpty(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
	}
	
	@PostMapping("/v2/login")
	public Mono<ResponseEntity<?>> loginV2Controller(@RequestHeader("usuario") String usuario, 
			@RequestHeader("clave") String clave) {
		return service.buscarPorUsuarioService(usuario).map((userDetails) -> {
			String token = jwtUtil.generateToken(userDetails);
			Date expiracion = jwtUtil.getExpirationDateFromToken(token);
			
			if (BCrypt.checkpw(clave, userDetails.getPassword())) {
				return ResponseEntity.ok(new AuthResponse(token, expiracion));
			} else {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
						.body(new ErrorLogin("Credenciales incorrectas.", new Date()));
			}
		}).defaultIfEmpty(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
	}
	
}
