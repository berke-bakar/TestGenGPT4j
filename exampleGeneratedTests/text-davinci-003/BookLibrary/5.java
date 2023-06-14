import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.example.Book;
import org.example.BookLibrary;
import org.example.Borrower;
import org.junit.jupiter.api.Test;

public class BookLibraryTest {

    // Test addBook()

    @Test
    void testAddBook_nullTitleAndAuthor() {
        BookLibrary bookLibrary = new BookLibrary();

        assertThrows(IllegalArgumentException.class, () -> bookLibrary.addBook(null, null));
    }

    @Test
    void testAddBook_titleExists() {
        BookLibrary bookLibrary = new BookLibrary();
        bookLibrary.addBook("Book 1", "Author 1");

        assertThrows(IllegalStateException.class, () -> bookLibrary.addBook("Book 1", "Author 1"));
    }

    // Test getBook()

    @Test
    void testGetBook_nullTitle() {
        BookLibrary bookLibrary = new BookLibrary();

        assertThrows(IllegalArgumentException.class, () -> bookLibrary.getBook(null));
    }

    @Test
    void testGetBook_bookDoesNotExist() {
        BookLibrary bookLibrary = new BookLibrary();
        bookLibrary.addBook("Book 1", "Author 1");

        assertThrows(IllegalStateException.class, () -> bookLibrary.getBook("Book 2"));
    }

    // Test registerBorrower()

    @Test
    void testRegisterBorrower_nullName() {
        BookLibrary bookLibrary = new BookLibrary();

        assertThrows(IllegalArgumentException.class, () -> bookLibrary.registerBorrower(null));
    }

    @Test
    void testRegisterBorrower_borrowerExists() {
        BookLibrary bookLibrary = new BookLibrary();
        bookLibrary.registerBorrower("John Doe");

        assertThrows(IllegalStateException.class, () -> bookLibrary.registerBorrower("John Doe"));
    }

    // Test getBorrower()

    @Test
    void testGetBorrower_nullName() {
        BookLibrary bookLibrary = new BookLibrary();

        assertThrows(IllegalArgumentException.class, () -> bookLibrary.getBorrower(null));
    }

    @Test
    void testGetBorrower_borrowerDoesNotExist() {
        BookLibrary bookLibrary = new BookLibrary();
        bookLibrary.registerBorrower("John Doe");

        assertThrows(IllegalStateException.class, () -> bookLibrary.getBorrower("Jane Doe"));
    }

    // Test lendBook()

    @Test
    void testLendBook_nullTitleAndName() {
        BookLibrary bookLibrary = new BookLibrary();

        assertThrows(IllegalArgumentException.class, () -> bookLibrary.lendBook(null, null));
    }

    @Test
    void testLendBook_bookAlreadyBorrowed() {
        BookLibrary bookLibrary = new BookLibrary();
        bookLibrary.addBook("Book 1", "Author 1");
        bookLibrary.registerBorrower("John Doe");
        bookLibrary.lendBook("Book 1", "John Doe");

        assertThrows(IllegalStateException.class, () -> bookLibrary.lendBook("Book 1", "John Doe"));
    }

    // Test returnBook()

    @Test
    void testReturnBook_nullTitle() {
        BookLibrary bookLibrary = new BookLibrary();

        assertThrows(IllegalArgumentException.class, () -> bookLibrary.returnBook(null));
    }

    @Test
    void testReturnBook_bookNotBorrowed() {
        BookLibrary bookLibrary = new BookLibrary();
        bookLibrary.addBook("Book 1", "Author 1");

        assertThrows(IllegalStateException.class, () -> bookLibrary.returnBook("Book 1"));
    }

}