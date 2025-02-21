import { Component } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { BookService } from '../../../services/book/book.service';

@Component({
  selector: 'app-add-book-modal',
  templateUrl: './add-book-modal.component.html',
  styleUrls: ['./add-book-modal.component.css']
})
export class AddBookModalComponent {
  book: any = { title: '', category: null, copies: 1 };

  constructor(
    private dialogRef: MatDialogRef<AddBookModalComponent>,
    private bookService: BookService
  ) {}

  onSubmit() {
    if (this.book.title && this.book.category && this.book.copies) {
      this.bookService.addBook(this.book).subscribe(
        result => {
          alert('Operação realizada com sucesso');
          this.dialogRef.close(true);
        },
        error => alert('Ocorreu um erro ao realizar a operação')
      );
    }
  }

  onCancel() {
    this.dialogRef.close();
  }
}
