package com.challenge.library.service;

import com.challenge.library.model.Loan;

public interface LoanService {
    void rentBook(Long bookId, Long userId);
    void returnBook(Long bookId, Long userId);
}
