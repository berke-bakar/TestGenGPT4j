import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;

class BookLibraryTest {

    private final BookLibrary library = new BookLibrary();

    @Test
    void addValidBook() {
        Book book = library.addBook("The Lord of the Rings", "J.R.R. Tolkien");
        Assertions.assertEquals("The Lord of the Rings", book.getTitle());
        Assertions.assertEquals("J.R.R. Tolkien", book.getAuthor());
    }

    @Test
    void addBookWithNullTitle() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> library.addBook(null, "Author"));
    }

    @Test
    void addBookWithNullAuthor() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> library.addBook("Title", null));
    }

    @Test
    void addExistingBook() {
        library.addBook("Harry Potter", "J.K. Rowling");
        Assertions.assertThrows(IllegalStateException.class, () -> library.addBook("Harry Potter", "J.K. Rowling"));
    }

    @Test
    void getValidBook() {
        library.addBook("The Great Gatsby", "F. Scott Fitzgerald");
        Book book = library.getBook("The Great Gatsby");
        Assertions.assertEquals("The Great Gatsby", book.getTitle());
        Assertions.assertEquals("F. Scott Fitzgerald", book.getAuthor());
    }

    @Test
    void getBookWithNullTitle() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> library.getBook(null));
    }

    @Test
    void getNonExistingBook() {
        Assertions.assertThrows(IllegalStateException.class, () -> library.getBook("Infinite Jest"));
    }

    @Test
    void registerValidBorrower() {
        Borrower borrower = library.registerBorrower("John Doe");
        Assertions.assertEquals("John Doe", borrower.getName());
        Assertions.assertEquals(0, borrower.getBorrowedBooks());
    }

    @Test
    void registerBorrowerWithNullName() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> library.registerBorrower(null));
    }

    @Test
    void registerExistingBorrower() {
        library.registerBorrower("Jane Doe");
        Assertions.assertThrows(IllegalStateException.class, () -> library.registerBorrower("Jane Doe"));
    }

    @Test
    void getValidBorrower() {
        library.registerBorrower("Alice Smith");
        Borrower borrower = library.getBorrower("Alice Smith");
        Assertions.assertEquals("Alice Smith", borrower.getName());
        Assertions.assertEquals(0, borrower.getBorrowedBooks());
    }

    @Test
    void getBorrowerWithNullName() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> library.getBorrower(null));
    }

    @Test
    void getNonExistingBorrower() {
        Assertions.assertThrows(IllegalStateException.class, () -> library.getBorrower("Bob Brown"));
    }

    @Test
    void lendValidBook() {
        library.addBook("Moby Dick", "Herman Melville");
        library.registerBorrower("Franklin Walker");
        library.lendBook("Moby Dick", "Franklin Walker");
        Book book = library.getBook("Moby Dick");
        Borrower borrower = library.getBorrower("Franklin Walker");
        Assertions.assertTrue(book.isBorrowed());
        Assertions.assertEquals(borrower, book.getBorrower());
        Assertions.assertEquals(1, borrower.getBorrowedBooks());
    }

    @Test
    void lendAlreadyBorrowedBook() {
        library.addBook("1984", "George Orwell");
        library.registerBorrower("Emily Olsen");
        library.lendBook("1984", "Emily Olsen");
        Assertions.assertThrows(IllegalStateException.class, () -> library.lendBook("1984", "John Smith"));
    }

    @Test
    void returnValidBook() {
        library.addBook("The Catcher in the Rye", "J.D. Salinger");
        library.registerBorrower("Mary Johnson");
        library.lendBook("The Catcher in the Rye", "Mary Johnson");
        library.returnBook("The Catcher in the Rye");
        Book book = library.getBook("The Catcher in the Rye");
        Borrower borrower = library.getBorrower("Mary Johnson");
        Assertions.assertFalse(book.isBorrowed());
        Assertions.assertNull(book.getBorrower());
        Assertions.assertEquals(0, borrower.getBorrowedBooks());
    }

    @Test
    void returnNonBorrowedBook() {
        library.addBook("Pride and Prejudice", "Jane Austen");
        Assertions.assertThrows(IllegalStateException.class, () -> library.returnBook("Pride and Prejudice"));
    }
}