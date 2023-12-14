package com.cadastro.cliente.apicadastrocliente.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.cadastro.cliente.apicadastrocliente.model.CastroCliente;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public record CadastroClienteDto(
		
		Integer id,
		
		@NotBlank(message = "Campo obrigatorio")
		@Size(min = 1, max=255, message = "Campo só pode conter até 255 caracteres")
		String nome,
		
		@NotBlank(message = "Campo obrigatorio")
		@Size(min = 1, max=255, message = "Campo só pode conter até 255 caracteres")
		String email,
		
		@NotBlank(message = "Campo obrigatorio")
		@Size(min = 11, max=11, message="Campo tem que conter 11 caracteres")
		String cpf,
		
		@Digits(integer = 7, fraction = 2)
		BigDecimal renda,

		String telefone,
		
		@JsonFormat(pattern="dd/MM/yyyy HH:mm:ss")
		
		LocalDateTime dataCriacao
		
		) {
	
	public CadastroClienteDto(CastroCliente cliente) {
		this(cliente.getId(), cliente.getNome(), cliente.getEmail(), cliente.getCpf(), cliente.getRenda(),
				cliente.getTelefone(), cliente.getDataCriacao());
	}
}
