import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookLibraryTest {

    private BookLibrary library;

    @BeforeEach
    void setUp() {
        library = new BookLibrary();
    }

    @Test
    void testAddBook() {
        Book book = library.addBook("The Lord of the Rings", "J.R.R. Tolkien");
        assertNotNull(book);
        assertEquals("The Lord of the Rings", book.getTitle());
        assertEquals("J.R.R. Tolkien", book.getAuthor());
    }

    @Test
    void testAddBookWithNullTitle() {
        assertThrows(IllegalArgumentException.class, () -> library.addBook(null, "J.R.R. Tolkien"));
    }

    @Test
    void testAddBookWithNullAuthor() {
        assertThrows(IllegalArgumentException.class, () -> library.addBook("The Lord of the Rings", null));
    }

    @Test
    void testAddExistingBook() {
        library.addBook("The Lord of the Rings", "J.R.R. Tolkien");
        assertThrows(IllegalStateException.class, () -> library.addBook("The Lord of the Rings", "J.R.R. Tolkien"));
    }

    @Test
    void testGetBook() {
        Book book1 = library.addBook("The Lord of the Rings", "J.R.R. Tolkien");
        Book book2 = library.getBook("The Lord of the Rings");
        assertEquals(book1, book2);
    }

    @Test
    void testGetBookWithNullTitle() {
        assertThrows(IllegalArgumentException.class, () -> library.getBook(null));
    }

    @Test
    void testGetNonExistingBook() {
        assertThrows(IllegalStateException.class, () -> library.getBook("The Lord of the Rings"));
    }

    @Test
    void testRegisterBorrower() {
        Borrower borrower = library.registerBorrower("John Doe");
        assertNotNull(borrower);
        assertEquals("John Doe", borrower.getName());
    }

    @Test
    void testRegisterBorrowerWithNullName() {
        assertThrows(IllegalArgumentException.class, () -> library.registerBorrower(null));
    }

    @Test
    void testRegisterExistingBorrower() {
        library.registerBorrower("John Doe");
        assertThrows(IllegalStateException.class, () -> library.registerBorrower("John Doe"));
    }

    @Test
    void testGetBorrower() {
        Borrower borrower1 = library.registerBorrower("John Doe");
        Borrower borrower2 = library.getBorrower("John Doe");
        assertEquals(borrower1, borrower2);
    }

    @Test
    void testGetBorrowerWithNullName() {
        assertThrows(IllegalArgumentException.class, () -> library.getBorrower(null));
    }

    @Test
    void testGetNonExistingBorrower() {
        assertThrows(IllegalStateException.class, () -> library.getBorrower("John Doe"));
    }

    @Test
    void testLendBook() {
        library.addBook("The Lord of the Rings", "J.R.R. Tolkien");
        library.registerBorrower("John Doe");
        library.lendBook("The Lord of the Rings", "John Doe");
        assertTrue(library.getBook("The Lord of the Rings").isBorrowed());
        assertEquals(1, library.getBorrower("John Doe").getBorrowedBooks());
    }

    @Test
    void testLendAlreadyBorrowedBook() {
        library.addBook("The Lord of the Rings", "J.R.R. Tolkien");
        library.registerBorrower("John Doe");
        library.lendBook("The Lord of the Rings", "John Doe");
        assertThrows(IllegalStateException.class, () -> library.lendBook("The Lord of the Rings", "Jane Smith"));
    }

    @Test
    void testReturnBook() {
        library.addBook("The Lord of the Rings", "J.R.R. Tolkien");
        library.registerBorrower("John Doe");
        library.lendBook("The Lord of the Rings", "John Doe");
        library.returnBook("The Lord of the Rings");
        assertFalse(library.getBook("The Lord of the Rings").isBorrowed());
        assertEquals(0, library.getBorrower("John Doe").getBorrowedBooks());
    }

    @Test
    void testReturnNotBorrowedBook() {
        library.addBook("The Lord of the Rings", "J.R.R. Tolkien");
        assertThrows(IllegalStateException.class, () -> library.returnBook("The Lord of the Rings"));
    }
}