import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BookLibraryUnitTests {

    private BookLibrary bookLibrary;

    @BeforeEach
    void init() {
        bookLibrary = new BookLibrary();
    }

    @Test
    void testAddBook() {
        String title = "title";
        String author = "author";
        Book book = bookLibrary.addBook(title, author);
        assertAll(
                () -> assertEquals(title, book.getTitle()),
                () -> assertEquals(author, book.getAuthor())
        );
    }

    @Test
    void testAddBook_titleIsNull_throwsIllegalArgumentException() {
        String title = null;
        String author = "author";
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> bookLibrary.addBook(title, author)
        );
        assertEquals("Title and author must not be null", exception.getMessage());
    }

    @Test
    void testAddBook_authorIsNull_throwsIllegalArgumentException() {
        String title = "title";
        String author = null;
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> bookLibrary.addBook(title, author)
        );
        assertEquals("Title and author must not be null", exception.getMessage());
    }

    @Test
    void testAddBook_bookAlreadyExists_throwsIllegalStateException() {
        String title = "title";
        String author = "author";
        bookLibrary.addBook(title, author);
        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> bookLibrary.addBook(title, author)
        );
        assertEquals("Book already exists", exception.getMessage());
    }

    @Test
    void testGetBook() {
        String title = "title";
        String author = "author";
        Book book = bookLibrary.addBook(title, author);
        assertEquals(book, bookLibrary.getBook(title));
    }

    @Test
    void testGetBook_titleIsNull_throwsIllegalArgumentException() {
        String title = null;
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> bookLibrary.getBook(title)
        );
        assertEquals("Title must not be null", exception.getMessage());
    }

    @Test
    void testGetBook_bookDoesNotExist_throwsIllegalStateException() {
        String title = "title";
        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> bookLibrary.getBook(title)
        );
        assertEquals("Book does not exist", exception.getMessage());
    }

    @Test
    void testRegisterBorrower() {
        String name = "name";
        Borrower borrower = bookLibrary.registerBorrower(name);
        assertEquals(name, borrower.getName());
    }

    @Test
    void testRegisterBorrower_nameIsNull_throwsIllegalArgumentException() {
        String name = null;
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> bookLibrary.registerBorrower(name)
        );
        assertEquals("Name must not be null", exception.getMessage());
    }

    @Test
    void testRegisterBorrower_borrowerAlreadyRegistered_throwsIllegalStateException() {
        String name = "name";
        bookLibrary.registerBorrower(name);
        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> bookLibrary.registerBorrower(name)
        );
        assertEquals("Borrower already registered", exception.getMessage());
    }

    @Test
    void testGetBorrower() {
        String name = "name";
        bookLibrary.registerBorrower(name);
        assertEquals(name, bookLibrary.getBorrower(name).getName());
    }

    @Test
    void testGetBorrower_nameIsNull_throwsIllegalArgumentException() {
        String name = null;
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> bookLibrary.getBorrower(name)
        );
        assertEquals("Name must not be null", exception.getMessage());
    }

    @Test
    void testGetBorrower_borrowerDoesNotExist_throwsIllegalStateException() {
        String name = "name";
        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> bookLibrary.getBorrower(name)
        );
        assertEquals("Borrower does not exist", exception.getMessage());
    }

    @Test
    void testLendBook() {
        String title = "title";
        String author = "author";
        bookLibrary.addBook(title, author);
        String name = "name";
        bookLibrary.registerBorrower(name);
        bookLibrary.lendBook(title, name);
        Book book = bookLibrary.getBook(title);
        Borrower borrower = bookLibrary.getBorrower(name);
        assertAll(
                () -> assertTrue(book.isBorrowed()),
                () -> assertEquals(borrower, book.getBorrower()),
                () -> assertEquals(1, borrower.getBorrowedBooks())
        );
    }

    @Test
    void testLendBook_bookIsBorrowed_throwsIllegalStateException() {
        String title = "title";
        String author = "author";
        bookLibrary.addBook(title, author);
        String name = "name";
        bookLibrary.registerBorrower(name);
        bookLibrary.lendBook(title, name);
        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> bookLibrary.lendBook(title, name)
        );
        assertEquals("Book is already borrowed", exception.getMessage());
    }

    @Test
    void testReturnBook() {
        String title = "title";
        String author = "author";
        bookLibrary.addBook(title, author);
        String name = "name";
        bookLibrary.registerBorrower(name);
        bookLibrary.lendBook(title, name);
        bookLibrary.returnBook(title);
        Book book = bookLibrary.getBook(title);
        assertFalse(book.isBorrowed());
    }

    @Test
    void testReturnBook_bookIsNotBorrowed_throwsIllegalStateException() {
        String title = "title";
        String author = "author";
        bookLibrary.addBook(title, author);
        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> bookLibrary.returnBook(title)
        );
        assertEquals("Book is not borrowed", exception.getMessage());
    }

}