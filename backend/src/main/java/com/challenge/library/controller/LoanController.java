package com.challenge.library.controller;

import com.challenge.library.service.LoanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Loans", description = "Endpoints for managing book loans")
@RestController
@RequestMapping("/loans")
@RequiredArgsConstructor
public class LoanController {

    private final LoanService loanService;

    @Operation(summary = "Loan a book", description = "Loan an available book")
    @PostMapping("/{bookId}/rent/{userId}")
    @RolesAllowed({"ADMIN", "USER"})
    public ResponseEntity<String> rentBook(@PathVariable Long bookId, @PathVariable Long userId) {
        try {
            loanService.rentBook(bookId, userId);
            return ResponseEntity.ok("Operation performed successfully");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while performing the operation");
        }
    }

    @Operation(summary = "Return a book", description = "Return a loaned book")
    @PostMapping("/{bookId}/return/{userId}")
    @RolesAllowed({"ADMIN", "USER"})
    public ResponseEntity<String> returnBook(@PathVariable Long bookId, @PathVariable Long userId) {
        try {
            loanService.returnBook(bookId, userId);
            return ResponseEntity.ok("Operation performed successfully");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while performing the operation");
        }
    }
}
