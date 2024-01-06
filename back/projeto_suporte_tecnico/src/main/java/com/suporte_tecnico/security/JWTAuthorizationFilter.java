package com.suporte_tecnico.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

// Estendendo de BasicAuthenticationFilter
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {
   
	// Injeção de dependência
	private JWTUtil jwtUtil;
	private UserDetailsService userDetailsService;

	// Construtor com parâmetros
	public JWTAuthorizationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil,
			UserDetailsService userDetailsService) {
		super(authenticationManager);
		this.jwtUtil = jwtUtil;
		this.userDetailsService = userDetailsService;
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// A variável header receberá o valor da autorização
		String header = request.getHeader("Authorization");
		// Se for diferente de nulo, e se iniciar com Bearer + espaço
		if(header != null && header.startsWith("Bearer ")) {
			// Passando do Bearer + espaço(a condição de cima), será feita a verificação do Token
			
			// A variável authToken receberá o valor do Token
			UsernamePasswordAuthenticationToken authToken = getAuthentication(header.substring(7));
			// Se o Token for diferente de nulo deu tudo certo
			if(authToken != null) {
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
		}
		chain.doFilter(request, response);
	}

	// Esse método será usado na verificação do método doFilterInternal
	private UsernamePasswordAuthenticationToken getAuthentication(String token) {
		// Condição para verificar se o Token é válido
		if(jwtUtil.tokenValido(token)) {
			// Variável username receberá o nosso Token vindo da classe JWTUtil
			String username = jwtUtil.getUsername(token);
			// Variável details receberá o nome do usuário vindo da variável username
			UserDetails details = userDetailsService.loadUserByUsername(username);
			// E retorna com a autenticação do Token
			return new UsernamePasswordAuthenticationToken(details.getUsername(), null, details.getAuthorities());
		}
		// Senão retorna nulo, e não será autenticado
		return null;
	}

}












