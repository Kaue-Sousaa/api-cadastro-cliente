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
import com.cadastro.cliente.apicadastrocliente.repository.CadastroClienteRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class AuthService {
	
	private final AuthenticationManager authenticationManager;
	
	private final CadastroClienteRepository repository;
	
	private final TokenService tokenService;
	
	@SuppressWarnings("rawtypes")
	public ResponseEntity signin(AuthenticationDto data) {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(data.email(), data.senha()));
			var cliente = repository.findByEmail(data.email());
			if (cliente != null) {
				var tokenResponse = tokenService.createAccessToken(data.email(), List.of(cliente.getRole()));
				return ResponseEntity.ok(tokenResponse);
			}else {
				throw new UsernameNotFoundException("Email " + data.email() + " não encontrado!");
			}
		} catch (Exception e) {
			throw new BadCredentialsException("Usuário inexistente ou senha inválida!");
		}
	}
	
	@SuppressWarnings("rawtypes")
	public ResponseEntity refreshToken(String email, String refreshToken) {
		var user = repository.findByEmail(email);
		TokenDto tokenResponse = null;
		if (user != null) {
			tokenResponse = tokenService.refreshToken(refreshToken);
		}else {
			throw new UsernameNotFoundException("Usuário " + email + " não encontrado!");
		}
		return ResponseEntity.ok(tokenResponse);
	}
}