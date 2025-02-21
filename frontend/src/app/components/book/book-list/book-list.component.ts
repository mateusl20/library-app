import { Component, OnInit } from '@angular/core';
import { BookService } from '../../../services/book/book.service';
import { LoanService } from '../../../services/loan/loan.service';
import { AuthService } from '../../../services/auth/auth.service';
import { Book } from '../../../models/book.model';
import { MatDialog } from '@angular/material/dialog';
import { AddBookModalComponent } from '../add-book-modal/add-book-modal.component';

@Component({
  selector: 'app-book-list',
  templateUrl: './book-list.component.html',
  styleUrls: ['./book-list.component.css']
})
export class BookListComponent implements OnInit {
  books: Book[] = [];
  isAdmin: boolean = false;
  isAuthenticated: boolean = false;
  currentUserId: number = 0;

  constructor(
    private bookService: BookService,
    private loanService: LoanService,
    private authService: AuthService,
    public dialog: MatDialog
  ) {}

  ngOnInit() {
    this.loadBooks();
    this.isAuthenticated = this.authService.isAuthenticated();
    this.isAdmin = this.authService.hasRole('ADMIN');
    const currentUser = this.authService.getCurrentUser();
    this.currentUserId = currentUser ? currentUser.id : 0;
  }

  loadBooks() {
    this.bookService.getAllBooks().subscribe(
      books => this.books = books.sort((a, b) => (a.available === b.available) ? 0 : a.available ? -1 : 1),
      error => console.error('Error loading books', error)
    );
  }

  openAddBookModal() {
    const dialogRef = this.dialog.open(AddBookModalComponent, {
      width: '400px',
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.loadBooks();
      }
    });
  }

  rentBook(bookId: number) {
    this.loanService.rentBook(bookId, this.currentUserId).subscribe(
      () => {
        alert('Operação realizada com sucesso');
        this.loadBooks();
      },
      error => {
        if (error.status === 400 && error.error.includes('Livro já alugado pelo Utilizador')) {
          alert('Livro já alugado pelo Utilizador');
        } else {
          alert('Ocorreu um erro ao realizar a operação');
        }
      }
    );
  }

  returnBook(bookId: number) {
    this.loanService.returnBook(bookId, this.currentUserId).subscribe(
      () => {
        alert('Operação realizada com sucesso');
        this.loadBooks();
      },
      error => alert('Ocorreu um erro ao realizar a operação')
    );
  }

  removeBook(bookId: number) {
    if (confirm('Tem certeza que deseja remover o Livro?')) {
      this.bookService.removeBook(bookId).subscribe(
        () => {
          alert('Operação realizada com sucesso');
          this.loadBooks();
        },
        error => {
          if (error.status === 400 && error.error.includes('Não foi possível Remover Livro pois o mesmo encontra-se alugado')) {
            alert('Não foi possível Remover Livro pois o mesmo encontra-se alugado');
          } else {
            alert('Ocorreu um erro ao realizar a operação');
          }
        }
      );
    }
  }
}
