package com.suporte_tecnico.domain.enums;

public enum Perfil {

	// Constantes
	// Já com valores e descrição
	ADMIN(0, "ROLE_ADMIN"), CLIENTE(1, "ROLE_CLIENTE"), TECNICO(2, "ROLE_TECNICO");
	
	//Variáveis 
	private Integer codigo;
	private String descricao;
	
	//Construtor
	private Perfil(Integer codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	// Encapsulamento(Só os Get's)
	public Integer getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}
	
	// O método é do tipo Perfil
	public static Perfil toEnum(Integer cod) {
		// Se o código for nulo ele retorna nulo
		if(cod == null) {
			return null;
		}
		// Os valores dos códigos existentes nas contantes serão tratados aqui
		for(Perfil x : Perfil.values()) {
			// Se o código for igual a getCodigo, ele retorna o valor
			if(cod.equals(x.getCodigo())) {
				return x;
			}
		}
		// Se não entrar no if ou no for, estoura uma exceção
		throw new IllegalArgumentException("Perfil inválido");
	}
}
