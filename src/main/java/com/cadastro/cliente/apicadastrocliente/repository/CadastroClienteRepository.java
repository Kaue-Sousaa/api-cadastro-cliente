package com.cadastro.cliente.apicadastrocliente.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cadastro.cliente.apicadastrocliente.model.Cliente;

@Repository
public interface CadastroClienteRepository extends JpaRepository<Cliente, Integer> {
	
	
	Optional<Integer> findByNomeOrCpf(String nome, String cpf);
}
