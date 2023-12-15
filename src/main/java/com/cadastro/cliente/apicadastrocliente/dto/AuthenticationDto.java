package com.cadastro.cliente.apicadastrocliente.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthenticationDto(
		@NotBlank
		String login,
		
		@NotBlank
		String password) {

}
