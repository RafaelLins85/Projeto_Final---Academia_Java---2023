import { Component, OnInit } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Credenciais } from 'src/app/models/credenciais';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  // Variável creds do tipo Credenciais
  creds: Credenciais = {
    email: '',
    senha: ''
  }

  // Validando os valores
  email = new FormControl(null, Validators.email);
  senha = new FormControl(null, Validators.minLength(3));

  // Construtor
  constructor(
    private toast: ToastrService,
    private service: AuthService,
    private router: Router) { }

  ngOnInit(): void { }

  // O Toastr será passado no canto diteito da tela
  // Será autorizado ou não a entrada do usuário
  logar() {
    this.service.authenticate(this.creds).subscribe(resposta => {
      this.service.successfulLogin(resposta.headers.get('Authorization').substring(7));
      this.router.navigate([''])
    }, () => {
      this.toast.error('Usuário e/ou senha inválidos');
    })
  }

  // Validando os valores
  validaCampos(): boolean {
    return this.email.valid && this.senha.valid
  }

}
