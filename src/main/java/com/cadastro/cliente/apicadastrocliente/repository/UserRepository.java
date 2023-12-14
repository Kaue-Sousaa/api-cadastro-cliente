package com.cadastro.cliente.apicadastrocliente.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.cadastro.cliente.apicadastrocliente.model.User;

public interface UserRepository extends JpaRepository<User, Integer>{
	
	UserDetails findByLogin(String login);

}
