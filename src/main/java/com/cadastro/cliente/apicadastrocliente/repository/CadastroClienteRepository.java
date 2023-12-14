package com.cadastro.cliente.apicadastrocliente.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cadastro.cliente.apicadastrocliente.model.CastroCliente;

@Repository
public interface CadastroClienteRepository extends JpaRepository<CastroCliente, Integer> {
	
	
	CastroCliente findByNomeOrCpf(String nome, String cpf);
}
