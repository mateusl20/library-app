package com.challenge.library.service.impl;

import com.challenge.library.model.Book;
import com.challenge.library.repository.BookRepository;
import com.challenge.library.repository.LoanRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private LoanRepository loanRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllBooks() {

        Book book1 = new Book();
        book1.setAvailable(true);
        Book book2 = new Book();
        book2.setAvailable(false);

        List<Book> books = Arrays.asList(book1, book2);

        when(bookRepository.findByOrderByAvailableDesc()).thenReturn(books);

        List<Book> result = bookService.getAllBooks();

        assertEquals(2, result.size());
        verify(bookRepository, times(1)).findByOrderByAvailableDesc();
    }

    @Test
    void addBook() {
        Book book = new Book();
        when(bookRepository.save(book)).thenReturn(book);

        Book result = bookService.addBook(book);

        assertEquals(book, result);
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void removeBook_successful() {

        Long bookId = 1L;
        when(loanRepository.findByBookIdAndReturnDateIsNull(bookId)).thenReturn(Arrays.asList());

        bookService.removeBook(bookId);

        verify(loanRepository, times(1)).findByBookIdAndReturnDateIsNull(bookId);
        verify(bookRepository, times(1)).deleteById(bookId);
    }

    @Test
    void removeBook_hasActiveLoans() {
        Long bookId = 1L;
        when(loanRepository.findByBookIdAndReturnDateIsNull(bookId)).thenReturn(Arrays.asList(new com.challenge.library.model.Loan()));

        assertThrows(IllegalStateException.class, () -> bookService.removeBook(bookId));

        verify(loanRepository, times(1)).findByBookIdAndReturnDateIsNull(bookId);
        verify(bookRepository, never()).deleteById(anyLong());
    }
}
