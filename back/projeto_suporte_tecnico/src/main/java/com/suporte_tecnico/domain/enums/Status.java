package com.suporte_tecnico.domain.enums;

public enum Status {

	// Constantes
	// Já com valores e descrição
	ABERTO(0, "ABERTO"), ANDAMENTO(1, "ANDAMENTO"), ENCERRADO(2, "ENCERRADO");

	// Variáveis
	private Integer codigo;
	private String descricao;

	//Construtor
	private Status(Integer codigo, String descricao) {
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

	// O método é do tipo Status
	public static Status toEnum(Integer cod) {
		// Se o código for nulo ele retorna nulo
		if (cod == null) {
			return null;
		}
		// Os valores dos códigos existentes nas contantes serão tratados aqui
		for (Status x : Status.values()) {
			// Se o código for igual a getCodigo, ele retorna o valor
			if (cod.equals(x.getCodigo())) {
				return x;
			}
		}
		// Se não entrar no if ou no for, estoura uma exceção
		throw new IllegalArgumentException("Status inválido");
	}
}
