package com.cadastro.cliente.apicadastrocliente.service;

import org.springframework.stereotype.Service;

import com.cadastro.cliente.apicadastrocliente.dto.CadastroClienteDto;
import com.cadastro.cliente.apicadastrocliente.exception.RequiredObjectIsNullException;
import com.cadastro.cliente.apicadastrocliente.exception.ResourceNotFoundException;
import com.cadastro.cliente.apicadastrocliente.model.CastroCliente;
import com.cadastro.cliente.apicadastrocliente.repository.CadastroClienteRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClienteService {
	
	private final CadastroClienteRepository repository;
	
	public String cadastrarCliente(CadastroClienteDto dadosCliente) {
		if (dadosCliente == null) {
			throw new RequiredObjectIsNullException(
					"Não foi possivel realizar cadastro, pois os dados estão nulos");
		}
		var entity = repository.findByNomeOrCpf(dadosCliente.nome(), dadosCliente.cpf());
		if(entity == null) {
			repository.save(new CastroCliente(dadosCliente));
			return "Usúario cadastrado com sucesso!";
		}
		return verificarCampoNomeAndCpf(entity, dadosCliente);
	}
	
	private String verificarCampoNomeAndCpf(CastroCliente dadosBanco, CadastroClienteDto newDadosCliente) {
		if(dadosBanco.getNome().equals(newDadosCliente.nome()) && dadosBanco.getCpf().equals(newDadosCliente.cpf())){
			return "Nome e Cpf do cliente já cadastrados!";
		}else if(dadosBanco.getNome().equals(newDadosCliente.nome()) && !dadosBanco.getCpf().equals(newDadosCliente.cpf())) {
			return "Nome do cliente já cadastrado!";
		}else if(!dadosBanco.getNome().equals(newDadosCliente.nome()) && dadosBanco.getCpf().equals(newDadosCliente.cpf())) {
			return "Cpf do cliente já cadastrado!";
		}
		return "";
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
