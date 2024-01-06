package com.suporte_tecnico.domain;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.suporte_tecnico.domain.enums.Prioridade;
import com.suporte_tecnico.domain.enums.Status;

//Pra dizer que a classe é uma entidade
@Entity
public class Chamado implements Serializable {

	private static final long serialVersionUID = 1L;

	// Variáveis
	@Id           // O id vai ser  gerado pelo banco
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	// Variável dataAbertura, já pegando a data atual
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataAbertura = LocalDate.now();
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataFechamento;
	// Variável prioridade é do tipo da classe Prioridade
	private Prioridade prioridade;
	// Variável status é do tipo da classe Status
	private Status status;
	private String titulo;
	private String observacoes;

	// Variável tecnico do tipo Tecnico
	// @ManyToOne. Muitos para um
	// @JoinColumn. Nome da coluna no banco
	@ManyToOne
	@JoinColumn(name = "tecnico_id")
	private Tecnico tecnico;

	// Variável cliente do tipo Cliente
	@ManyToOne
	@JoinColumn(name = "cliente_id")
	private Cliente cliente;

	// Construtor sem parâmetros
	public Chamado() {
		super();
	}

	// Construtor com valores para ser iniciados.
	// Menos dataAbertura que recebe o valor da data atual, e dataFechamento que fecha automáticamente
	public Chamado(Integer id, Prioridade prioridade, Status status, String titulo, String observacoes, Tecnico tecnico,
			Cliente cliente) {
		super();
		this.id = id;
		this.prioridade = prioridade;
		this.status = status;
		this.titulo = titulo;
		this.observacoes = observacoes;
		this.tecnico = tecnico;
		this.cliente = cliente;
	}

	// Encapsulamento
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public LocalDate getDataAbertura() {
		return dataAbertura;
	}

	public void setDataAbertura(LocalDate dataAbertura) {
		this.dataAbertura = dataAbertura;
	}

	public LocalDate getDataFechamento() {
		return dataFechamento;
	}

	public void setDataFechamento(LocalDate dataFechamento) {
		this.dataFechamento = dataFechamento;
	}

	public Prioridade getPrioridade() {
		return prioridade;
	}

	public void setPrioridade(Prioridade prioridade) {
		this.prioridade = prioridade;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}

	public Tecnico getTecnico() {
		return tecnico;
	}

	public void setTecnico(Tecnico tecnico) {
		this.tecnico = tecnico;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	// Métodos hashCode e equals
	// Para fazer a comparação dos objetos pelos valores dos atributos usando o id
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		Chamado other = (Chamado) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
