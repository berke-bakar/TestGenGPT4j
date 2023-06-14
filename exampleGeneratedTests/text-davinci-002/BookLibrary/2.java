public class BookLibraryTest {
    @Test
    public void testAddBook(){
        BookLibrary bl = new BookLibrary();
        // exercise
        bl.addBook("IS", "Kathy Sierra");
        // verify
        assertEquals(1, bl.getAllBooks().size());
        assertEquals("Kathy Sierra", bl.getBook("IS").getAuthor());
    }

    @Test
    public void testAddBorrower(){
        BookLibrary bl = new BookLibrary();
        // exercise
        bl.registerBorrower("John");
        // verify
        assertEquals(1, bl.getAllBorrowers().size());
        assertEquals("John", bl.getBorrower("John").getName());
    }

    @Test
    public void testLendBook(){
        BookLibrary bl = new BookLibrary();
        bl.addBook("IS", "Kathy Sierra");
        bl.registerBorrower("John");
        // exercise
        bl.lendBook("IS","John");
        // verify
        assertEquals(1, bl.getBorrower("John").getBorrowedBooks());
        assertEquals("John", bl.getBook("IS").getBorrower().getName());
    }

    @Test
    public void testReturnBook(){
        BookLibrary bl = new BookLibrary();
        bl.addBook("IS", "Kathy Sierra");
        bl.registerBorrower("John");
        bl.lendBook("IS","John");
        // exercise
        bl.returnBook("IS");
        // verify
        assertEquals(0, bl.getBorrower("John").getBorrowedBooks());
        assertNull(bl.getBook("IS").getBorrower());
    }
}