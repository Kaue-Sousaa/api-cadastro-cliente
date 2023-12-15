package com.cadastro.cliente.apicadastrocliente.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cadastro.cliente.apicadastrocliente.model.User;

public interface UserRepository extends JpaRepository<User, Integer>{
	
	User findByLogin(String login);

}
