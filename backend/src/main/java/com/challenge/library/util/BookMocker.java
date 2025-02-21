package com.challenge.library.util;

import com.challenge.library.model.Book;
import com.challenge.library.model.enums.Category;

import java.util.ArrayList;
import java.util.Collection;

public class BookMocker {
    public static Collection<Book> mockBooks() {
        Collection<Book> mockedBooks = new ArrayList<>(2);

        Book book = new Book();
        book.setTitle("The Hitchhiker's Guide to the Galaxy");
        book.setCategory(Category.CIENCIAS);
        book.setCopies(5);
        book.setAvailable(true);
        mockedBooks.add(book);

        Book book2 = new Book();
        book2.setTitle("Clean Code");
        book2.setCategory(Category.INFORMATICA);
        book2.setCopies(10);
        book2.setAvailable(true);
        mockedBooks.add(book2);

        return mockedBooks;
    }
}
