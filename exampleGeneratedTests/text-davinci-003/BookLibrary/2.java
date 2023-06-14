@ExtendWith(MockitoExtension.class)
public class BookLibraryTest {

    @Mock
    Book bookMock;

    @Mock
    Borrower borrowerMock;

    @Test
    void addBook_allArgsValid_bookIsAdded() {
        BookLibrary bookLibrary = new BookLibrary();
        String title = "Title";
        String author = "Author";

        Book book = bookLibrary.addBook(title, author);

        assertNotNull(book);
        assertTrue(bookLibrary.books.containsKey(title));
    }

    @Test
    void addBook_titleNull_throwIllegalArgumentException() {
        BookLibrary bookLibrary = new BookLibrary();
        String author = "Author";

        assertThrows(IllegalArgumentException.class, () -> bookLibrary.addBook(null, author));
    }

    @Test
    void addBook_authorNull_throwIllegalArgumentException() {
        BookLibrary bookLibrary = new BookLibrary();
        String title = "Title";

        assertThrows(IllegalArgumentException.class, () -> bookLibrary.addBook(title, null));
    }

    @Test
    void addBook_bookAlreadyExists_throwIllegalStateException() {
        BookLibrary bookLibrary = new BookLibrary();
        String title = "Title";
        String author = "Author";

        bookLibrary.addBook(title, author);
        assertThrows(IllegalStateException.class, () -> bookLibrary.addBook(title, author));
    }

    @Test
    void getBook_titleValid_bookIsReturned() {
        BookLibrary bookLibrary = new BookLibrary();
        String title = "Title";
        String author = "Author";
        bookLibrary.addBook(title, author);

        Book book = bookLibrary.getBook(title);

        assertNotNull(book);
        assertEquals(title, book.getTitle());
        assertEquals(author, book.getAuthor());
    }

    @Test
    void getBook_titleNull_throwIllegalArgumentException() {
        BookLibrary bookLibrary = new BookLibrary();

        assertThrows(IllegalArgumentException.class, () -> bookLibrary.getBook(null));
    }

    @Test
    void getBook_bookDoesNotExist_throwIllegalStateException() {
        BookLibrary bookLibrary = new BookLibrary();

        assertThrows(IllegalStateException.class, () -> bookLibrary.getBook("Title"));
    }

    @Test
    void registerBorrower_nameValid_borrowerIsRegistered() {
        BookLibrary bookLibrary = new BookLibrary();
        String name = "Name";

        Borrower borrower = bookLibrary.registerBorrower(name);

        assertNotNull(borrower);
        assertTrue(bookLibrary.borrowers.containsKey(name));
    }

    @Test
    void registerBorrower_nameNull_throwIllegalArgumentException() {
        BookLibrary bookLibrary = new BookLibrary();

        assertThrows(IllegalArgumentException.class, () -> bookLibrary.registerBorrower(null));
    }

    @Test
    void registerBorrower_borrowerAlreadyRegistered_throwIllegalStateException() {
        BookLibrary bookLibrary = new BookLibrary();
        String name = "Name";
        bookLibrary.registerBorrower(name);

        assertThrows(IllegalStateException.class, () -> bookLibrary.registerBorrower(name));
    }

    @Test
    void getBorrower_nameValid_borrowerIsReturned() {
        BookLibrary bookLibrary = new BookLibrary();
        String name = "Name";
        bookLibrary.registerBorrower(name);

        Borrower borrower = bookLibrary.getBorrower(name);

        assertNotNull(borrower);
        assertEquals(name, borrower.getName());
    }

    @Test
    void getBorrower_nameNull_throwIllegalArgumentException() {
        BookLibrary bookLibrary = new BookLibrary();

        assertThrows(IllegalArgumentException.class, () -> bookLibrary.getBorrower(null));
    }

    @Test
    void getBorrower_borrowerDoesNotExist_throwIllegalStateException() {
        BookLibrary bookLibrary = new BookLibrary();

        assertThrows(IllegalStateException.class, () -> bookLibrary.getBorrower("Name"));
    }

    @Test
    void lendBook_allArgsValid_bookIsLent() {
        when(bookMock.isBorrowed()).thenReturn(false);

        BookLibrary bookLibrary = new BookLibrary();
        String title = "Title";
        String name = "Name";
        bookLibrary.books.put(title, bookMock);
        bookLibrary.borrowers.put(name, borrowerMock);

        bookLibrary.lendBook(title, name);

        verify(borrowerMock).borrowBook(bookMock);
        verify(bookMock).setBorrower(borrowerMock);
    }

    @Test
    void lendBook_titleNull_throwIllegalArgumentException() {
        BookLibrary bookLibrary = new BookLibrary();
        String name = "Name";
        bookLibrary.borrowers.put(name, borrowerMock);

        assertThrows(IllegalArgumentException.class, () -> bookLibrary.lendBook(null, name));
    }

    @Test
    void lendBook_nameNull_throwIllegalArgumentException() {
        BookLibrary bookLibrary = new BookLibrary();
        String title = "Title";
        bookLibrary.books.put(title, bookMock);

        assertThrows(IllegalArgumentException.class, () -> bookLibrary.lendBook(title, null));
    }

    @Test
    void lendBook_bookAlreadyBorrowed_throwIllegalStateException() {
        when(bookMock.isBorrowed()).thenReturn(true);

        BookLibrary bookLibrary = new BookLibrary();
        String title = "Title";
        String name = "Name";
        bookLibrary.books.put(title, bookMock);
        bookLibrary.borrowers.put(name, borrowerMock);

        assertThrows(IllegalStateException.class, () -> bookLibrary.lendBook(title, name));
    }

    @Test
    void returnBook_titleValid_bookIsReturned() {
        when(bookMock.isBorrowed()).thenReturn(true);
        when(bookMock.getBorrower()).thenReturn(borrowerMock);

        BookLibrary bookLibrary = new BookLibrary();
        String title = "Title";
        bookLibrary.books.put(title, bookMock);

        bookLibrary.returnBook(title);

        verify(borrowerMock).returnBook(bookMock);
        verify(bookMock).setBorrower(null);
    }

    @Test
    void returnBook_titleNull_throwIllegalArgumentException() {
        BookLibrary bookLibrary = new BookLibrary();

        assertThrows(IllegalArgumentException.class, () -> bookLibrary.returnBook(null));
    }

    @Test
    void returnBook_bookNotBorrowed_throwIllegalStateException() {
        when(bookMock.isBorrowed()).thenReturn(false);

        BookLibrary bookLibrary = new BookLibrary();
        String title = "Title";
        bookLibrary.books.put(title, bookMock);

        assertThrows(IllegalStateException.class, () -> bookLibrary.returnBook(title));
    }
}