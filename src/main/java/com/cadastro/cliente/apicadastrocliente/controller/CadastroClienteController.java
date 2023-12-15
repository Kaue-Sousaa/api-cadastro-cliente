package com.cadastro.cliente.apicadastrocliente.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cadastro.cliente.apicadastrocliente.dto.CadastroClienteDto;
import com.cadastro.cliente.apicadastrocliente.service.ClienteService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/cliente")
@RequiredArgsConstructor
@Validated
public class CadastroClienteController {
	
	private final ClienteService service;
	
	@PostMapping(value="/cadastro",consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> cadastrarCliente(@Valid @RequestBody CadastroClienteDto dadosCliente){
		return ResponseEntity.ok(service.cadastrarCliente(dadosCliente));
	}
	
	@PutMapping(value = "/atualizar",consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CadastroClienteDto> atualizarCadastroCliente(@Valid @RequestBody CadastroClienteDto dadosCliente){
		return ResponseEntity.ok(service.atualizarCadastro(dadosCliente));
	}
	
	@DeleteMapping(value = "/excluir/{id}")
	public void deletarCadastroCliente(@Valid @PathVariable Integer id){
		service.deletarCadastro(id);
	} 
	
}
