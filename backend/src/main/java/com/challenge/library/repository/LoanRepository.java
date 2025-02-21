package com.challenge.library.repository;

import com.challenge.library.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
    List<Loan> findByUserIdAndReturnDateIsNull(Long userId);
    List<Loan> findByBookIdAndReturnDateIsNull(Long bookId);
}
