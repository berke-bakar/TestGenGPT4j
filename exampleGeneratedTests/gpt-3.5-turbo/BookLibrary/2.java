import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BookLibraryTest {

    private BookLibrary library;

    @BeforeEach
    public void setUp() {
        library = new BookLibrary();
    }

    @Test
    @DisplayName("Adding book with non-null title and author adds the book successfully")
    public void testAddBookWithNonNullTitleAndAuthor() {
        Book book = library.addBook("Java for Dummies", "Barry Burd");
        assertNotNull(book);
        assertEquals("Java for Dummies", book.getTitle());
        assertEquals("Barry Burd", book.getAuthor());
    }

    @Test
    @DisplayName("Adding book with null title throws IllegalArgumentException")
    public void testAddBookWithNullTitle() {
        assertThrows(IllegalArgumentException.class, () -> library.addBook(null, "Barry Burd"));
    }

    @Test
    @DisplayName("Adding book with null author throws IllegalArgumentException")
    public void testAddBookWithNullAuthor() {
        assertThrows(IllegalArgumentException.class, () -> library.addBook("Java for Dummies", null));
    }

    @Test
    @DisplayName("Adding existing book throws IllegalStateException")
    public void testAddExistingBook() {
        library.addBook("Java for Dummies", "Barry Burd");
        assertThrows(IllegalStateException.class, () -> library.addBook("Java for Dummies", "Barry Burd"));
    }

    @Test
    @DisplayName("Getting book with non-null title returns the book")
    public void testGetBookWithNonNullTitle() {
        library.addBook("Java for Dummies", "Barry Burd");
        Book book = library.getBook("Java for Dummies");
        assertNotNull(book);
        assertEquals("Java for Dummies", book.getTitle());
        assertEquals("Barry Burd", book.getAuthor());
    }

    @Test
    @DisplayName("Getting book with null title throws IllegalArgumentException")
    public void testGetBookWithNullTitle() {
        assertThrows(IllegalArgumentException.class, () -> library.getBook(null));
    }

    @Test
    @DisplayName("Getting non-existing book throws IllegalStateException")
    public void testGetNonExistingBook() {
        assertThrows(IllegalStateException.class, () -> library.getBook("Java for Dummies"));
    }

    @Test
    @DisplayName("Registering borrower with non-null name registers the borrower successfully")
    public void testRegisterBorrowerWithNonNullName() {
        Borrower borrower = library.registerBorrower("Alice");
        assertNotNull(borrower);
        assertEquals("Alice", borrower.getName());
    }

    @Test
    @DisplayName("Registering borrower with null name throws IllegalArgumentException")
    public void testRegisterBorrowerWithNullName() {
        assertThrows(IllegalArgumentException.class, () -> library.registerBorrower(null));
    }

    @Test
    @DisplayName("Registering existing borrower throws IllegalStateException")
    public void testRegisterExistingBorrower() {
        library.registerBorrower("Alice");
        assertThrows(IllegalStateException.class, () -> library.registerBorrower("Alice"));
    }

    @Test
    @DisplayName("Getting borrower with non-null name returns the borrower")
    public void testGetBorrowerWithNonNullName() {
        library.registerBorrower("Alice");
        Borrower borrower = library.getBorrower("Alice");
        assertNotNull(borrower);
        assertEquals("Alice", borrower.getName());
    }

    @Test
    @DisplayName("Getting borrower with null name throws IllegalArgumentException")
    public void testGetBorrowerWithNullName() {
        assertThrows(IllegalArgumentException.class, () -> library.getBorrower(null));
    }

    @Test
    @DisplayName("Getting non-existing borrower throws IllegalStateException")
    public void testGetNonExistingBorrower() {
        assertThrows(IllegalStateException.class, () -> library.getBorrower("Alice"));
    }

    @Test
    @DisplayName("Lending available book to existing borrower borrows the book successfully")
    public void testLendAvailableBookToExistingBorrower() {
        Book book = library.addBook("Java for Dummies", "Barry Burd");
        Borrower borrower = library.registerBorrower("Alice");
        library.lendBook("Java for Dummies", "Alice");
        assertTrue(book.isBorrowed());
        assertEquals(borrower, book.getBorrower());
        assertEquals(1, borrower.getBorrowedBooks());
    }

    @Test
    @DisplayName("Lending borrowed book throws IllegalStateException")
    public void testLendBorrowedBook() {
        library.addBook("Java for Dummies", "Barry Burd");
        library.registerBorrower("Alice");
        library.lendBook("Java for Dummies", "Alice");
        assertThrows(IllegalStateException.class, () -> library.lendBook("Java for Dummies", "Bob"));
    }

    @Test
    @DisplayName("Lending non-existing book throws IllegalStateException")
    public void testLendNonExistingBook() {
        library.registerBorrower("Alice");
        assertThrows(IllegalStateException.class, () -> library.lendBook("Java for Dummies", "Alice"));
    }

    @Test
    @DisplayName("Lending book to non-existing borrower throws IllegalStateException")
    public void testLendBookToNonExistingBorrower() {
        library.addBook("Java for Dummies", "Barry Burd");
        assertThrows(IllegalStateException.class, () -> library.lendBook("Java for Dummies", "Alice"));
    }

    @Test
    @DisplayName("Returning borrowed book returns the book successfully")
    public void testReturnBorrowedBook() {
        Book book = library.addBook("Java for Dummies", "Barry Burd");
        Borrower borrower = library.registerBorrower("Alice");
        library.lendBook("Java for Dummies", "Alice");
        library.returnBook("Java for Dummies");
        assertFalse(book.isBorrowed());
        assertNull(book.getBorrower());
        assertEquals(0, borrower.getBorrowedBooks());
    }

    @Test
    @DisplayName("Returning non-borrowed book throws IllegalStateException")
    public void testReturnNonBorrowedBook() {
        library.addBook("Java for Dummies", "Barry Burd");
        assertThrows(IllegalStateException.class, () -> library.returnBook("Java for Dummies"));
    }

}