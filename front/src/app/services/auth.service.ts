import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { JwtHelperService } from '@auth0/angular-jwt';
import { API_CONFIG } from '../config/api.config';
import { Credenciais } from '../models/credenciais';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  // Instância
  jwtService: JwtHelperService = new JwtHelperService();

  // Construtor
  // Fazendo requisições Http
  constructor(private http: HttpClient) { }

  authenticate(creds: Credenciais) {
    return this.http.post(`${API_CONFIG.baseUrl}/login`, creds, {
      observe: 'response',
      responseType: 'text'
    })
  }

  // Se logar com sucesso
  successfulLogin(authToken: string) {
    localStorage.setItem('token', authToken);
  }

  // Se o token estiver expirado, será retornado verdadeiro, caso contrário retorna falso
  isAuthenticated() {
    let token = localStorage.getItem('token')
    if(token != null) {
      return !this.jwtService.isTokenExpired(token)
    }
    return false
  }

  // Método para limpar os logins
  logout() {
    localStorage.clear();
  }
}
