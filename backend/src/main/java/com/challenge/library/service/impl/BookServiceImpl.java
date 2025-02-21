package com.challenge.library.service.impl;

import com.challenge.library.model.Book;
import com.challenge.library.repository.BookRepository;
import com.challenge.library.repository.LoanRepository;
import com.challenge.library.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final LoanRepository loanRepository;

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findByOrderByAvailableDesc();
    }

    @Override
    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public void removeBook(Long bookId) {
        if (!loanRepository.findByBookIdAndReturnDateIsNull(bookId).isEmpty()) {
            throw new IllegalStateException("Book has active loans. Cannot remove.");
        }
        bookRepository.deleteById(bookId);
    }
}
