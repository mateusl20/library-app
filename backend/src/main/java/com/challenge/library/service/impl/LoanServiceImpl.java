package com.challenge.library.service.impl;

import com.challenge.library.model.Book;
import com.challenge.library.model.Loan;
import com.challenge.library.model.User;
import com.challenge.library.repository.BookRepository;
import com.challenge.library.repository.LoanRepository;
import com.challenge.library.repository.UserRepository;
import com.challenge.library.service.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LoanServiceImpl implements LoanService {

    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    @Override
    public void rentBook(Long bookId, Long userId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        if (!book.isAvailable() || book.getCopies() == 0) {
            throw new IllegalStateException("Book is not available for rent.");
        }

        List<Loan> existingLoans = loanRepository.findByUserIdAndReturnDateIsNull(userId);
        if (existingLoans.stream().anyMatch(loan -> loan.getBook().getId().equals(bookId))) {
            throw new IllegalStateException("Book already rented by the user.");
        }

        Loan loan = new Loan();
        loan.setBook(book);
        loan.setUser(user);
        loan.setRentalDate(Instant.now());
        loanRepository.save(loan);

        book.setAvailable(false);
        book.setCopies(book.getCopies() - 1);
        bookRepository.save(book);
    }

    @Override
    public void returnBook(Long bookId, Long userId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));

        Loan loan = loanRepository.findByUserIdAndReturnDateIsNull(userId).stream()
                .filter(l -> l.getBook().getId().equals(bookId))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No active loan found for this user and book."));

        loan.setReturnDate(Instant.now());
        loanRepository.save(loan);

        book.setAvailable(true);
        book.setCopies(book.getCopies() + 1);
        bookRepository.save(book);
    }
}
