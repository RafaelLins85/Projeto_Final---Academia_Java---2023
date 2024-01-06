import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-nav',
  templateUrl: './nav.component.html',
  styleUrls: ['./nav.component.css']
})
export class NavComponent implements OnInit {

  // Construtor
  constructor(
    private router: Router,
    private authService: AuthService,
    private toast: ToastrService) { }

  // Abrindo direto na lista de tecnicos
  ngOnInit(): void {
    this.router.navigate(['tecnicos'])
  }

  // Redirecionando para fazer o Logout
  logout() {
    this.router.navigate(['login'])
    this.authService.logout();
    this.toast.info('Saindo com sucesso', 'Sair')
  }
}
