package com.cadastro.cliente.apicadastrocliente.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cadastro.cliente.apicadastrocliente.dto.AuthenticationDto;
import com.cadastro.cliente.apicadastrocliente.dto.LoginResponseDto;
import com.cadastro.cliente.apicadastrocliente.dto.RegisterDto;
import com.cadastro.cliente.apicadastrocliente.model.User;
import com.cadastro.cliente.apicadastrocliente.repository.UserRepository;
import com.cadastro.cliente.apicadastrocliente.security.TokenService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
	
	private final AuthenticationManager authenticationManager;
	
	private final UserRepository repository;
	
	private final TokenService tokenService;

	@PostMapping(value = "/login")
	public ResponseEntity<LoginResponseDto> login(@RequestBody @Valid AuthenticationDto data) {
		var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
		var auth = authenticationManager.authenticate(usernamePassword);
		
		var token = tokenService.genereteToken((User) auth.getPrincipal());
		
		return ResponseEntity.ok(new LoginResponseDto(token));
	}
	
	@PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity register(@RequestBody @Valid RegisterDto data){
		if(repository.findByLogin(data.login()) != null) {
			return ResponseEntity.badRequest().build();
		}
		String encryPassword = new BCryptPasswordEncoder().encode(data.password());
		User newUser = new User(data.login(), encryPassword, data.role());
		
		repository.save(newUser);
		return ResponseEntity.ok().build();
	}
}