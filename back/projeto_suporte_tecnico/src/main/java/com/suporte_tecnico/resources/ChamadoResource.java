package com.suporte_tecnico.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.suporte_tecnico.domain.Chamado;
import com.suporte_tecnico.domain.dtos.ChamadoDTO;
import com.suporte_tecnico.services.ChamadoService;

//Essa classe é uma Bean @RestController
@RestController // Fazendo mapeamento dos endpoints
@RequestMapping(value = "/chamados")
public class ChamadoResource {

	// Injeção de dependência
	@Autowired
	private ChamadoService service;

	// Não existe o endpoint para deletar Chamado
	// O chamado será encerrado em uma tela do front

	// MÉTODO PARA CRIAR UM CHAMADO

	// No corpo da requisição virá um ChamadoDTO
	// O @Valid valida os campos da requisição
	@PostMapping
	public ResponseEntity<ChamadoDTO> create(@Valid @RequestBody ChamadoDTO obj) {
		// Vai ser criado o ChamadoDTO na variável obj , depois será passado para
		// variável newobj do tipo Chamado
		Chamado newObj = service.create(obj);
		// URI de acesso ao novo objeto criado
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newObj.getId()).toUri();
		// Agora a URI é passada para concretizar
		return ResponseEntity.created(uri).build();
	}

	// MÉTODO LISTANDO POR ID

	// Usando TecnicoDTO para ser o objeto de dados de transfência para a entidade Chamado
	// Pegando o id da classe ChamadoDTO pelo @PathVariable id
	// Usando a classe injetada ChamadoService, para usar o método findById
	@GetMapping(value = "/{id}") // O valor do id vai vir na URL
	public ResponseEntity<ChamadoDTO> findById(@PathVariable Integer id) {
		// A variável obj, receberá o método findById da classe ChamadoService
		Chamado obj = service.findById(id);
		// Será retornado um ChamadoDTO, com a variável obj como parâmetro
		return ResponseEntity.ok().body(new ChamadoDTO(obj));
	}

	// MÉTODO LISTANDO TUDO

	// Quando tiver uma requisição @GetMapping sem nenhum parâmetro, esse método será chamado
	// O retorno pra o usuário será uma lista de ChamadoDTO -> (listDTO)
	@GetMapping
	public ResponseEntity<List<ChamadoDTO>> findAll() {
		List<Chamado> list = service.findAll();
		List<ChamadoDTO> listDTO = list.stream().map(obj -> new ChamadoDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
	}

	// MÉTODO ATUALIZANDO UM CHAMADO

	// No corpo da requisição virá um ChamadoDTO
	// O @Valid valida os campos da requisição
	@PutMapping(value = "/{id}") // O valor do id vai vir na URL
	public ResponseEntity<ChamadoDTO> update(@PathVariable Integer id, @Valid @RequestBody ChamadoDTO objDTO) {
		// A variável obj, receberá o método update da classe ChamadoService
		Chamado newObj = service.update(id, objDTO);
		// Será retornado um ChamadoDTO, com a variável newObj como parâmetro
		return ResponseEntity.ok().body(new ChamadoDTO(newObj));
	}
}
