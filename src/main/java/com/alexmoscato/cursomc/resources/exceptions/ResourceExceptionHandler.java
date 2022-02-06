package com.alexmoscato.cursomc.resources.exceptions;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.alexmoscato.cursomc.services.exceptions.DataIntegrityException;
import com.alexmoscato.cursomc.services.exceptions.ObjectNotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler {
	
	//Mensagem que retorna no corpo caso uma exception do tipo ObjectNotFoundException for encontrada.
	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardException> objectNotFound(ObjectNotFoundException e, HttpServletRequest request){
		StandardException error = new StandardException(HttpStatus.NOT_FOUND.value(), e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	} 
	
	//Mensagem que retorna no corpo caso uma exception do tipo DataIntegrityException for encontrada.
	@ExceptionHandler(DataIntegrityException.class)
	public ResponseEntity<StandardException> dataIntegrity(DataIntegrityException e, HttpServletRequest request){
		StandardException error = new StandardException(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
	
	//Mensagem que retorna no corpo caso uma exception do tipo MethodArgumentNotValidException for encontrada.
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandardException> validation(MethodArgumentNotValidException e, HttpServletRequest request){
		ValidationException error = new ValidationException(HttpStatus.BAD_REQUEST.value(), "Erro de validação", System.currentTimeMillis());
		for(FieldError x: e.getBindingResult().getFieldErrors()) {
			error.addErros(x.getField(), x.getDefaultMessage());
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
}
