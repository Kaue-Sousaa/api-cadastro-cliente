package com.cadastro.cliente.apicadastrocliente.dto;

public record TokenDto(
		 String userName,
		 String accessToken,
		 String refreshToken
		) {

}
