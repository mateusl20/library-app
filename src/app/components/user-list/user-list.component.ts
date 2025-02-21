import { Component, OnInit } from '@angular/core';
import { UserService } from '../../services/user/user.service';
import { AuthService } from '../../services/auth/auth.service';
import { User } from '../../models/user.model';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent implements OnInit {
  users: User[] = [];
  currentUserId: number = 0;

  constructor(private userService: UserService, private authService: AuthService) {}

  ngOnInit() {
    this.loadUsers();
    const currentUser = this.authService.getCurrentUser();
    this.currentUserId = currentUser ? currentUser.id : 0;
  }

  loadUsers() {
    this.userService.getAllActiveUsers().subscribe(
      users => this.users = users.sort((a, b) => new Date(b.creationDate).getTime() - new Date(a.creationDate).getTime()),
      error => console.error('Error loading users', error)
    );
  }

  inactivateUser(userId: number) {
    if (userId === this.currentUserId) {
      alert('Não é possível inativar o próprio usuário.');
      return;
    }
    this.userService.inactivateUser(userId).subscribe(
      () => {
        alert('Operação realizada com sucesso');
        this.loadUsers();
      },
      error => alert('Ocorreu um erro ao realizar a operação')
    );
  }
}
