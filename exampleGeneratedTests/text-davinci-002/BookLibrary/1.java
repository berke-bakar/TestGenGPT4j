public class BookLibraryTest {

    @Test
    public void addBook_withNullTitle_throwsIllegalArgumentException() {
        BookLibrary bookLibrary = new BookLibrary();

        assertThrows(
                IllegalArgumentException.class,
                () -> bookLibrary.addBook(null, "author")
        );
    }

    @Test
    public void addBook_withNullAuthor_throwsIllegalArgumentException() {
        BookLibrary bookLibrary = new BookLibrary();

        assertThrows(
                IllegalArgumentException.class,
                () -> bookLibrary.addBook("title", null)
        );
    }

    @Test
    public void addBook_withExistingTitle_throwsIllegalStateException() {
        BookLibrary bookLibrary = new BookLibrary();
        bookLibrary.addBook("title", "author");

        assertThrows(
                IllegalStateException.class,
                () -> bookLibrary.addBook("title", "author")
        );
    }

    @Test
    public void getBook_withNullTitle_throwsIllegalArgumentException() {
        BookLibrary bookLibrary = new BookLibrary();

        assertThrows(
                IllegalArgumentException.class,
                () -> bookLibrary.getBook(null)
        );
    }

    @Test
    public void getBook_withNonExistingTitle_throwsIllegalStateException() {
        BookLibrary bookLibrary = new BookLibrary();

        assertThrows(
                IllegalStateException.class,
                () -> bookLibrary.getBook("title")
        );
    }

    @Test
    public void registerBorrower_withNullName_throwsIllegalArgumentException() {
        BookLibrary bookLibrary = new BookLibrary();

        assertThrows(
                IllegalArgumentException.class,
                () -> bookLibrary.registerBorrower(null)
        );
    }

    @Test
    public void registerBorrower_withExistingName_throwsIllegalStateException() {
        BookLibrary bookLibrary = new BookLibrary();
        bookLibrary.registerBorrower("name");

        assertThrows(
                IllegalStateException.class,
                () -> bookLibrary.registerBorrower("name")
        );
    }

    @Test
    public void getBorrower_withNullName_throwsIllegalArgumentException() {
        BookLibrary bookLibrary = new BookLibrary();

        assertThrows(
                IllegalArgumentException.class,
                () -> bookLibrary.getBorrower(null)
        );
    }

    @Test
    public void getBorrower_withNonExistingName_throwsIllegalStateException() {
        BookLibrary bookLibrary = new BookLibrary();

        assertThrows(
                IllegalStateException.class,
                () -> bookLibrary.getBorrower("name")
        );
    }

    @Test
    public void lendBook_withNonExistingTitle_throwsIllegalStateException() {
        BookLibrary bookLibrary = new BookLibrary();
        bookLibrary.registerBorrower("name");

        assertThrows(
                IllegalStateException.class,
                () -> bookLibrary.lendBook("title", "name")
        );
    }

    @Test
    public void lendBook_withNonExistingName_throwsIllegalStateException() {
        BookLibrary bookLibrary = new BookLibrary();
        bookLibrary.addBook("title", "author");

        assertThrows(
                IllegalStateException.class,
                () -> bookLibrary.lendBook("title", "name")
        );
    }

    @Test
    public void lendBook_withBorrowedBook_throwsIllegalStateException() {
        BookLibrary bookLibrary = new BookLibrary();
        bookLibrary.addBook("title", "author");
        bookLibrary.registerBorrower("name");
        bookLibrary.lendBook("title", "name");

        assertThrows(
                IllegalStateException.class,
                () -> bookLibrary.lendBook("title", "name")
        );
    }

    @Test
    public void returnBook_withNonExistingTitle_throwsIllegalStateException() {
        BookLibrary bookLibrary = new BookLibrary();

        assertThrows(
                IllegalStateException.class,
                () -> bookLibrary.returnBook("title")
        );
    }

    @Test
    public void returnBook_withNonBorrowedBook_throwsIllegalStateException() {
        BookLibrary bookLibrary = new BookLibrary();
        bookLibrary.addBook("title", "author");

        assertThrows(
                IllegalStateException.class,
                () -> bookLibrary.returnBook("title")
        );
    }
}