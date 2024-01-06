package com.suporte_tecnico.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.suporte_tecnico.security.JWTAuthenticationFilter;
import com.suporte_tecnico.security.JWTAuthorizationFilter;
import com.suporte_tecnico.security.JWTUtil;


@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
// Com a extensão da classe WebSecurityConfigurerAdapter posso usar os métodos dela aqui.
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	// Injeção de dependência
	@Autowired
	private JWTUtil jwtUtil;
	@Autowired
	private UserDetailsService userDetailsService;

	// Método cofigure que servirá para proteção e liberação
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.cors().and().csrf().disable();		
		// Registrando filtros de autenticação e autorização do JWT
		http.addFilter(new JWTAuthenticationFilter(authenticationManager(), jwtUtil));
		http.addFilter(new JWTAuthorizationFilter(authenticationManager(), jwtUtil, userDetailsService));

		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}
	
	// Método cofigure que servirá para proteção e liberação
	// Autorizando o SWAGGER
    protected void config(HttpSecurity http) throws Exception {
        http.authorizeRequests()		
                .antMatchers("/swagger-ui.html", "/webjars/**", "/v2/api-docs")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }

    // Método cofigure que servirá para proteção e liberação
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// Configurando a autenticação
		// auth recebe userDetailsService e também o método bCryptPasswordEncoder()
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
	}

	// Método corsConfigurationSource do tipo CorsConfigurationSource
	// Liberando as requisições que serão feitas pro back-end
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		// Instância da configuração do cors, já aplicando uma permissão para valores padrão
		CorsConfiguration configuration = new CorsConfiguration().applyPermitDefaultValues();
		// Setando todos os métodos usando um Arrays.asList
		configuration.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE", "OPTIONS"));
		// Variável source do tipo UrlBasedCorsConfigurationSource já sendo inicializada
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		// Usando o método registerCorsConfiguration, através da variável source para receber todas as requisições com as configurações
		source.registerCorsConfiguration("/**", configuration);
		// Dando retorno pela variável
		return source;
	}
    
	// Método BCryptPasswordEncoder
	// Criptografando a senha, para não ficar visível no banco
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
