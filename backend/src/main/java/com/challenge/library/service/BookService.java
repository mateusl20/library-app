package com.challenge.library.service;

import com.challenge.library.model.Book;

import java.util.List;

public interface BookService {
    List<Book> getAllBooks();
    Book addBook(Book book);
    void removeBook(Long bookId);
}
