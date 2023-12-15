package com.cadastro.cliente.apicadastrocliente.security;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.cadastro.cliente.apicadastrocliente.dto.AuthenticationDto;
import com.cadastro.cliente.apicadastrocliente.dto.TokenDto;
import com.cadastro.cliente.apicadastrocliente.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class AuthService {
	
	private final AuthenticationManager authenticationManager;
	
	private final UserRepository repository;
	
	private final TokenService tokenService;
	
	@SuppressWarnings("rawtypes")
	public ResponseEntity signin(AuthenticationDto data) {
		TokenDto tokenResponse = null;
		try {
			var login = data.login();
			var password = data.password();
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login, password));
			var user = repository.findByLogin(login);
			if (user != null) {
				tokenResponse = tokenService.createAccessToken(login, List.of(user.getRole().name()));
			}else {
				throw new UsernameNotFoundException("Usuário " + login + " não encontrado!");
			}
			return ResponseEntity.ok(tokenResponse);
		} catch (Exception e) {
			throw new BadCredentialsException("Usuário ou senha incorretos!");
		}
	}
	
	@SuppressWarnings("rawtypes")
	public ResponseEntity refreshToken(String login, String refreshToken) {
		var user = repository.findByLogin(login);
		TokenDto tokenResponse = null;
		if (user != null) {
			tokenResponse = tokenService.refreshToken(refreshToken);
		}else {
			throw new UsernameNotFoundException("Usuário " + login + " não encontrado!");
		}
		return ResponseEntity.ok(tokenResponse);
	}
}