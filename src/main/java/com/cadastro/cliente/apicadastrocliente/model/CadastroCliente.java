package com.cadastro.cliente.apicadastrocliente.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cadastro.cliente.apicadastrocliente.dto.CadastroClienteDto;
import com.cadastro.cliente.apicadastrocliente.enums.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cadastro_cliente", schema = "cliente")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CadastroCliente implements UserDetails{
	private static final long serialVersionUID = 1L;

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
	
	@Column(name = "senha",nullable = false)
	private String senha;
	
	@Column(name = "role", nullable = false)
	private String role;
	
	@JsonIgnoreProperties
	@Transient
	private String confirmSenha;
	
	 public CadastroCliente(CadastroClienteDto cadastroCliente) {
	        this.id = cadastroCliente.id();
	        this.nome = cadastroCliente.nome();
	        this.email = cadastroCliente.email();
	        this.cpf = cadastroCliente.cpf();
	        this.renda = cadastroCliente.renda();
	        this.telefone = cadastroCliente.telefone();
	        this.dataCriacao = cadastroCliente.dataCriacao();
	        this.senha = cadastroCliente.senha();
	        this.confirmSenha = cadastroCliente.confirmSenha();
	        this.role = cadastroCliente.role();
	    }

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		if (this.role.equals(UserRole.ADMIN.name()))
			return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
		else
			return List.of(new SimpleGrantedAuthority("ROLE_USER"));
	}

	@Override
	public String getUsername() {
		return this.email;
	}
	
	@Override
	public String getPassword() {
		return this.senha;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}