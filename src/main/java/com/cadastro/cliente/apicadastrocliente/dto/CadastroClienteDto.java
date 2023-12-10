package com.cadastro.cliente.apicadastrocliente.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.cadastro.cliente.apicadastrocliente.model.Cliente;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public record CadastroClienteDto(
		
		@NotBlank(message = "Campo Id obrigatorio")
		Integer id,
		
		@NotBlank(message = "Campo nome obrigatorio")
		@Size(min = 1, max=255, message = "Campo só pode conter até 255 caracteres")
		String nome,
		
		@NotBlank(message = "Campo email obrigatorio")
		@Size(min = 1, max=255, message = "Campo só pode conter até 255 caracteres")
		String email,
		
		@NotBlank(message = "Campo cpf obrigatorio")
		@Size(min = 11, max=11)
		String cpf,
		
		@NotBlank(message = "Campo renda obrigatorio")
		BigDecimal renda,

		String telefone,
		
		@JsonFormat(pattern="dd/MM/yyyy HH:mm:ss")
		LocalDateTime dataCriacao
		
		) {
	
	public CadastroClienteDto(Cliente cliente) {
		this(cliente.getId(), cliente.getNome(), cliente.getEmail(), cliente.getCpf(), cliente.getRenda(),
				cliente.getTelefone(), cliente.getDataCriacao());
	}
}
