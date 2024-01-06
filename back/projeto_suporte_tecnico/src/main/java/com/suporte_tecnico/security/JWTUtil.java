package com.suporte_tecnico.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTUtil {
	// Esse valor foi pego do properties
	@Value("${jwt.expiration}")
	private Long expiration;
	
	// Esse valor foi pego do properties
	@Value("${jwt.secret}")
	private String secret;

	// Método para gerar o Token
	public String generateToken(String email) {
		return Jwts.builder()
				.setSubject(email)
				.setExpiration(new Date(System.currentTimeMillis() + expiration))
				.signWith(SignatureAlgorithm.HS512, secret.getBytes())
				.compact();
	}

	// Método para validar o Token
	public boolean tokenValido(String token) {
		// Variável claims que receberá o Token
		Claims claims = getClaims(token);
		// Se for diferente de nulo, então pegou o valor
		if(claims != null) {
			// Variável username para receber o nome do usuário
			String username = claims.getSubject();
			// Variável expirationDate para receber o tempo de expiração do Token
			Date expirationDate = claims.getExpiration();
			// Variável now para receber o tempo atual em milissegundos
			Date now = new Date(System.currentTimeMillis());
			
			// Se o nome do usuário for diferente de nulo, e o momento atual for antes da data de expiração, tá tudo certo com o Tokem
			if(username != null && expirationDate != null && now.before(expirationDate)) {
				return true;
			}
		}
		// Senão for retorna falso
		return false;
	}

	// Método que faz parte da validação do Token
	// Pode lançar uma exceção caso dê errado
	private Claims getClaims(String token) {
		try {
			return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();
		} catch (Exception e) {
			return null;
		}
	}

	// Método para validar o nome do usuário
	public String getUsername(String token) {
		// Variável claims que receberá o token
		Claims claims = getClaims(token);
		// Se claims for diferente de nulo, aí será retornado o username do Token
		if(claims != null) {
			return claims.getSubject();
		}
		return null;
	}
}
