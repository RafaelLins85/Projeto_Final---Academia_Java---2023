package com.suporte_tecnico.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.suporte_tecnico.domain.dtos.TecnicoDTO;
import com.suporte_tecnico.domain.enums.Perfil;

//Pra dizer que a classe é uma entidade
@Entity
public class Tecnico extends Pessoa {
	private static final long serialVersionUID = 1L;
    
	//@JsonIgnore protege contra a serialização
	@JsonIgnore
	// @OneToMany. Um técnico para muitos chamados
	// E sendo mapeado pelo atributo tecnico da classe Chamado
	@OneToMany(mappedBy = "tecnico", fetch = FetchType.EAGER) 
	// Lista chamados do tipo da classe Chamado
	private List<Chamado> chamados = new ArrayList<>(); // O new ArrayList<>() para evitar a execão de ponteiro nulo, o new point exception

	// Construtor sem parâmetros. Sempre que um tecnico for adicionado, receberá o perfil de tecnico
	public Tecnico() {
		super();
		addPerfil(Perfil.TECNICO);
	}

	// Construtor com valores para ser iniciados. Sempre que um tecnico for adicionado, receberá o perfil de tecnico
	public Tecnico(Integer id, String nome, String cpf, String email, String senha) {
		super(id, nome, cpf, email, senha);
		addPerfil(Perfil.TECNICO);
	}
	
	// Construtor com valores que estão vindo de TecnicoDTO passando uma conversão
	public Tecnico(TecnicoDTO obj) {
		super();
		this.id = obj.getId();
		this.nome = obj.getNome();
		this.cpf = obj.getCpf();
		this.email = obj.getEmail();
		this.senha = obj.getSenha();
		this.perfis = obj.getPerfis().stream().map(x -> x.getCodigo()).collect(Collectors.toSet());
		this.dataCriacao = obj.getDataCriacao();
	}

	// Encapsulamento
	public List<Chamado> getChamados() {
		return chamados;
	}

	public void setChamados(List<Chamado> chamados) {
		this.chamados = chamados;
	}
}

