package com.cadastro.cliente.apicadastrocliente.exception;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ExceptionResponse implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private final LocalDateTime timestamp;
	private final List<String> message;
	private final String details;

}