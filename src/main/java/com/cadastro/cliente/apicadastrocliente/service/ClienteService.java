package com.cadastro.cliente.apicadastrocliente.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cadastro.cliente.apicadastrocliente.dto.CadastroClienteDto;
import com.cadastro.cliente.apicadastrocliente.exception.RequiredObjectIsNullException;
import com.cadastro.cliente.apicadastrocliente.exception.ResourceNotFoundException;
import com.cadastro.cliente.apicadastrocliente.model.CadastroCliente;
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
			if(!isValidCampoConfirmSenha(dadosCliente.senha(), dadosCliente.confirmSenha())) {
				return "As senhas não são iguais. Tente novamente.";
			}
			return salvarCliente(dadosCliente);
		}
		return verificarCampoNomeAndCpf(entity, dadosCliente);
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
	
	public CadastroClienteDto buscarCliente(Integer id) {
		var entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Usario não encontrado!"));
		return new CadastroClienteDto(entity);
	}
	
	public List<CadastroClienteDto> listarClientes() {
		List<CadastroClienteDto> listaCliente = new ArrayList<>();
		var entity = repository.findAll();
		if(!entity.isEmpty()) {
			listaCliente = entity.stream().map(CadastroClienteDto::new).toList();
		}
		return listaCliente;
	}
	
	public void deletarCadastro(Integer id) {
		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado!"));
		repository.delete(entity);
	}
	
	private String verificarCampoNomeAndCpf(CadastroCliente dadosBanco, CadastroClienteDto newDadosCliente) {
		if(dadosBanco.getNome().equals(newDadosCliente.nome()) && dadosBanco.getCpf().equals(newDadosCliente.cpf())){
			return "Nome e Cpf do cliente já cadastrados!";
		}else if(dadosBanco.getNome().equals(newDadosCliente.nome()) && !dadosBanco.getCpf().equals(newDadosCliente.cpf())) {
			return "Nome do cliente já cadastrado!";
		}else if(!dadosBanco.getNome().equals(newDadosCliente.nome()) && dadosBanco.getCpf().equals(newDadosCliente.cpf())) {
			return "Cpf do cliente já cadastrado!";
		}
		return "";
	}
	
	private boolean isValidCampoConfirmSenha(String senha, String confirmSenha) {
		return senha.equals(confirmSenha);
	}

	private String salvarCliente(CadastroClienteDto dadosCliente) {
		CadastroCliente cadastroCliente = new CadastroCliente(dadosCliente);
		cadastroCliente.setSenha(passwordEncoder(dadosCliente.senha()));
		repository.save(cadastroCliente);
		return "Usúario cadastrado com sucesso!";
	}
	
	private String passwordEncoder(String senha) {
		return new BCryptPasswordEncoder().encode(senha); 
	}
}