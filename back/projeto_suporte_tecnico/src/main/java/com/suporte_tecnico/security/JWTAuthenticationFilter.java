package com.suporte_tecnico.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.suporte_tecnico.domain.dtos.CredenciaisDTO;

// Estendendo de UsernamePasswordAuthenticationFilter
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	// Injeção de dependência
	private AuthenticationManager authenticationManager;
	private JWTUtil jwtUtil;

	// Construtor com parâmetros
	public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
		super();
		this.authenticationManager = authenticationManager;
		this.jwtUtil = jwtUtil;
	}

	// Método que tenta criar uma autenticação
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		try {
			// Instância de CredenciaisDTO
			CredenciaisDTO creds = new ObjectMapper().readValue(request.getInputStream(), CredenciaisDTO.class);
			// Instância de UsernamePasswordAuthenticationToken
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
					creds.getEmail(), creds.getSenha(), new ArrayList<>());
			// A variável authentication do tipo Authentication, receberá a
			// authenticationToken como parâmetro da authenticationManager
			Authentication authentication = authenticationManager.authenticate(authenticationToken);
			// E será retornada a authentication
			return authentication;
		} catch (Exception e) {
			// Senão pode lançar uma exceção
			throw new RuntimeException(e);
		}
	}

	// Método para quando a autenticação der certo
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		// Pegando o getUsername() e passando para variável username
		String username = ((UserSS) authResult.getPrincipal()).getUsername();
		// Pegando o username como parâmetro pela jwtUtil passando para variável token
		String token = jwtUtil.generateToken(username);
		response.setHeader("access-control-expose-headers", "Authorization");
		response.setHeader("Authorization", "Bearer " + token);
	}

	// Método para quando a autenticação der errado
	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {

		response.setStatus(401);
		response.setContentType("application/json");
		response.getWriter().append(json());
	}

	// Método para complementar a resposta do método unsuccessfulAuthentication
	private CharSequence json() {
		long date = new Date().getTime();
		return "{" + "\"timestamp\": " + date + ", " + "\"status\": 401, " + "\"error\": \"Não autorizado\", "
				+ "\"message\": \"Email ou senha inválidos\", " + "\"path\": \"/login\"}";
	}

}