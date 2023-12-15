package com.cadastro.cliente.apicadastrocliente.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cadastro.cliente.apicadastrocliente.dto.AuthenticationDto;
import com.cadastro.cliente.apicadastrocliente.security.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
	
	private final AuthService authService;
	
	@SuppressWarnings("rawtypes")
	@PostMapping(value = "/login")
	public ResponseEntity login(@RequestBody @Valid AuthenticationDto data) {
		var token = authService.signin(data);
		if(token == null) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Solicitação de Cliente Inválido!");
		}
		return token;
	}
	
	@SuppressWarnings("rawtypes")
	@PutMapping(value = "/refresh/{login}")
	public ResponseEntity refreshToken(@PathVariable("login") String login, @RequestHeader("Authorization") String refreshToken) {
		var token = authService.refreshToken(login, refreshToken);
		if(token == null) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Solicitação de Cliente Inválido!");
		}
		return token;
	}
}