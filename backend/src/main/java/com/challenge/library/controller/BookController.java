package com.challenge.library.controller;

import com.challenge.library.model.Book;
import com.challenge.library.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Books", description = "Endpoints for managing books")
@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @Operation(summary = "Get all books", description = "Retrieve a list of all books")
    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }


    @Operation(summary = "Add a book", description = "Add a book on the database")
    @PostMapping("/add")
    @RolesAllowed("ADMIN")
    public ResponseEntity<Book> addBook(@Valid @RequestBody Book book) {
        Book savedBook = bookService.addBook(book);
        return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
    }

    @Operation(summary = "Delete a book", description = "Remove a book by Id")
    @DeleteMapping("/{bookId}/remove")
    @RolesAllowed("ADMIN")
    public ResponseEntity<String> removeBook(@PathVariable Long bookId) {
        try {
            bookService.removeBook(bookId);
            return ResponseEntity.ok("Operation performed successfully");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("It was not possible to Remove Book as it is rented");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while performing the operation");
        }
    }
}
