import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { UserListComponent } from './components/user-list/user-list.component';
import { BookListComponent } from './components/book/book-list/book-list.component';
import { LoginComponent } from './components/login/login.component';
import { roleGuard } from './guards/role.guard';

const routes: Routes = [
  { path: '', redirectTo: '/home', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'home', component: HomeComponent, canActivate: [roleGuard], data: { allowAnonymous: true } },
  { path: 'users', component: UserListComponent, canActivate: [roleGuard], data: { requiredRole: 'ADMIN' } },
  { path: 'books', component: BookListComponent, canActivate: [roleGuard], data: { allowAnonymous: true } },
  { path: '**', redirectTo: '/home' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
