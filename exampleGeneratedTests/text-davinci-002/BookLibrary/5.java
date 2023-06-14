public class BookLibraryTest {

    @Test
    public void addBook_WithValidTitleAndAuthor_ShouldAddBook() {
        // Arrange
        BookLibrary bookLibrary = new BookLibrary();

        // Act
        bookLibrary.addBook("Effective Java", "Joshua Bloch");

        // Assert
        assertEquals(1, bookLibrary.getBooks().size());
        assertTrue(bookLibrary.getBooks().containsKey("Effective Java"));
        assertEquals("Joshua Bloch", bookLibrary.getBooks().get("Effective Java").getAuthor());
    }

    @Test
    public void addBook_WithNullTitle_ShouldThrowIllegalArgumentException() {
        // Arrange
        BookLibrary bookLibrary = new BookLibrary();

        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> bookLibrary.addBook(null, "Joshua Bloch"));
    }

    @Test
    public void addBook_WithNullAuthor_ShouldThrowIllegalArgumentException() {
        // Arrange
        BookLibrary bookLibrary = new BookLibrary();

        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> bookLibrary.addBook("Effective Java", null));
    }

    @Test
    public void addBook_WithExistingTitle_ShouldThrowIllegalStateException() {
        // Arrange
        BookLibrary bookLibrary = new BookLibrary();
        bookLibrary.addBook("Effective Java", "Joshua Bloch");

        // Act & Assert
        assertThrows(IllegalStateException.class,
                () -> bookLibrary.addBook("Effective Java", "Joshua Bloch"));
    }

    @Test
    public void getBook_WithExistingTitle_ShouldReturnBook() {
        // Arrange
        BookLibrary bookLibrary = new BookLibrary();
        bookLibrary.addBook("Effective Java", "Joshua Bloch");

        // Act
        Book book = bookLibrary.getBook("Effective Java");

        // Assert
        assertEquals("Effective Java", book.getTitle());
        assertEquals("Joshua Bloch", book.getAuthor());
    }

    @Test
    public void getBook_WithNullTitle_ShouldThrowIllegalArgumentException() {
        // Arrange
        BookLibrary bookLibrary = new BookLibrary();

        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> bookLibrary.getBook(null));
    }

    @Test
    public void getBook_WithNonExistingTitle_ShouldThrowIllegalStateException() {
        // Arrange
        BookLibrary bookLibrary = new BookLibrary();

        // Act & Assert
        assertThrows(IllegalStateException.class,
                () -> bookLibrary.getBook("Effective Java"));
    }

    @Test
    public void registerBorrower_WithValidName_ShouldRegisterBorrower() {
        // Arrange
        BookLibrary bookLibrary = new BookLibrary();

        // Act
        bookLibrary.registerBorrower("John Doe");

        // Assert
        assertEquals(1, bookLibrary.getBorrowers().size());
        assertTrue(bookLibrary.getBorrowers().containsKey("John Doe"));
        assertEquals("John Doe", bookLibrary.getBorrowers().get("John Doe").getName());
    }

    @Test
    public void registerBorrower_WithNullName_ShouldThrowIllegalArgumentException() {
        // Arrange
        BookLibrary bookLibrary = new BookLibrary();

        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> bookLibrary.registerBorrower(null));
    }

    @Test
    public void registerBorrower_WithExistingName_ShouldThrowIllegalStateException() {
        // Arrange
        BookLibrary bookLibrary = new BookLibrary();
        bookLibrary.registerBorrower("John Doe");

        // Act & Assert
        assertThrows(IllegalStateException.class,
                () -> bookLibrary.registerBorrower("John Doe"));
    }

    @Test
    public void getBorrower_WithExistingName_ShouldReturnBorrower() {
        // Arrange
        BookLibrary bookLibrary = new BookLibrary();
        bookLibrary.registerBorrower("John Doe");

        // Act
        Borrower borrower = bookLibrary.getBorrower("John Doe");

        // Assert
        assertEquals("John Doe", borrower.getName());
    }

    @Test
    public void getBorrower_WithNullName_ShouldThrowIllegalArgumentException() {
        // Arrange
        BookLibrary bookLibrary = new BookLibrary();

        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> bookLibrary.getBorrower(null));
    }

    @Test
    public void getBorrower_WithNonExistingName_ShouldThrowIllegalStateException() {
        // Arrange
        BookLibrary bookLibrary = new BookLibrary();

        // Act & Assert
        assertThrows(IllegalStateException.class,
                () -> bookLibrary.getBorrower("John Doe"));
    }

    @Test
    public void lendBook_WithValidTitleAndName_ShouldLendBook() {
        // Arrange
        BookLibrary bookLibrary = new BookLibrary();
        bookLibrary.addBook("Effective Java", "Joshua Bloch");
        bookLibrary.registerBorrower("John Doe");

        // Act
        bookLibrary.lendBook("Effective Java", "John Doe");

        // Assert
        assertTrue(bookLibrary.getBook("Effective Java").isBorrowed());
        assertEquals("John Doe", bookLibrary.getBook("Effective Java").getBorrower().getName());
        assertEquals(1, bookLibrary.getBorrower("John Doe").getBorrowedBooks());
    }

    @Test
    public void lendBook_WithNullTitle_ShouldThrowIllegalArgumentException() {
        // Arrange
        BookLibrary bookLibrary = new BookLibrary();

        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> bookLibrary.lendBook(null, "John Doe"));
    }

    @Test
    public void lendBook_WithNullName_ShouldThrowIllegalArgumentException() {
        // Arrange
        BookLibrary bookLibrary = new BookLibrary();

        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> bookLibrary.lendBook("Effective Java", null));
    }

    @Test
    public void lendBook_WithNonExistingTitle_ShouldThrowIllegalStateException() {
        // Arrange
        BookLibrary bookLibrary = new BookLibrary();
        bookLibrary.registerBorrower("John Doe");

        // Act & Assert
        assertThrows(IllegalStateException.class,
                () -> bookLibrary.lendBook("Effective Java", "John Doe"));
    }

    @Test
    public void lendBook_WithNonExistingName_ShouldThrowIllegalStateException() {
        // Arrange
        BookLibrary bookLibrary = new BookLibrary();
        bookLibrary.addBook("Effective Java", "Joshua Bloch");

        // Act & Assert
        assertThrows(IllegalStateException.class,
                () -> bookLibrary.lendBook("Effective Java", "John Doe"));
    }

    @Test
    public void lendBook_WithBorrowedBook_ShouldThrowIllegalStateException() {
        // Arrange
        BookLibrary bookLibrary = new BookLibrary();
        bookLibrary.addBook("Effective Java", "Joshua Bloch");
        bookLibrary.registerBorrower("John Doe");
        bookLibrary.lendBook("Effective Java", "John Doe");

        // Act & Assert
        assertThrows(IllegalStateException.class,
                () -> bookLibrary.lendBook("Effective Java", "John Doe"));
    }

    @Test
    public void returnBook_WithExistingTitle_ShouldReturnBook() {
        // Arrange
        BookLibrary bookLibrary = new BookLibrary();
        bookLibrary.addBook("Effective Java", "Joshua Bloch");
        bookLibrary.registerBorrower("John Doe");
        bookLibrary.lendBook("Effective Java", "John Doe");

        // Act
        bookLibrary.returnBook("Effective Java");

        // Assert
        assertFalse(bookLibrary.getBook("Effective Java").isBorrowed());
        assertNull(bookLibrary.getBook("Effective Java").getBorrower());
        assertEquals(0, bookLibrary.getBorrower("John Doe").getBorrowedBooks());
    }

    @Test
    public void returnBook_WithNullTitle_ShouldThrowIllegalArgumentException() {
        // Arrange
        BookLibrary bookLibrary = new BookLibrary();

        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> bookLibrary.returnBook(null));
    }

    @Test
    public void returnBook_WithNonExistingTitle_ShouldThrowIllegalStateException() {
        // Arrange
        BookLibrary bookLibrary = new BookLibrary();

        // Act & Assert
        assertThrows(IllegalStateException.class,
                () -> bookLibrary.returnBook("Effective Java"));
    }

    @Test
    public void returnBook_WithNonBorrowedBook_ShouldThrowIllegalStateException() {
        // Arrange
        BookLibrary bookLibrary = new BookLibrary();
        bookLibrary.addBook("Effective Java", "Joshua Bloch");

        // Act & Assert
        assertThrows(IllegalStateException.class,
                () -> bookLibrary.returnBook("Effective Java"));
    }
}