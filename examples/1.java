public class MyClass {
  private final UserDatabase userDatabase;

  public MyClass(final UserDatabase userDatabase) {
    this.userDatabase = userDatabase
  }

  public void writeUserName(int id, Writer writer) {
    final String userName = this.userDatabase.getUserName(id);
    writer.write(userName);
  }
}