package com.suporte_tecnico.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.suporte_tecnico.domain.Pessoa;
import com.suporte_tecnico.domain.Tecnico;
import com.suporte_tecnico.domain.dtos.TecnicoDTO;
import com.suporte_tecnico.repositories.PessoaRepository;
import com.suporte_tecnico.repositories.TecnicoRepository;
import com.suporte_tecnico.services.exceptions.DataIntegrityViolationException;
import com.suporte_tecnico.services.exceptions.ObjectnotFoundException;

@Service
public class TecnicoService {

	// Injetando a classe
	@Autowired
	private TecnicoRepository repository;
	@Autowired
	private PessoaRepository pessoaRepository;
	// Senha codificada
	@Autowired
	private BCryptPasswordEncoder encoder;

	// ESSA CLASSE SERÁ USADA NA TecnicoResource. TODOS OS MÉTODOS SERÃO USADOS LÁ.
	// MAS, TODOS OS MÉTODOS USADOS AQUI VEM DAS CLASSES TecnicoRepository,
	// PessoaRepository e BCryptPasswordEncoder. ONDE FOI IMPLEMENTADA UMA REGRA DE NEGÓCIO.

	// Método para criar técnicos
	public Tecnico create(TecnicoDTO objDTO) {
		// Processo de validação
		objDTO.setId(null);
		// Pegando a senha do objDTO.getSenha() e salvando codificada
		objDTO.setSenha(encoder.encode(objDTO.getSenha()));
		validaPorCpfEEmail(objDTO);
		// A variável newObj, recebendo o objeto de trânsferencia, que está na variável objDTO
		Tecnico newObj = new Tecnico(objDTO);
		// Agora, um novo tecnico será salvo no BDD
		return repository.save(newObj);
	}

	// Método para listar técnicos por id

	// Essa excecão vem da classe ObjectnotFoundException
	// Retornará um Optional, pq ele pode encontrar ou não
	public Tecnico findById(Integer id) {
		Optional<Tecnico> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectnotFoundException("Objeto não encontrado! Id: " + id));
	}

	// Método para listar todos os técnicos
	public List<Tecnico> findAll() {
		return repository.findAll();
	}

	// Método para atualizar técnicos
	public Tecnico update(Integer id, @Valid TecnicoDTO objDTO) {
		// Setando um id
		objDTO.setId(id);
		// Pegando o id e colocando na variável oldObj através do método findById(id)
		Tecnico oldObj = findById(id);
		// Fazendo a comparação de senha e email
		if (!objDTO.getSenha().equals(oldObj.getSenha()))
			objDTO.setSenha(encoder.encode(objDTO.getSenha()));
		// Usando o método validaPorCpfEEmail, para fazer a validação
		validaPorCpfEEmail(objDTO);
		// O objeto oldObj receberá os valores
		oldObj = new Tecnico(objDTO);
		// Agora, teremos uma atualização feita no BDD
		return repository.save(oldObj);
	}

	// Método para deletar técnicos
	public void delete(Integer id) {
		// A variável obj recebendo o findById(id)
		Tecnico obj = findById(id);

		// Se tentar excluir um tecnico, com ordem de serviço existente, entrará nessa
		// condição e será lançado uma execção que está na classe
		// DataIntegrityViolationException.
		if (obj.getChamados().size() > 0) {
			throw new DataIntegrityViolationException("Técnico possui ordens de serviço e não pode ser deletado!");
		}
		// Senão ele pode ser deletado normalmente.
		repository.deleteById(id);
	}

	// Método para validar cpf e email, para evitar erros no banco.
	// Pegando o cpf e colocando em uma condição. Se o id for diferente do que está
	// pegando no banco, então não é o que queremos.
	// Aí será lançado uma execção que está na classe DataIntegrityViolationException.
	// Retornará um Optional, pq ele pode encontrar ou não
	private void validaPorCpfEEmail(TecnicoDTO objDTO) {
		Optional<Pessoa> obj = pessoaRepository.findByCpf(objDTO.getCpf());
		if (obj.isPresent() && obj.get().getId() != objDTO.getId()) {
			throw new DataIntegrityViolationException("CPF já cadastrado no sistema!");
		}

		// Se o id for diferente, enteão o email já está cadastrado no banco.
		obj = pessoaRepository.findByEmail(objDTO.getEmail());
		if (obj.isPresent() && obj.get().getId() != objDTO.getId()) {
			throw new DataIntegrityViolationException("E-mail já cadastrado no sistema!");
		}
	}

}
