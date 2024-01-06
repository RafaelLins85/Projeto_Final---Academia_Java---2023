package com.suporte_tecnico.resources.exceptions;

import java.io.Serializable;

// SEGUINDO AS BOAS PRÁTICAS DO TRATAMENTO DE EXCEÇÕES
// SERVIRÁ DE PRADRÃO PARA CLASSE ResourceExceptionHandler

public class StandardError implements Serializable {
	private static final long serialVersionUID = 1L;

	// Variáveis
	private Long timestamp;
	private Integer status;
	private String error;
	private String message;
	private String path;

	// Construtores
	public StandardError() {
		super();
	}

	public StandardError(Long timestamp, Integer status, String error, String message, String path) {
		super();
		this.timestamp = timestamp;
		this.status = status;
		this.error = error;
		this.message = message;
		this.path = path;
	}

	// Encapsulamento
	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}
