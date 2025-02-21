package com.challenge.library.service.impl;

import com.challenge.library.model.Book;
import com.challenge.library.model.Loan;
import com.challenge.library.model.User;
import com.challenge.library.repository.BookRepository;
import com.challenge.library.repository.LoanRepository;
import com.challenge.library.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LoanServiceImplTest {

    @Mock
    private LoanRepository loanRepository;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private LoanServiceImpl loanService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void rentBook_successful() {

        Long bookId = 1L;
        Long userId = 1L;
        Book book = new Book();
        book.setId(bookId);
        book.setAvailable(true);
        User user = new User();
        user.setId(userId);

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(loanRepository.findByUserIdAndReturnDateIsNull(userId)).thenReturn(Collections.emptyList());
        when(loanRepository.save(any(com.challenge.library.model.Loan.class))).thenAnswer(i -> i.getArguments()[0]);

        loanService.rentBook(bookId, userId);

        assertFalse(book.isAvailable());
        verify(bookRepository, times(1)).findById(bookId);
        verify(userRepository, times(1)).findById(userId);
        verify(loanRepository, times(1)).findByUserIdAndReturnDateIsNull(userId);
        verify(loanRepository, times(1)).save(any(com.challenge.library.model.Loan.class));
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void rentBook_bookNotAvailable() {

        Long bookId = 1L;
        Long userId = 1L;

        Book book = new Book();
        book.setId(bookId);
        book.setAvailable(false);

        User user = new User();
        user.setId(userId);

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        assertThrows(IllegalStateException.class, () -> loanService.rentBook(bookId, userId));

        verify(bookRepository, times(1)).findById(bookId);
        verify(userRepository, times(1)).findById(userId);

        verify(loanRepository, never()).findByUserIdAndReturnDateIsNull(anyLong());
        verify(loanRepository, never()).save(any(com.challenge.library.model.Loan.class));
        verify(bookRepository, never()).save(any(Book.class));
    }

    @Test
    void returnBook_successful() {
        Long bookId = 1L;
        Long userId = 1L;
        Book book = new Book();
        book.setId(bookId);
        book.setAvailable(false);
        User user = new User();
        user.setId(userId);
        Loan loan = new Loan();
        loan.setBook(book);
        loan.setUser(user);

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(loanRepository.findByUserIdAndReturnDateIsNull(userId)).thenReturn(Collections.singletonList(loan));
        when(loanRepository.save(any(Loan.class))).thenAnswer(i -> i.getArguments()[0]);

        loanService.returnBook(bookId, userId);

        assertTrue(book.isAvailable());
        verify(bookRepository, times(1)).findById(bookId);
        verify(userRepository, times(1)).findById(userId);
        verify(loanRepository, times(1)).findByUserIdAndReturnDateIsNull(userId);
        verify(loanRepository, times(1)).save(loan);
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void returnBook_noActiveLoan() {

        Long bookId = 1L;
        Long userId = 1L;

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(new Book()));
        when(userRepository.findById(userId)).thenReturn(Optional.of(new User()));
        when(loanRepository.findByUserIdAndReturnDateIsNull(userId)).thenReturn(Collections.emptyList());

        assertThrows(IllegalStateException.class, () -> loanService.returnBook(bookId, userId));

        verify(bookRepository, times(1)).findById(bookId);
        verify(userRepository, times(1)).findById(userId);
        verify(loanRepository, times(1)).findByUserIdAndReturnDateIsNull(userId);
        verify(loanRepository, never()).save(any(Loan.class));
        verify(bookRepository, never()).save(any(Book.class));
    }
}
