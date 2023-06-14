import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;

@TestMethodOrder(OrderAnnotation.class)
public class BookLibraryTest {

    private BookLibrary bookLibrary = new BookLibrary();
    private static final String TEST_TITLE = "Test Title";
    private static final String TEST_AUTHOR = "Test Author";
    private static final String TEST_BORROWER = "Test Borrower";

    @Test
    @Order(1)
    public void addBook() {
        Book book = bookLibrary.addBook(TEST_TITLE, TEST_AUTHOR);
        assertNotNull(book);
        assertEquals(TEST_TITLE, book.getTitle());
        assertEquals(TEST_AUTHOR, book.getAuthor());
    }

    @Test
    @Order(2)
    public void addBookNullTitleThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> bookLibrary.addBook(null, TEST_AUTHOR));
    }

    @Test
    @Order(3)
    public void addBookNullAuthorThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> bookLibrary.addBook(TEST_TITLE, null));
    }

    @Test
    @Order(4)
    public void addBookExistsThrowsException() {
        bookLibrary.addBook(TEST_TITLE, TEST_AUTHOR);
        assertThrows(IllegalStateException.class, () -> bookLibrary.addBook(TEST_TITLE, TEST_AUTHOR));
    }

    @Test
    @Order(5)
    public void getBook() {
        Book book = bookLibrary.addBook(TEST_TITLE, TEST_AUTHOR);
        Book actual = bookLibrary.getBook(TEST_TITLE);
        assertNotNull(actual);
        assertEquals(book, actual);
    }

    @Test
    @Order(6)
    public void getBookNullThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> bookLibrary.getBook(null));
    }

    @Test
    @Order(7)
    public void getBookDoesNotExistThrowsException() {
        assertThrows(IllegalStateException.class, () -> bookLibrary.getBook(TEST_TITLE));
    }

    @Test
    @Order(8)
    public void registerBorrower() {
        Borrower borrower = bookLibrary.registerBorrower(TEST_BORROWER);
        assertNotNull(borrower);
        assertEquals(TEST_BORROWER, borrower.getName());
    }

    @Test
    @Order(9)
    public void registerBorrowerNullNameThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> bookLibrary.registerBorrower(null));
    }

    @Test
    @Order(10)
    public void registerBorrowerExistsThrowsException() {
        bookLibrary.registerBorrower(TEST_BORROWER);
        assertThrows(IllegalStateException.class, () -> bookLibrary.registerBorrower(TEST_BORROWER));
    }

    @Test
    @Order(11)
    public void getBorrower() {
        Borrower borrower = bookLibrary.registerBorrower(TEST_BORROWER);
        Borrower actual = bookLibrary.getBorrower(TEST_BORROWER);
        assertNotNull(actual);
        assertEquals(borrower, actual);
    }

    @Test
    @Order(12)
    public void getBorrowerNullThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> bookLibrary.getBorrower(null));
    }

    @Test
    @Order(13)
    public void getBorrowerDoesNotExistThrowsException() {
        assertThrows(IllegalStateException.class, () -> bookLibrary.getBorrower(TEST_BORROWER));
    }

    @Test
    @Order(14)
    public void lendBook() {
        Book book = bookLibrary.addBook(TEST_TITLE, TEST_AUTHOR);
        Borrower borrower = bookLibrary.registerBorrower(TEST_BORROWER);
        bookLibrary.lendBook(TEST_TITLE, TEST_BORROWER);
        assertTrue(book.isBorrowed());
        assertEquals(1, borrower.getBorrowedBooks());
        assertEquals(borrower, book.getBorrower());
    }

    @Test
    @Order(15)
    public void lendBookAlreadyBorrowedThrowsException() {
        Book book = bookLibrary.addBook(TEST_TITLE, TEST_AUTHOR);
        Borrower borrower = bookLibrary.registerBorrower(TEST_BORROWER);
        bookLibrary.lendBook(TEST_TITLE, TEST_BORROWER);
        assertThrows(IllegalStateException.class, () -> bookLibrary.lendBook(TEST_TITLE, TEST_BORROWER));
    }

    @Test
    @Order(16)
    public void returnBook() {
        Book book = bookLibrary.addBook(TEST_TITLE, TEST_AUTHOR);
        Borrower borrower = bookLibrary.registerBorrower(TEST_BORROWER);
        bookLibrary.lendBook(TEST_TITLE, TEST_BORROWER);
        bookLibrary.returnBook(TEST_TITLE);
        assertFalse(book.isBorrowed());
        assertEquals(0, borrower.getBorrowedBooks());
        assertNull(book.getBorrower());
    }

    @Test
    @Order(17)
    public void returnBookNotBorrowedThrowsException() {
        Book book = bookLibrary.addBook(TEST_TITLE, TEST_AUTHOR);
        assertThrows(IllegalStateException.class, () -> bookLibrary.returnBook(TEST_TITLE));
    }
}