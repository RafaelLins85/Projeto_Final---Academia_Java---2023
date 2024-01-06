package com.suporte_tecnico.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.suporte_tecnico.domain.Pessoa;
import com.suporte_tecnico.repositories.PessoaRepository;
import com.suporte_tecnico.security.UserSS;

//Implementando a interface UserDetailsService
@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	
	// Injeção de depedência
	@Autowired
	private PessoaRepository repository;

	// Carregando o usuário pelo email
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		// Vai retornar um Optional pq ele pode ou não encontrar
		// Se ele encontrar, retorna tudo do contrato com UserSS
		// Senão, será retornada uma exeção, com o email de parâmetro
		Optional<Pessoa> user = repository.findByEmail(email);
		if(user.isPresent()) {
			return new UserSS(user.get().getId(), user.get().getEmail(), user.get().getSenha(), user.get().getPerfis());
		}
		throw new UsernameNotFoundException(email);
	}
}
