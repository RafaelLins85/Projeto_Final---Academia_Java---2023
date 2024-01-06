package com.suporte_tecnico.resources.exceptions;

import java.util.ArrayList;
import java.util.List;


public class ValidationError extends StandardError {
	private static final long serialVersionUID = 1L;

	// Lista erros do tipo da classe FieldMessage
	private List<FieldMessage> errors = new ArrayList<>();

	// Construtor sem parametros.
	public ValidationError() {
		super();
	}

	// Construtor com valores para ser iniciados.
	public ValidationError(Long timestamp, Integer status, String error, String message, String path) {
		super(timestamp, status, error, message, path);
	}

	// Get de da lista erros
	public List<FieldMessage> getErrors() {
		return errors;
	}
 
    // Método da lista erros
	public void addError(String fieldName, String message) {
		this.errors.add(new FieldMessage(fieldName, message));
	}

}
