package com.cadastro.cliente.apicadastrocliente.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.cadastro.cliente.apicadastrocliente.dto.CadastroClienteDto;

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
@SequenceGenerator(name = "seq_cadastro_cliente", sequenceName = "seq_cadastro_cliente")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Cliente {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "seq_cadastro_cliente")
	Integer id;
	String nome;
	String email;
	String cpf;
	BigDecimal renda;
	String telefone;
	LocalDateTime dataCriacao;
	
	 public Cliente(CadastroClienteDto cadastroCliente) {
	        this.id = cadastroCliente.id();
	        this.nome = cadastroCliente.nome();
	        this.email = cadastroCliente.email();
	        this.cpf = cadastroCliente.cpf();
	        this.renda = cadastroCliente.renda();
	        this.telefone = cadastroCliente.telefone();
	        this.dataCriacao = cadastroCliente.dataCriacao();
	    }
}
