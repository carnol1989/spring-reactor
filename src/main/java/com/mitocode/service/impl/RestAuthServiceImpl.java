package com.mitocode.service.impl;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Service;

@Service
public class RestAuthServiceImpl {

	public boolean tieneAcceso(String path) {
		boolean rpta = false;

		String metodoRol = "";
		// /listar
		switch (path) {
		case "listar":
			metodoRol = "ADMIN";
			break;
		case "listarId":
			metodoRol = "ADMIN,USER,DBA";
			break;
		}

		String metodoRoles[] = metodoRol.split(",");
		// https://github.com/spring-projects/spring-security/issues/5207
		// De momento esta característica tiene bugs la cual permitiría obtener
		// información del usuario logueado
		SecurityContext context = ReactiveSecurityContextHolder.getContext().block();

		System.out.println(context.getAuthentication().getName());
		for (GrantedAuthority auth : context.getAuthentication().getAuthorities()) {
			String rolUser = auth.getAuthority();
			System.out.println(rolUser);
			for (String rolMet : metodoRoles) {
				if (rolUser.equalsIgnoreCase(rolMet)) {
					rpta = true;
				}
			}
		}

		return rpta;
	}

}
