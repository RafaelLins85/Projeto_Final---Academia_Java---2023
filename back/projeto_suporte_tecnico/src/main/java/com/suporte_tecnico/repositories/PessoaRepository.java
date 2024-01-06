package com.suporte_tecnico.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.suporte_tecnico.domain.Pessoa;

// Estendendo JpaRepository pegando a classse Pessoa, e o tipo primitivo do identificador da classe que é um Integer
public interface PessoaRepository extends JpaRepository<Pessoa, Integer> {

	// Métodos que serão usados para validação, na classe TecnicoService
	// Como Tecnico e Cliente herdam de Pessoa poderam usa-los
	// Retornará um Optional, pq ele pode encontrar ou não
	Optional<Pessoa> findByCpf(String cpf);
	Optional<Pessoa> findByEmail(String email);

}
