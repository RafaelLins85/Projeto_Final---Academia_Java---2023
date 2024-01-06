package com.suporte_tecnico.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.suporte_tecnico.domain.Chamado;
import com.suporte_tecnico.domain.Cliente;
import com.suporte_tecnico.domain.Tecnico;
import com.suporte_tecnico.domain.dtos.ChamadoDTO;
import com.suporte_tecnico.domain.enums.Prioridade;
import com.suporte_tecnico.domain.enums.Status;
import com.suporte_tecnico.repositories.ChamadoRepository;
import com.suporte_tecnico.services.exceptions.ObjectnotFoundException;

@Service
public class ChamadoService {

	// Injeção de Dependência
	@Autowired
	private ChamadoRepository repository;
	@Autowired
	private TecnicoService tecnicoService;
	// Senha codificada
	@Autowired
	private ClienteService clienteService;

	// Método para listar chamados por id

	// ESSA CLASSE SERÁ USADA NA ChamadoResource. TODOS OS MÉTODOS SERÃO USADOS LÁ.
	// MAS, TODOS OS MÉTODOS USADOS AQUI VEM DAS CLASSES ChamadoRepository,
	// TecnicoService e ClienteService. ONDE FOI IMPLEMENTADA UMA REGRA DE NEGÓCIO.

	// Método para criar chamados
	// Usando o método newChamado, junto com o obj, no parâmetro para criar um novo chamado
	public Chamado create(ChamadoDTO obj) {
		// Agora, um novo chamado será salvo no BDD
		return repository.save(newChamado(obj));
	}

	// Senão encontrar lança uma exeção
	// Essa excecão vem da classe ObjectnotFoundException
	// Retornará um Optional, pq ele pode encontrar ou não
	public Chamado findById(Integer id) {
		Optional<Chamado> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectnotFoundException("Objeto não encontrado! ID: " + id));
	}

	// Método para listar todos os chamados
	public List<Chamado> findAll() {
		return repository.findAll();
	}

	// Método para atualizar chamados
	// Usando o método newChamado para atualizar um novo chamado
	public Chamado update(Integer id, @Valid ChamadoDTO objDTO) {
		// Esse id é passado pelo usuário
		objDTO.setId(id);
		// oldObj vai ver se já existe um chamado, senão existir vai ser lançada uma exeção
		Chamado oldObj = findById(id);
		// Depois recebe um newChamado, e salva atualizando o BDD
		oldObj = newChamado(objDTO);
		return repository.save(oldObj);
	}

	// Recebendo chamado de Tecnico e de Cliente
	private Chamado newChamado(ChamadoDTO obj) {
		// Variável de tecnico recebe um tecnicoService que faz uma busca por id usando
		// o construtor de ChamadoDTO obj pra ter acesso ao getTecnico().getId()
		Tecnico tecnico = tecnicoService.findById(obj.getTecnico());
		// Variável de cliente recebe um clienteService que faz uma busca por id usando
		// o construtor de ChamadoDTO obj pra ter acesso ao getCliente().getId())
		Cliente cliente = clienteService.findById(obj.getCliente());

		// Criando um novo chamado 
		Chamado chamado = new Chamado();
		
		// Se o chamado for diferente de nulo atualiza, senão será criado um novo chamado
		if (obj.getId() != null) {
			chamado.setId(obj.getId());
		}
		// Fazendo tratamento da data de fechamento
		if (obj.getStatus().equals(2)) {
			chamado.setDataFechamento(LocalDate.now());
		}

		// Inserindo os dados do novo chamado para ser salvo no BDD
		chamado.setTecnico(tecnico);
		chamado.setCliente(cliente);
		chamado.setPrioridade(Prioridade.toEnum(obj.getPrioridade()));
		chamado.setStatus(Status.toEnum(obj.getStatus()));
		chamado.setTitulo(obj.getTitulo());
		chamado.setObservacoes(obj.getObservacoes());
		return chamado;
	}

}
