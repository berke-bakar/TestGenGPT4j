package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class BookLibraryTest {

    private BookLibrary bookLibrary;

    @BeforeEach
    void setUp() {
        bookLibrary = new BookLibrary();
        bookLibrary.addBook("The Lord of the Rings", "J.R.R. Tolkien");
        bookLibrary.registerBorrower("John Doe");
    }

    @Nested
    @DisplayName("addBook method")
    class AddBook {

        @Test
        @DisplayName("should add a book to the library")
        void shouldAddBook() {
            bookLibrary.addBook("The Hobbit", "J.R.R. Tolkien");
            Assertions.assertTrue(bookLibrary.getBook("The Hobbit").getTitle().equals("The Hobbit"));
        }

        @Test
        @DisplayName("should throw an exception if title or author is null")
        void shouldThrowExceptionIfTitleOrAuthorIsNull() {
            Assertions.assertThrows(IllegalArgumentException.class, () -> bookLibrary.addBook(null, "J.R.R. Tolkien"));
            Assertions.assertThrows(IllegalArgumentException.class, () -> bookLibrary.addBook("The Hobbit", null));
        }

        @Test
        @DisplayName("should throw an exception if book already exists in the library")
        void shouldThrowExceptionIfBookAlreadyExists() {
            Assertions.assertThrows(IllegalStateException.class, () -> bookLibrary.addBook("The Lord of the Rings", "J.R.R. Tolkien"));
        }
    }

    @Nested
    @DisplayName("getBook method")
    class GetBook {

        @Test
        @DisplayName("should return the book with the given title")
        void shouldReturnBookWithTitle() {
            Assertions.assertTrue(bookLibrary.getBook("The Lord of the Rings").getTitle().equals("The Lord of the Rings"));
        }

        @Test
        @DisplayName("should throw an exception if title is null")
        void shouldThrowExceptionIfTitleIsNull() {
            Assertions.assertThrows(IllegalArgumentException.class, () -> bookLibrary.getBook(null));
        }

        @Test
        @DisplayName("should throw an exception if book does not exist in the library")
        void shouldThrowExceptionIfBookDoesNotExist() {
            Assertions.assertThrows(IllegalStateException.class, () -> bookLibrary.getBook("The Hobbit"));
        }
    }

    @Nested
    @DisplayName("registerBorrower method")
    class RegisterBorrower {

        @Test
        @DisplayName("should register a new borrower")
        void shouldRegisterBorrower() {
            bookLibrary.registerBorrower("Jane Doe");
            Assertions.assertTrue(bookLibrary.getBorrower("Jane Doe").getName().equals("Jane Doe"));
        }

        @Test
        @DisplayName("should throw an exception if name is null")
        void shouldThrowExceptionIfNameIsNull() {
            Assertions.assertThrows(IllegalArgumentException.class, () -> bookLibrary.registerBorrower(null));
        }

        @Test
        @DisplayName("should throw an exception if borrower is already registered")
        void shouldThrowExceptionIfBorrowerAlreadyRegistered() {
            Assertions.assertThrows(IllegalStateException.class, () -> bookLibrary.registerBorrower("John Doe"));
        }
    }

    @Nested
    @DisplayName("getBorrower method")
    class GetBorrower {

        @Test
        @DisplayName("should return the borrower with the given name")
        void shouldReturnBorrowerWithName() {
            Assertions.assertTrue(bookLibrary.getBorrower("John Doe").getName().equals("John Doe"));
        }

        @Test
        @DisplayName("should throw an exception if name is null")
        void shouldThrowExceptionIfNameIsNull() {
            Assertions.assertThrows(IllegalArgumentException.class, () -> bookLibrary.getBorrower(null));
        }

        @Test
        @DisplayName("should throw an exception if borrower does not exist")
        void shouldThrowExceptionIfBorrowerDoesNotExist() {
            Assertions.assertThrows(IllegalStateException.class, () -> bookLibrary.getBorrower("Jane Doe"));
        }
    }

    @Nested
    @DisplayName("lendBook method")
    class LendBook {

        @Test
        @DisplayName("should lend a book to a borrower")
        void shouldLendBook() {
            bookLibrary.lendBook("The Lord of the Rings", "John Doe");
            Assertions.assertEquals(bookLibrary.getBook("The Lord of the Rings").getBorrower().getName(), "John Doe");
            Assertions.assertEquals(bookLibrary.getBorrower("John Doe").getBorrowedBooks(), 1);
        }

        @Test
        @DisplayName("should throw an exception if book is already borrowed")
        void shouldThrowExceptionIfBookIsAlreadyBorrowed() {
            bookLibrary.lendBook("The Lord of the Rings", "John Doe");
            Assertions.assertThrows(IllegalStateException.class, () -> bookLibrary.lendBook("The Lord of the Rings", "Jane Doe"));
        }
    }

    @Nested
    @DisplayName("returnBook method")
    class ReturnBook {

        @Test
        @DisplayName("should return a borrowed book")
        void shouldReturnBook() {
            bookLibrary.lendBook("The Lord of the Rings", "John Doe");
            bookLibrary.returnBook("The Lord of the Rings");
            Assertions.assertNull(bookLibrary.getBook("The Lord of the Rings").getBorrower());
            Assertions.assertEquals(bookLibrary.getBorrower("John Doe").getBorrowedBooks(), 0);
        }

        @Test
        @DisplayName("should throw an exception if book is not borrowed")
        void shouldThrowExceptionIfBookIsNotBorrowed() {
            Assertions.assertThrows(IllegalStateException.class, () -> bookLibrary.returnBook("The Lord of the Rings"));
        }
    }
}