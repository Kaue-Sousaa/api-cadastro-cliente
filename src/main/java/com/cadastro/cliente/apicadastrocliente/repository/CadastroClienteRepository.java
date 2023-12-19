package com.cadastro.cliente.apicadastrocliente.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cadastro.cliente.apicadastrocliente.model.CadastroCliente;

@Repository
public interface CadastroClienteRepository extends JpaRepository<CadastroCliente, Integer> {
	
	CadastroCliente findByNomeOrCpf(String nome, String cpf);
	
	CadastroCliente findByEmail(String email);
	
}
