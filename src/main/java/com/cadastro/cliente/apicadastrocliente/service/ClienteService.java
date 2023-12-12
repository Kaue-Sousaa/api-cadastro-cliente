package com.cadastro.cliente.apicadastrocliente.service;

import org.springframework.stereotype.Service;

import com.cadastro.cliente.apicadastrocliente.dto.CadastroClienteDto;
import com.cadastro.cliente.apicadastrocliente.exception.RequiredObjectIsNullException;
import com.cadastro.cliente.apicadastrocliente.exception.ResourceNotFoundException;
import com.cadastro.cliente.apicadastrocliente.model.Cliente;
import com.cadastro.cliente.apicadastrocliente.repository.CadastroClienteRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClienteService {
	
	private final CadastroClienteRepository repository;
	
	public String cadastrarCliente(CadastroClienteDto dadosCliente) {
		if(dadosCliente == null) {
			throw new RequiredObjectIsNullException("Dados estão nulos");
		}else {
			repository.save(new Cliente(dadosCliente));
			return "Usúario cadastrado com sucesso!";
		}
	}

	public CadastroClienteDto atualizarCadastro(CadastroClienteDto dadosCliente) {
		if(dadosCliente == null) {
			throw new RequiredObjectIsNullException("Dados estão nulos");
		}
			
		var entity = repository.findById(dadosCliente.id())
				.orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado!"));
		entity.setNome(dadosCliente.nome());
		entity.setEmail(dadosCliente.email());
		entity.setCpf(dadosCliente.cpf());
		entity.setRenda(dadosCliente.renda());
		entity.setTelefone(dadosCliente.telefone());

		repository.save(entity);
		return new CadastroClienteDto(entity);
	}

	public void deletarCadastro(Integer id) {
		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado!"));
		repository.delete(entity);
	}
}
