package org.example;

import java.util.HashMap;
import java.util.Map;

public class BookLibrary {

    private final Map<String, Book> books = new HashMap<>();
    private final Map<String, Borrower> borrowers = new HashMap<>();

    public Book addBook(String title, String author) {
        if (title == null || author == null) {
            throw new IllegalArgumentException("Title and author must not be null");
        }

        if (books.containsKey(title)) {
            throw new IllegalStateException("Book already exists");
        }

        Book book = new Book(title, author);
        books.put(title, book);
        return book;
    }

    public Book getBook(String title) {
        if (title == null) {
            throw new IllegalArgumentException("Title must not be null");
        }

        Book book = books.get(title);
        if (book == null) {
            throw new IllegalStateException("Book does not exist");
        }

        return book;
    }

    public Borrower registerBorrower(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Name must not be null");
        }

        if (borrowers.containsKey(name)) {
            throw new IllegalStateException("Borrower already registered");
        }

        Borrower borrower = new Borrower(name);
        borrowers.put(name, borrower);
        return borrower;
    }

    public Borrower getBorrower(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Name must not be null");
        }

        Borrower borrower = borrowers.get(name);
        if (borrower == null) {
            throw new IllegalStateException("Borrower does not exist");
        }

        return borrower;
    }

    public void lendBook(String title, String name) {
        Book book = getBook(title);
        Borrower borrower = getBorrower(name);

        if (book.isBorrowed()) {
            throw new IllegalStateException("Book is already borrowed");
        }

        borrower.borrowBook(book);
        book.setBorrower(borrower);
    }

    public void returnBook(String title) {
        Book book = getBook(title);

        if (!book.isBorrowed()) {
            throw new IllegalStateException("Book is not borrowed");
        }

        book.getBorrower().returnBook(book);
        book.setBorrower(null);
    }
}

class Book {
    private final String title;
    private final String author;
    private Borrower borrower;

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public boolean isBorrowed() {
        return borrower != null;
    }

    public Borrower getBorrower() {
        return borrower;
    }

    public void setBorrower(Borrower borrower) {
        this.borrower = borrower;
    }
}

class Borrower {
    private final String name;
    private int borrowedBooks = 0;

    public Borrower(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getBorrowedBooks() {
        return borrowedBooks;
    }

    public void borrowBook(Book book) {
        borrowedBooks++;
    }

    public void returnBook(Book book) {
        borrowedBooks--;
    }
}
