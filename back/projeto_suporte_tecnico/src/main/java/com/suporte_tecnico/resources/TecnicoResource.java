package com.suporte_tecnico.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.suporte_tecnico.domain.Tecnico;
import com.suporte_tecnico.domain.dtos.TecnicoDTO;
import com.suporte_tecnico.services.TecnicoService;

// Essa classe é uma Bean @RestController
@RestController // Fazendo mapeamento dos endpoints
@RequestMapping(value = "/tecnicos")
public class TecnicoResource {

	// Injeção de dependência
	@Autowired
	private TecnicoService service;

	// MÉTODO PARA CRIAR UM TECNICO

	// Só quem tem autorização pra criar é o administrador
	// Se tiver o "hasAnyRole('ADMIN')", será identificado como administrador e pode
	// criar um técnico
	// No corpo da requisição virá um TecnicoDTO
	// O @Valid valida os campos da requisição
	@PreAuthorize("hasAnyRole('ADMIN')")
	@PostMapping
	public ResponseEntity<TecnicoDTO> create(@Valid @RequestBody TecnicoDTO objDTO) {
		// Vai ser criado o TecnicoDTO na variável obj , depois será passado para
		// variável newobj do tipo Tecnico
		Tecnico newObj = service.create(objDTO);
		// URI de acesso ao novo objeto criado
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newObj.getId()).toUri();
		// Agora a URI é passada para concretizar
		return ResponseEntity.created(uri).build();
	}

	// MÉTODO PARA LISTAR POR ID

	// Usando TecnicoDTO para ser o objeto de dados de transfência para a entidade Tecnico
	// Pegando o id da classe TecnicoDTO pelo @PathVariable id
	// Usando a classe injetada TecnicoService, para usar o método findById
	@GetMapping(value = "/{id}") // O valor do id vai vir na URL
	public ResponseEntity<TecnicoDTO> findById(@PathVariable Integer id) {
		// A variável obj, receberá o método findById da classe TecnicoService
		Tecnico obj = service.findById(id);
		// Será retornado um TecnicoDTO, com a variável obj como parâmetro
		return ResponseEntity.ok().body(new TecnicoDTO(obj));
	}

	// MÉTODO PARA LISTAR TUDO

	// Quando tiver uma requisição @GetMapping sem nenhum parâmetro, esse método será chamado
	// O retorno pra o usuário será uma lista de TecnicoDTO -> (listDTO)
	@GetMapping
	public ResponseEntity<List<TecnicoDTO>> findAll() {
		List<Tecnico> list = service.findAll();
		List<TecnicoDTO> listDTO = list.stream().map(obj -> new TecnicoDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
	}

	// MÉTODO PARA ATUALIZAR UM TECNICO

	// Só quem tem autorização pra atualizar é o administrador
	// Se tiver o "hasAnyRole('ADMIN')", será identificado como administrador e pode atualizar um técnico
	// No corpo da requisição virá um TecnicoDTO
	// O @Valid valida os campos da requisição
	@PreAuthorize("hasAnyRole('ADMIN')")
	@PutMapping(value = "/{id}") // O valor do id vai vir na URL
	public ResponseEntity<TecnicoDTO> update(@PathVariable Integer id, @Valid @RequestBody TecnicoDTO objDTO) {
		// A variável obj, receberá o método update da classe TecnicoService
		Tecnico obj = service.update(id, objDTO);
		// Será retornado um TecnicoDTO, com a variável obj como parâmetro
		return ResponseEntity.ok().body(new TecnicoDTO(obj));
	}

	// MÉTODO PARA DELETAR UM TECNICO

	// Só quem tem autorização pra deletar é o administrador
	// Se tiver o "hasAnyRole('ADMIN')", será identificado como administrador e pode
	// deletar um técnico
	@PreAuthorize("hasAnyRole('ADMIN')")
	@DeleteMapping(value = "/{id}") // O valor do id vai vir na URL
	public ResponseEntity<TecnicoDTO> delete(@PathVariable Integer id) {
		// Pegando a método delete da classe TecnicoService
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}
