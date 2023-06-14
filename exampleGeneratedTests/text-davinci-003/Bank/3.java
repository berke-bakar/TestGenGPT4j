@ExtendWith(MockitoExtension.class)
public class BankTest {

    @Mock
    private Account mockAccount;
    @Mock
    private Transaction mockTransaction;

    @Test
    void createAccount_validInputs() {
        Bank bank = new Bank();
        Account account = bank.createAccount("ABC123", 1000.00)
        assertNotNull(account);
        assertEquals("ABC123", account.getAccountId());
        assertEquals(1000.00, account.getBalance());
    }

    @Test
    void createAccount_invalidInputs() {
        Bank bank = new Bank();
        assertThrows(IllegalArgumentException.class, () -> bank.createAccount(null, -10.0));
    }

    @Test
    void getAccount_validInputs() {
        Bank bank = new Bank();
        Account account = bank.createAccount("ABC123", 1000.00)
        Account retrievedAccount = bank.getAccount("ABC123");
        assertNotNull(retrievedAccount);
        assertEquals(account, retrievedAccount);
    }

    @Test
    void getAccount_invalidInputs() {
        Bank bank = new Bank();
        assertThrows(IllegalArgumentException.class, () -> bank.getAccount(null));
    }

    @Test
    void deleteAccount_validInputs() {
        Bank bank = new Bank();
        Account account = bank.createAccount("ABC123", 1000.00)
        bank.deleteAccount("ABC123");
        assertThrows(IllegalStateException.class, () -> bank.getAccount("ABC123"));
    }

    @Test
    void deleteAccount_invalidInputs() {
        Bank bank = new Bank();
        assertThrows(IllegalArgumentException.class, () -> bank.deleteAccount(null));
    }

    @Test
    void transfer_validInputs() {
        Bank bank = new Bank();
        Account fromAccount = bank.createAccount("ABC123", 1000.00)
        Account toAccount = bank.createAccount("DEF456", 500.00);

        bank.transfer("ABC123", "DEF456", 100.00);

        assertEquals(900.00, fromAccount.getBalance());
        assertEquals(600.00, toAccount.getBalance());
    }

    @Test
    void transfer_invalidInputs() {
        Bank bank = new Bank();
        Account fromAccount = bank.createAccount("ABC123", 1000.00)
        Account toAccount = bank.createAccount("DEF456", 500.00);

        assertThrows(IllegalArgumentException.class, () -> bank.transfer("ABC123", "DEF456", -10.00));
        assertThrows(IllegalStateException.class, () -> bank.transfer("ABC123", "DEF456", 1500.00));
    }
}