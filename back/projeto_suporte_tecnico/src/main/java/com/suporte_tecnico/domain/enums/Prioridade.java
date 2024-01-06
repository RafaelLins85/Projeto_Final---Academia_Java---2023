package com.suporte_tecnico.domain.enums;

public enum Prioridade {

	// Constantes
	// Já com valores e descrição
	BAIXA(0, "BAIXA"), MEDIA(1, "MEDIA"), ALTA(2, "ALTA");

	// Variáveis
	private Integer codigo;
	private String descricao;

	//Construtor
	private Prioridade(Integer codigo, String descricao) {
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

	// O método é do tipo Prioridade
	public static Prioridade toEnum(Integer cod) {
		// Se o código for nulo ele retorna nulo
		if (cod == null) {
			return null;
		}
		// Os valores dos códigos existentes nas contantes serão tratados aqui
		for (Prioridade x : Prioridade.values()) {
			// Se o código for igual a getCodigo, ele retorna o valor
			if (cod.equals(x.getCodigo())) {
				return x;
			}
		}
		// Se não entrar no if ou no for, estoura uma exceção
		throw new IllegalArgumentException("Prioridade inválida");
	}
}
