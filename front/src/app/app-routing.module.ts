import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from './auth/auth.guard';
import { ChamadoCreateComponent } from './components/chamado/chamado-create/chamado-create.component';
import { ChamadoListComponent } from './components/chamado/chamado-list/chamado-list.component';
import { ClienteListComponent } from './components/cliente/cliente-list/cliente-list.component';
import { ClienteCreateComponent } from './components/cliente/cliente-create/cliente-create.component';
import { LoginComponent } from './components/login/login.component';
import { NavComponent } from './components/nav/nav.component';
import { TecnicoCreateComponent } from './components/tecnico/tecnico-create/tecnico-create.component';
import { TecnicoDeleteComponent } from './components/tecnico/tecnico-delete/tecnico-delete.component';
import { TecnicoListComponent } from './components/tecnico/tecnico-list/tecnico-list.component';
import { TecnicoUpdateComponent } from './components/tecnico/tecnico-update/tecnico-update.component';

// Rotas configuradas. Elas serão renderizadas na nav.componente.html
const routes: Routes = [
  // Login não é filha de Nav, então fica fora dela. Assim, será a primeira tela a aparecer
  { path: 'login', component: LoginComponent },
  {                      // O AuthGuard, é quem libera ou não o acesso para as outras telas
    path: '', component: NavComponent, canActivate: [AuthGuard], children: [
      { path: 'tecnicos',            component:   TecnicoListComponent },
      { path: 'tecnicos/create',     component: TecnicoCreateComponent },
      { path: 'tecnicos/update/:id', component: TecnicoUpdateComponent },
      { path: 'tecnicos/delete/:id', component: TecnicoDeleteComponent },

      { path: 'clientes',            component:   ClienteListComponent },
      { path: 'clientes/create',     component: ClienteCreateComponent },

      { path: 'chamados',            component: ChamadoListComponent },
      { path: 'chamados/create',     component: ChamadoCreateComponent },

      
    ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
