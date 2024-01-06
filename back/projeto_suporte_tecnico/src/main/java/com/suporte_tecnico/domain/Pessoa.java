package com.suporte_tecnico.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.validator.constraints.br.CPF;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.suporte_tecnico.domain.enums.Perfil;

//Pra dizer que a classe é uma entidade
@Entity
public abstract class Pessoa implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// Variáveis
	@Id             // O id vai ser  gerado pelo banco
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Integer id;
	protected String nome;
	// Essa anotação serve para ver se o CPF é válido 
	@CPF
	@Column(unique = true)
	protected String cpf;
	@Column(unique = true)
	protected String email;
	protected String senha;
	
	// Lita de Perfil
	// @ElementCollection. Quando buscar essa lista ela irá trazer o perfil do usuário
	@ElementCollection(fetch = FetchType.EAGER)
	// @CollectionTable. Irá ter uma tabela PERFIS no banco
	@CollectionTable(name = "PERFIS") //O Set evita que tenha dois valores iguais dentro da lista. O new HashSet<>() para evitar a execão de ponteiro nulo, o new point exception
	protected Set<Integer> perfis = new HashSet<>(); // Só irá armazenar o código do Perfil
	
	// Variável dataCriacao, já pegando a data atual
	@JsonFormat(pattern = "dd/MM/yyyy")
	protected LocalDate dataCriacao = LocalDate.now();

	// Construtor sem parâmetros
	public Pessoa() {
		super();
	}

	// Construtor com valores para ser iniciados. Menos dataCriacao que recebe o valor da data atual
	public Pessoa(Integer id, String nome, String cpf, String email, String senha) {
		super();
		this.id = id;
		this.nome = nome;
		this.cpf = cpf;
		this.email = email;
		this.senha = senha;
	}

	// Encapsulamento
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

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

	// Retornando uma lista de Perfil
	// Fazendo stream e mapeando cada perfil usando o método toEnum da Enum Perfil
	// Depois coleta e faz a conversão para uma lista do tipo set
	public Set<Perfil> getPerfis() {
		return perfis.stream().map(x -> Perfil.toEnum(x)).collect(Collectors.toSet());
	}
    
	// Adicionando um perfil único para cada pessoa
	// Tudo isso já vem da Enum Perfil
	public void addPerfil(Perfil perfil) {
		this.perfis.add(perfil.getCodigo());
	}

	public LocalDate getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(LocalDate dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	// Métodos hashCode e equals
	// Para fazer a comparação dos objetos pelos valores dos atributos usando o id e cpf
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cpf == null) ? 0 : cpf.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pessoa other = (Pessoa) obj;
		if (cpf == null) {
			if (other.cpf != null)
				return false;
		} else if (!cpf.equals(other.cpf))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}


