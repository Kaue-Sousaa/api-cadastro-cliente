package com.cadastro.cliente.apicadastrocliente.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.cadastro.cliente.apicadastrocliente.dto.CadastroClienteDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cadastro_cliente", schema = "cliente")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CastroCliente {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "seq_cadastro_cliente")
	@SequenceGenerator(name = "seq_cadastro_cliente", sequenceName = "cliente.seq_cadastro_cliente", allocationSize = 1, initialValue = 1)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "nome", unique = true,nullable = false)
	private String nome;
	
	@Column(name = "email", unique = true,nullable = false)
	private String email;
	
	@Column(name = "cpf", unique = true,nullable = false)
	private String cpf;
	
	@Column(name = "renda", nullable = false)
	private BigDecimal renda;
	
	@Column(name = "telefone")
	private String telefone;
	
	@Column(name = "data_criacao",nullable = false)
	private LocalDateTime dataCriacao;
	
	 public CastroCliente(CadastroClienteDto cadastroCliente) {
	        this.id = cadastroCliente.id();
	        this.nome = cadastroCliente.nome();
	        this.email = cadastroCliente.email();
	        this.cpf = cadastroCliente.cpf();
	        this.renda = cadastroCliente.renda();
	        this.telefone = cadastroCliente.telefone();
	        this.dataCriacao = cadastroCliente.dataCriacao();
	    }
}
