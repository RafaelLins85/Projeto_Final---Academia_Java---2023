package com.suporte_tecnico.domain.dtos;

public class CredenciaisDTO {

	// ESSA CLASSE SERVE PARA FAZER A CONVERÇÃO DO USUÁRIO E SENHA QUE VIRÁ NA REQUISIÇÃO DO LOGIN
	
	// Variáveis
	private String email;
	private String senha;

	// Encasulamento
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
}
