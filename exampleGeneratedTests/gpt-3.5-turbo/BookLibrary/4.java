import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.Map;

public class BookLibraryTest {

    private BookLibrary bookLibrary;

    @BeforeEach
    void setUp() {
        bookLibrary = new BookLibrary();
    }

    @Test
    @DisplayName("Add book with null values should throw IllegalArgumentException")
    void testAddBookWithNullValues() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            bookLibrary.addBook(null, null);
        });
    }

    @Test
    @DisplayName("Add existing book should throw IllegalStateException")
    void testAddExistingBook() {
        String title = "Java for Dummies";
        String author = "John Doe";
        bookLibrary.addBook(title, author);
        Assertions.assertThrows(IllegalStateException.class, () -> {
            bookLibrary.addBook(title, author);
        });
    }

    @Test
    @DisplayName("Get book with null title should throw IllegalArgumentException")
    void testGetBookWithNullTitle() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            bookLibrary.getBook(null);
        });
    }

    @Test
    @DisplayName("Get non-existing book should throw IllegalStateException")
    void testGetNonExistingBook() {
        String title = "Java for Dummies";
        Assertions.assertThrows(IllegalStateException.class, () -> {
            bookLibrary.getBook(title);
        });
    }

    @Test
    @DisplayName("Register borrower with null name should throw IllegalArgumentException")
    void testRegisterBorrowerWithNullName() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            bookLibrary.registerBorrower(null);
        });
    }

    @Test
    @DisplayName("Register existing borrower should throw IllegalStateException")
    void testRegisterExistingBorrower() {
        String name = "John Doe";
        bookLibrary.registerBorrower(name);
        Assertions.assertThrows(IllegalStateException.class, () -> {
            bookLibrary.registerBorrower(name);
        });
    }

    @Test
    @DisplayName("Get borrower with null name should throw IllegalArgumentException")
    void testGetBorrowerWithNullName() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            bookLibrary.getBorrower(null);
        });
    }

    @Test
    @DisplayName("Get non-existing borrower should throw IllegalStateException")
    void testGetNonExistingBorrower() {
        String name = "John Doe";
        Assertions.assertThrows(IllegalStateException.class, () -> {
            bookLibrary.getBorrower(name);
        });
    }

    @Test
    @DisplayName("Lend book that is already borrowed should throw IllegalStateException")
    void testLendAlreadyBorrowedBook() {
        String title = "Java for Dummies";
        String author = "John Doe";
        String borrowerName1 = "Jane Doe";
        String borrowerName2 = "Jack Doe";
        Book book = bookLibrary.addBook(title, author);
        Borrower borrower1 = bookLibrary.registerBorrower(borrowerName1);
        Borrower borrower2 = bookLibrary.registerBorrower(borrowerName2);
        borrower1.borrowBook(book);
        book.setBorrower(borrower1);
        Assertions.assertThrows(IllegalStateException.class, () -> {
            bookLibrary.lendBook(title, borrowerName2);
        });
    }

    @Test
    @DisplayName("Lend book to borrower")
    void testLendBook() {
        String title = "Java for Dummies";
        String author = "John Doe";
        String borrowerName = "Jane Doe";
        Book book = bookLibrary.addBook(title, author);
        Borrower borrower = bookLibrary.registerBorrower(borrowerName);
        bookLibrary.lendBook(title, borrowerName);
        Assertions.assertTrue(book.isBorrowed());
        Assertions.assertEquals(borrower, book.getBorrower());
        Assertions.assertEquals(1, borrower.getBorrowedBooks());
    }

    @Test
    @DisplayName("Return non-existing book should throw IllegalStateException")
    void testReturnNonExistingBook() {
        String title = "Java for Dummies";
        Assertions.assertThrows(IllegalStateException.class, () -> {
            bookLibrary.returnBook(title);
        });
    }

    @Test
    @DisplayName("Return book that is not borrowed should throw IllegalStateException")
    void testReturnNotBorrowedBook() {
        String title = "Java for Dummies";
        String author = "John Doe";
        Book book = bookLibrary.addBook(title, author);
        Assertions.assertThrows(IllegalStateException.class, () -> {
            bookLibrary.returnBook(title);
        });
    }

    @Test
    @DisplayName("Return borrowed book")
    void testReturnBorrowedBook() {
        String title = "Java for Dummies";
        String author = "John Doe";
        String borrowerName = "Jane Doe";
        Book book = bookLibrary.addBook(title, author);
        Borrower borrower = bookLibrary.registerBorrower(borrowerName);
        borrower.borrowBook(book);
        book.setBorrower(borrower);
        bookLibrary.returnBook(title);
        Assertions.assertFalse(book.isBorrowed());
        Assertions.assertNull(book.getBorrower());
        Assertions.assertEquals(0, borrower.getBorrowedBooks());
    }
}