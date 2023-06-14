import org.example.Book;
import org.example.BookLibrary;
import org.example.Borrower;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BookLibraryTest {

    @Test
    public void testAddBook() {
        BookLibrary library = new BookLibrary();

        // Test case to check title and author must not be null
        assertThrows(IllegalArgumentException.class, ()-> library.addBook(null, null));

        // Test case to check book exists
        library.addBook("Example Book", "Example Author");
        assertThrows(IllegalStateException.class, ()-> library.addBook("Example Book", "Example Author"));

        // Test case to check book added
        Book book = library.addBook("Example Book 2", "Example Author 2");
        assertNotNull(book);
    }

    @Test
    public void testGetBook() {
        BookLibrary library = new BookLibrary();
        library.addBook("Example Book", "Example Author");

        // Test case to check title must not be null
        assertThrows(IllegalArgumentException.class, ()-> library.getBook(null));

        // Test case to check book exists
        assertThrows(IllegalStateException.class, ()-> library.getBook("Example Book 3"));

        // Test case to check book retrieved
        Book book = library.getBook("Example Book");
        assertNotNull(book);
    }

    @Test
    public void testRegisterBorrower() {
        BookLibrary library = new BookLibrary();

        // Test case to check name must not be null
        assertThrows(IllegalArgumentException.class, ()-> library.registerBorrower(null));

        // Test case to check borrower registered
        Borrower borrower = library.registerBorrower("Example Borrower");
        assertNotNull(borrower);
    }

    @Test
    public void testGetBorrower() {
        BookLibrary library = new BookLibrary();
        library.registerBorrower("Example Borrower");

        // Test case to check name must not be null
        assertThrows(IllegalArgumentException.class, ()-> library.getBorrower(null));

        // Test case to check borrower exists
        assertThrows(IllegalStateException.class, ()-> library.getBorrower("Example Borrower 2"));

        // Test case to check borrower retrieved
        Borrower borrower = library.getBorrower("Example Borrower");
        assertNotNull(borrower);
    }

    @Test
    public void testLendBook() {
        BookLibrary library = new BookLibrary();
        library.addBook("Example Book", "Example Author");
        library.registerBorrower("Example Borrower");

        // Test case to check book is already borrowed
        library.lendBook("Example Book", "Example Borrower");
        assertThrows(IllegalStateException.class, ()-> library.lendBook("Example Book", "Example Borrower"));

        // Test case to check book is borrowed
        Book book = library.getBook("Example Book");
        assertTrue(book.isBorrowed());
    }

    @Test
    public void testReturnBook() {
        BookLibrary library = new BookLibrary();
        library.addBook("Example Book", "Example Author");
        library.registerBorrower("Example Borrower");
        library.lendBook("Example Book", "Example Borrower");

        // Test case to check book is not borrowed
        library.returnBook("Example Book");
        assertThrows(IllegalStateException.class, ()-> library.returnBook("Example Book"));

        // Test case to check book is not borrowed
        Book book = library.getBook("Example Book");
        assertEquals(null, book.getBorrower());
    }
}