package com.suporte_tecnico.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.suporte_tecnico.domain.Cliente;
import com.suporte_tecnico.domain.dtos.ClienteDTO;
import com.suporte_tecnico.services.ClienteService;

//Essa classe é uma Bean @RestController
@RestController // Fazendo mapeamento dos endpoints
@RequestMapping(value = "/clientes")
public class ClienteResource {

	// Injeção de dependência
	@Autowired
	private ClienteService service;

	// MÉTODO PARA CRIAR UM CLIENTE

	// No corpo da requisição virá um ClienteDTO
	// O @Valid valida os campos da requisição
	@PostMapping
	public ResponseEntity<ClienteDTO> create(@Valid @RequestBody ClienteDTO objDTO) {
		// Vai ser criado o ClienteDTO na variável obj , depois será passado para
		// variável newobj do tipo Cliente
		Cliente newObj = service.create(objDTO);
		// URI de acesso ao novo objeto criado
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newObj.getId()).toUri();
		// Agora a URI é passada para concretizar
		return ResponseEntity.created(uri).build();
	}

	// MÉTOD PARAO LISTAR POR ID

	// Usando ClienteDTO para ser o objeto de dados de transfência para a entidade Cliente
	// Pegando o id da classe ClienteDTO pelo @PathVariable id
	// Usando a classe injetada ClienteService, para usar o método findById
	@GetMapping(value = "/{id}") // O valor do id vai vir na URL
	public ResponseEntity<ClienteDTO> findById(@PathVariable Integer id) {
		// A variável obj, receberá o método findById da classe ClienteService
		Cliente obj = service.findById(id);
		// Será retornado um ClienteDTO, com a variável obj como parâmetro
		return ResponseEntity.ok().body(new ClienteDTO(obj));
	}

	// MÉTODO PARA LISTAR TUDO

	// Quando tiver uma requisição @GetMapping sem nenhum parâmetro, esse método
	// será chamado
	// O retorno pra o usuário será uma lista de ClienteDTO -> (listDTO)
	@GetMapping
	public ResponseEntity<List<ClienteDTO>> findAll() {
		List<Cliente> list = service.findAll();
		List<ClienteDTO> listDTO = list.stream().map(obj -> new ClienteDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
	}

	// MÉTODO PARA ATUALIZAR UM CLIENTE

	// No corpo da requisição virá um CienteDTO
	// O @Valid valida os campos da requisição
	@PutMapping(value = "/{id}") // O valor do id vai vir na URL
	public ResponseEntity<ClienteDTO> update(@PathVariable Integer id, @Valid @RequestBody ClienteDTO objDTO) {
		// A variável obj, receberá o método update da classe ClienteService
		Cliente obj = service.update(id, objDTO);
		// Será retornado um ClienteDTO, com a variável obj como parâmetro
		return ResponseEntity.ok().body(new ClienteDTO(obj));
	}

	// MÉTODO PARA DELETAR UM CLIENTE

	@DeleteMapping(value = "/{id}") // O valor do id vai vir na URL
	public ResponseEntity<ClienteDTO> delete(@PathVariable Integer id) {
		// Pegando a método delete da classe ClienteService
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

}
