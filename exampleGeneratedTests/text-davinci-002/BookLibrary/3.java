@Test
public void testAddBook(){
  BookLibrary bookLibrary=new BookLibrary();
  bookLibrary.addBook("test1","author1");
  assertEquals(1,bookLibrary.books.size());
}
@Test
public void testAddBook_null_title(){
  BookLibrary bookLibrary=new BookLibrary();
  bookLibrary.addBook(null,"author1");
  assertThrows(IllegalArgumentException.class,()->{
    bookLibrary.addBook(null,"author1");
  });
}
@Test
public void testAddBook_null_author(){
  BookLibrary bookLibrary=new BookLibrary();
  bookLibrary.addBook("test1",null);
  assertThrows(IllegalArgumentException.class,()->{
    bookLibrary.addBook("test1",null);
  });
}
@Test
public void testAddBook_existing_book(){
  BookLibrary bookLibrary=new BookLibrary();
  bookLibrary.addBook("test1","author1");
  bookLibrary.addBook("test1","author1");
  assertThrows(IllegalStateException.class,()->{
    bookLibrary.addBook("test1","author1");
  });
}
@Test
public void testGetBook(){
  BookLibrary bookLibrary=new BookLibrary();
  bookLibrary.addBook("test1","author1");
  Book book=bookLibrary.getBook("test1");
  assertEquals("test1",book.getTitle());
}
@Test
public void testGetBook_null(){
  BookLibrary bookLibrary=new BookLibrary();
  bookLibrary.addBook("test1","author1");
  assertThrows(IllegalArgumentException.class,()->{
    bookLibrary.getBook(null);
  });
}
@Test
public void testGetBook_non_existing(){
  BookLibrary bookLibrary=new BookLibrary();
  bookLibrary.addBook("test1","author1");
  assertThrows(IllegalStateException.class,()->{
    bookLibrary.getBook("test2");
  });
}
@Test
public void testRegisterBorrower(){
  BookLibrary bookLibrary=new BookLibrary();
  bookLibrary.registerBorrower("name1");
  assertEquals(1,bookLibrary.borrowers.size());
}
@Test
public void testRegisterBorrower_null(){
  BookLibrary bookLibrary=new BookLibrary();
  bookLibrary.registerBorrower("name1");
  assertThrows(IllegalArgumentException.class,()->{
    bookLibrary.registerBorrower(null);
  });
}
@Test
public void testRegisterBorrower_existing(){
  BookLibrary bookLibrary=new BookLibrary();
  bookLibrary.registerBorrower("name1");
  bookLibrary.registerBorrower("name1");
  assertThrows(IllegalStateException.class,()->{
    bookLibrary.registerBorrower("name1");
  });
}
@Test
public void testGetBorrower(){
  BookLibrary bookLibrary=new BookLibrary();
  bookLibrary.registerBorrower("name1");
  Borrower borrower=bookLibrary.getBorrower("name1");
  assertEquals("name1",borrower.getName());
}
@Test
public void testGetBorrower_null(){
  BookLibrary bookLibrary=new BookLibrary();
  bookLibrary.registerBorrower("name1");
  assertThrows(IllegalArgumentException.class,()->{
    bookLibrary.getBorrower(null);
  });
}
@Test
public void testGetBorrower_non_existing(){
  BookLibrary bookLibrary=new BookLibrary();
  bookLibrary.registerBorrower("name1");
  assertThrows(IllegalStateException.class,()->{
    bookLibrary.getBorrower("name2");
  });
}
@Test
public void testLendBook(){
  BookLibrary bookLibrary=new BookLibrary();
  bookLibrary.addBook("test1","author1");
  bookLibrary.registerBorrower("name1");
  bookLibrary.lendBook("test1","name1");
  assertEquals(1,bookLibrary.getBook("test1").getBorrower().getBorrowedBooks());
}
@Test
public void testLendBook_null_title(){
  BookLibrary bookLibrary=new BookLibrary();
  bookLibrary.addBook("test1","author1");
  bookLibrary.registerBorrower("name1");
  assertThrows(IllegalArgumentException.class,()->{
    bookLibrary.lendBook(null,"name1");
  });
}
@Test
public void testLendBook_null_name(){
  BookLibrary bookLibrary=new BookLibrary();
  bookLibrary.addBook("test1","author1");
  bookLibrary.registerBorrower("name1");
  assertThrows(IllegalArgumentException.class,()->{
    bookLibrary.lendBook("test1",null);
  });
}
@Test
public void testLendBook_non_existing_title(){
  BookLibrary bookLibrary=new BookLibrary();
  bookLibrary.registerBorrower("name1");
  assertThrows(IllegalStateException.class,()->{
    bookLibrary.lendBook("test1","name1");
  });
}
@Test
public void testLendBook_non_existing_name(){
  BookLibrary bookLibrary=new BookLibrary();
  bookLibrary.addBook("test1","author1");
  assertThrows(IllegalStateException.class,()->{
    bookLibrary.lendBook("test1","name1");
  });
}
@Test
public void testLendBook_already_borrowed(){
  BookLibrary bookLibrary=new BookLibrary();
  bookLibrary.addBook("test1","author1");
  bookLibrary.registerBorrower("name1");
  bookLibrary.registerBorrower("name2");
  bookLibrary.lendBook("test1","name1");
  bookLibrary.lendBook("test1","name2");
  assertThrows(IllegalStateException.class,()->{
    bookLibrary.lendBook("test1","name2");
  });
}
@Test
public void testReturnBook(){
  BookLibrary bookLibrary=new BookLibrary();
  bookLibrary.addBook("test1","author1");
  bookLibrary.registerBorrower("name1");
  bookLibrary.lendBook("test1","name1");
  bookLibrary.returnBook("test1");
  assertEquals(0,bookLibrary.getBook("test1").getBorrower().getBorrowedBooks());
}
@Test
public void testReturnBook_null(){
  BookLibrary bookLibrary=new BookLibrary();
  bookLibrary.addBook("test1","author1");
  bookLibrary.registerBorrower("name1");
  bookLibrary.lendBook("test1","name1");
  assertThrows(IllegalArgumentException.class,()->{
    bookLibrary.returnBook(null);
  });
}
@Test
public void testReturnBook_non_existing(){
  BookLibrary bookLibrary=new BookLibrary();
  bookLibrary.registerBorrower("name1");
  bookLibrary.lendBook("test1","name1");
  assertThrows(IllegalStateException.class,()->{
    bookLibrary.returnBook("test2");
  });
}
@Test
public void testReturnBook_not_borrowed(){
  BookLibrary bookLibrary=new BookLibrary();
  bookLibrary.addBook("test1","author1");
  bookLibrary.registerBorrower("name1");
  bookLibrary.lendBook("test1","name1");
  bookLibrary.returnBook("test1");
  assertThrows(IllegalStateException.class,()->{
    bookLibrary.returnBook("test1");
  });
}