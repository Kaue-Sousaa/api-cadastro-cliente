package com.cadastro.cliente.apicadastrocliente.exception.handler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.cadastro.cliente.apicadastrocliente.exception.ExceptionResponse;
import com.cadastro.cliente.apicadastrocliente.exception.InvalidJwtAuthenticationException;
import com.cadastro.cliente.apicadastrocliente.exception.RequiredObjectIsNullException;
import com.cadastro.cliente.apicadastrocliente.exception.ResourceNotFoundException;

@RestControllerAdvice
public class CustomizedResponseEntityExceptioHandler {
	
	@ExceptionHandler({RequiredObjectIsNullException.class, MethodArgumentNotValidException.class})
	public final ResponseEntity<ExceptionResponse> handleAllException(MethodArgumentNotValidException ex, WebRequest request){
		BindingResult bindingResult = ex.getBindingResult();
        List<String> errorMessages = new ArrayList<>();
        bindingResult.getFieldErrors().forEach(fieldError -> {
            errorMessages.add(fieldError.getField() + ": " + fieldError.getDefaultMessage());
        });
		
		ExceptionResponse exceptionResponse = new ExceptionResponse(
				LocalDateTime.now(), errorMessages , request.getDescription(false));
		
		return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public final ResponseEntity<ExceptionResponse> handleNotFoundException(MethodArgumentNotValidException ex, WebRequest request){
		BindingResult bindingResult = ex.getBindingResult();
        List<String> errorMessages = new ArrayList<>();
        bindingResult.getFieldErrors().forEach(fieldError -> {
            errorMessages.add(fieldError.getField() + ": " + fieldError.getDefaultMessage());
        });
		
		ExceptionResponse exceptionResponse = new ExceptionResponse(
				LocalDateTime.now(), errorMessages, request.getDescription(false));
		
		return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
	}
	
	
	@ExceptionHandler(InvalidJwtAuthenticationException.class)
	public final ResponseEntity<ExceptionResponse> handleInvalidJwtAuthenticationException(MethodArgumentNotValidException ex, WebRequest request){
		BindingResult bindingResult = ex.getBindingResult();
        List<String> errorMessages = new ArrayList<>();
        bindingResult.getFieldErrors().forEach(fieldError -> {
            errorMessages.add(fieldError.getField() + ": " + fieldError.getDefaultMessage());
        });
        
		ExceptionResponse exceptionResponse = new ExceptionResponse(
				LocalDateTime.now(), errorMessages, request.getDescription(false));
		
		return new ResponseEntity<>(exceptionResponse, HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler({IllegalStateException.class, NullPointerException.class})
	public final ResponseEntity<ExceptionResponse> handleInternalServerErroException(
			MethodArgumentNotValidException ex, WebRequest request){
		BindingResult bindingResult = ex.getBindingResult();
        List<String> errorMessages = new ArrayList<>();
        bindingResult.getFieldErrors().forEach(fieldError -> {
            errorMessages.add(fieldError.getField() + ": " + fieldError.getDefaultMessage());
        });
		
		ExceptionResponse exceptionResponse = new ExceptionResponse(
				LocalDateTime.now(), errorMessages , request.getDescription(false));
		
		return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	
}
