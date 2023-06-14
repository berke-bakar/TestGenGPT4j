import org.example.Book;
import org.example.BookLibrary;
import org.example.Borrower;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BookLibraryTest {

    @Test
    public void addBookShouldAcceptValidArguments() {
        // Arrange
        BookLibrary library = new BookLibrary();
        String title = "testBook";
        String author = "author";

        // Act
        Book book = library.addBook(title, author);

        // Assert
        Assertions.assertNotNull(book);
        Assertions.assertEquals(title, book.getTitle());
        Assertions.assertEquals(author, book.getAuthor());
    }

    @Test
    public void addBookShouldThrowExceptionWhenTitleNull() {
        // Arrange
        BookLibrary library = new BookLibrary();
        String title = null;
        String author = "author";

        // Act
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> library.addBook(title, author));

        // Assert
        Assertions.assertEquals("Title and author must not be null", exception.getMessage());
    }

    @Test
    public void addBookShouldThrowExceptionWhenAuthorNull() {
        // Arrange
        BookLibrary library = new BookLibrary();
        String title = "testBook";
        String author = null;

        // Act
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> library.addBook(title, author));

        // Assert
        Assertions.assertEquals("Title and author must not be null", exception.getMessage());
    }

    @Test
    public void addBookShouldThrowExceptionWhenBookAlreadyExists() {
        // Arrange
        BookLibrary library = new BookLibrary();
        String title = "testBook";
        String author = "author";
        Book book = library.addBook(title, author);

        // Act
        Exception exception = Assertions.assertThrows(IllegalStateException.class, () -> library.addBook(title, author));

        // Assert
        Assertions.assertEquals("Book already exists", exception.getMessage());
    }

    @Test
    public void getBookShouldAcceptValidArguments() {
        // Arrange
        BookLibrary library = new BookLibrary();
        String title = "testBook";
        String author = "author";
        Book book = library.addBook(title, author);

        // Act
        Book bookRetrieved = library.getBook(title);

        // Assert
        Assertions.assertEquals(book, bookRetrieved);
    }

    @Test
    public void getBookShouldThrowExceptionWhenTitleNull() {
        // Arrange
        BookLibrary library = new BookLibrary();
        String title = null;

        // Act
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> library.getBook(title));

        // Assert
        Assertions.assertEquals("Title must not be null", exception.getMessage());
    }

    @Test
    public void getBookShouldThrowExceptionWhenBookDoesNotExist() {
        // Arrange
        BookLibrary library = new BookLibrary();
        String title = "testBook";

        // Act
        Exception exception = Assertions.assertThrows(IllegalStateException.class, () -> library.getBook(title));

        // Assert
        Assertions.assertEquals("Book does not exist", exception.getMessage());
    }

    @Test
    public void registerBorrowerShouldAcceptValidArguments() {
        // Arrange
        BookLibrary library = new BookLibrary();
        String name = "testBorrower";

        // Act
        Borrower borrower = library.registerBorrower(name);

        // Assert
        Assertions.assertNotNull(borrower);
        Assertions.assertEquals(name, borrower.getName());
    }

    @Test
    public void registerBorrowerShouldThrowExceptionWhenNameNull() {
        // Arrange
        BookLibrary library = new BookLibrary();
        String name = null;

        // Act
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> library.registerBorrower(name));

        // Assert
        Assertions.assertEquals("Name must not be null", exception.getMessage());
    }

    @Test
    public void registerBorrowerShouldThrowExceptionWhenBorrowerAlreadyRegistered() {
        // Arrange
        BookLibrary library = new BookLibrary();
        String name = "testBorrower";
        library.registerBorrower(name);

        // Act
        Exception exception = Assertions.assertThrows(IllegalStateException.class, () -> library.registerBorrower(name));

        // Assert
        Assertions.assertEquals("Borrower already registered", exception.getMessage());
    }

    @Test
    public void getBorrowerShouldAcceptValidArguments() {
        // Arrange
        BookLibrary library = new BookLibrary();
        String name = "testBorrower";
        Borrower borrower = library.registerBorrower(name);

        // Act
        Borrower borrowerRetrieved = library.getBorrower(name);

        // Assert
        Assertions.assertEquals(borrower, borrowerRetrieved);
    }

    @Test
    public void getBorrowerShouldThrowExceptionWhenNameNull() {
        // Arrange
        BookLibrary library = new BookLibrary();
        String name = null;

        // Act
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> library.getBorrower(name));

        // Assert
        Assertions.assertEquals("Name must not be null", exception.getMessage());
    }

    @Test
    public void getBorrowerShouldThrowExceptionWhenBorrowerDoesNotExist() {
        // Arrange
        BookLibrary library = new BookLibrary();
        String name = "testBorrower";

        // Act
        Exception exception = Assertions.assertThrows(IllegalStateException.class, () -> library.getBorrower(name));

        // Assert
        Assertions.assertEquals("Borrower does not exist", exception.getMessage());
    }

    @Test
    public void lendBookShouldAcceptValidArguments() {
        // Arrange
        BookLibrary library = new BookLibrary();
        String title = "testBook";
        String author = "author";
        Book book = library.addBook(title, author);
        String name = "testBorrower";
        Borrower borrower = library.registerBorrower(name);

        // Act
        library.lendBook(title, name);

        // Assert
        Assertions.assertEquals(borrower.getBorrowedBooks(), 1);
        Assertions.assertEquals(book.getBorrower(), borrower);
    }

    @Test
    public void lendBookShouldThrowExceptionWhenTitleNull() {
        // Arrange
        BookLibrary library = new BookLibrary();
        String title = null;
        String name = "testBorrower";
        Borrower borrower = library.registerBorrower(name);

        // Act
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> library.lendBook(title, name));

        // Assert
        Assertions.assertEquals("Title must not be null", exception.getMessage());
    }

    @Test
    public void lendBookShouldThrowExceptionWhenNameNull() {
        // Arrange
        BookLibrary library = new BookLibrary();
        String title = "testBook";
        String name = null;
        String author = "author";
        library.addBook(title, author);

        // Act
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> library.lendBook(title, name));

        // Assert
        Assertions.assertEquals("Name must not be null", exception.getMessage());
    }

    @Test
    public void lendBookShouldThrowExceptionWhenBookDoesNotExist() {
        // Arrange
        BookLibrary library = new BookLibrary();
        String title = "testBook";
        String name = "testBorrower";

        // Act
        Exception exception = Assertions.assertThrows(IllegalStateException.class, () -> library.lendBook(title, name));

        // Assert
        Assertions.assertEquals("Book does not exist", exception.getMessage());
    }

    @Test
    public void lendBookShouldThrowExceptionWhenBorrowerDoesNotExist() {
        // Arrange
        BookLibrary library = new BookLibrary();
        String title = "testBook";
        String name = "testBorrower";
        String author = "author";
        library.addBook(title, author);

        // Act
        Exception exception = Assertions.assertThrows(IllegalStateException.class, () -> library.lendBook(title, name));

        // Assert
        Assertions.assertEquals("Borrower does not exist", exception.getMessage());
    }

    @Test
    public void lendBookShouldThrowExceptionWhenBookAlreadyBorrowed() {
        // Arrange
        BookLibrary library = new BookLibrary();
        String title = "testBook";
        String name = "testBorrower";
        String author = "author";
        Book book = library.addBook(title, author);
        Borrower borrower = library.registerBorrower(name);
        library.lendBook(title, name);

        // Act
        Exception exception = Assertions.assertThrows(IllegalStateException.class, () -> library.lendBook(title, name));

        // Assert
        Assertions.assertEquals("Book is already borrowed", exception.getMessage());
    }

    @Test
    public void returnBookShouldAcceptValidArguments() {
        // Arrange
        BookLibrary library = new BookLibrary();
        String title = "testBook";
        String name = "testBorrower";
        String author = "author";
        Book book = library.addBook(title, author);
        Borrower borrower = library.registerBorrower(name);
        library.lendBook(title, name);

        // Act
        library.returnBook(title);

        // Assert
        Assertions.assertEquals(borrower.getBorrowedBooks(), 0);
        Assertions.assertNull(book.getBorrower());
    }

    @Test
    public void returnBookShouldThrowExceptionWhenTitleNull() {
        // Arrange
        BookLibrary library = new BookLibrary();
        String title = null;

        // Act
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> library.returnBook(title));

        // Assert
        Assertions.assertEquals("Title must not be null", exception.getMessage());
    }

    @Test
    public void returnBookShouldThrowExceptionWhenBookDoesNotExist() {
        // Arrange
        BookLibrary library = new BookLibrary();
        String title = "testBook";

        // Act
        Exception exception = Assertions.assertThrows(IllegalStateException.class, () -> library.returnBook(title));

        // Assert
        Assertions.assertEquals("Book does not exist", exception.getMessage());
    }

    @Test
    public void returnBookShouldThrowExceptionWhenBookNotBorrowed() {
        // Arrange
        BookLibrary library = new BookLibrary();
        String title = "testBook";
        String author = "author";
        library.addBook(title, author);

        // Act
        Exception exception = Assertions.assertThrows(IllegalStateException.class, () -> library.returnBook(title));

        // Assert
        Assertions.assertEquals("Book is not borrowed", exception.getMessage());
    }

}