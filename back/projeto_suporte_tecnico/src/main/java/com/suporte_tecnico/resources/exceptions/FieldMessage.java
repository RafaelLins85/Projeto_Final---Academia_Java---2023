package com.suporte_tecnico.resources.exceptions;

import java.io.Serializable;

// CLASSE QUE SERVE DE PADRÃO

public class FieldMessage implements Serializable {
	private static final long serialVersionUID = 1L;

	// Atributos
	private String fieldName;
	private String message;

	// Construtor sem parametros.
	public FieldMessage() {
		super();
	}

	// Construtor com valores para ser iniciados.
	public FieldMessage(String fieldName, String message) {
		super();
		this.fieldName = fieldName;
		this.message = message;
	}

	// Encapsulamento
	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
