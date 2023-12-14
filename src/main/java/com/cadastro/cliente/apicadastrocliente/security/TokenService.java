package com.cadastro.cliente.apicadastrocliente.security;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.cadastro.cliente.apicadastrocliente.exception.InvalidJwtAuthenticationException;
import com.cadastro.cliente.apicadastrocliente.model.User;


@Service
public class TokenService {
	
	@Value("${api.security.token.secret}")
	private String secret;
	
	public String genereteToken(User user) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(secret);
			String token = JWT.create()
					.withIssuer("cadastro-cliente")
					.withSubject(user.getLogin())
					.withExpiresAt(expirationDate())
					.sign(algorithm);
			return token;
		} catch (JWTCreationException e) {
			throw new RuntimeException("Erro ao gerar token", e);
		}
	}
	
	public String validateToken(String token) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(secret);
			return JWT.require(algorithm)
					.withIssuer("cadastro-cliente")
					.build()
					.verify(token)
					.getSubject();
		} catch (JWTVerificationException e) {
			throw new InvalidJwtAuthenticationException("Token invalido!");
		}
	}
	
	private Date expirationDate() {
		return Date.from(LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00")));
	}
}
