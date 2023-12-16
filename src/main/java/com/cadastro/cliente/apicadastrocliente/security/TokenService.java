package com.cadastro.cliente.apicadastrocliente.security;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.cadastro.cliente.apicadastrocliente.dto.TokenDto;
import com.cadastro.cliente.apicadastrocliente.exception.InvalidJwtAuthenticationException;

@Service
public class TokenService {
	
	@Value("${api.security.token.secret}")
	private String secret;
	
	private long validityInMilliseconds = 3600000; //1h
	
	private final UserDetailsService userDetailsService;
	
	Algorithm algorithm = null;
	
	public TokenService(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	public TokenDto createAccessToken(String email, List<String> roles) {
		Date now = new Date();
		Date validity = new Date(now.getTime() + validityInMilliseconds);
		return new TokenDto(
				getAccessToken(email, roles, now, validity),
				getRefreshToken(email, roles,  now));
	}

	public TokenDto refreshToken(String refreshToken) {
		if(refreshToken.contains("Bearer ")) {
			refreshToken = refreshToken.substring("Bearer ".length());
		}
		algorithm = Algorithm.HMAC256(secret);
		JWTVerifier verifier = JWT.require(algorithm).build();
		DecodedJWT decodedJWT = verifier.verify(refreshToken);
		String email = decodedJWT.getSubject();
		List<String> roles = decodedJWT.getClaim("roles").asList(String.class);
		return createAccessToken(email, roles);
	}
	
	private String getAccessToken(String email, List<String> roles, Date now, Date validity) {
		String issuerUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
		algorithm = Algorithm.HMAC256(secret);
		return JWT.create()
				.withClaim("roles", roles)
				.withIssuedAt(now)
				.withExpiresAt(validity)
				.withSubject(email)
				.withIssuer(issuerUrl)
				.sign(algorithm)
				.strip();
	}
	
	private String getRefreshToken(String email, List<String> roles, Date now) {
		Date validityRefreshToken = new Date(now.getTime() + (validityInMilliseconds * 3));
		algorithm = Algorithm.HMAC256(secret);
		return JWT.create()
				.withClaim("roles", roles)
				.withIssuedAt(now)
				.withExpiresAt(validityRefreshToken)
				.withSubject(email)
				.sign(algorithm)
				.strip();
	}
	
	public boolean validateToken(String token) {
		try {
			DecodedJWT decodedJWT = decodedToken(token);
			if(decodedJWT.getExpiresAt().before(new Date())) {
				return false;
			}
			return true;
		} catch (Exception e) {
			throw new InvalidJwtAuthenticationException("Token invalido!");
		}
	}
	
	private DecodedJWT decodedToken(String token) {
		Algorithm alg = Algorithm.HMAC256(secret.getBytes());
		JWTVerifier verifier = JWT.require(alg).build();
		DecodedJWT decodedJWT = verifier.verify(token);
		return decodedJWT;
	}
	
	public Authentication getAuthentication(String token) {
		try {
			DecodedJWT decodedJWT = decodedToken(token);
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(decodedJWT.getSubject());
			return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
		} catch (Exception e) {
			throw new BadCredentialsException("Usuário inexistente ou senha inválida!");
		}
	}
}
