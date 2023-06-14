import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BankTest {
    private Bank bank;

    @BeforeEach
    void setUp() {
        bank = new Bank();
    }

    @Test
    @DisplayName("Test createAccount method with valid input")
    void testCreateAccount() {
        Account account = bank.createAccount("ACC123", 100.0);
        Assertions.assertNotNull(account);
        Assertions.assertEquals("ACC123", account.getAccountId());
        Assertions.assertEquals(100.0, account.getBalance());
    }

    @Test
    @DisplayName("Test createAccount method with null accountId")
    void testCreateAccountWithNullAccountId() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> bank.createAccount(null, 100.0));
    }

    @Test
    @DisplayName("Test createAccount method with negative initialBalance")
    void testCreateAccountWithNegativeInitialBalance() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> bank.createAccount("ACC123", -100.0));
    }

    @Test
    @DisplayName("Test createAccount method with existing accountId")
    void testCreateAccountWithExistingAccountId() {
        bank.createAccount("ACC123", 100.0);
        Assertions.assertThrows(IllegalStateException.class, () -> bank.createAccount("ACC123", 200.0));
    }

    @Test
    @DisplayName("Test getAccount method with valid input")
    void testGetAccount() {
        bank.createAccount("ACC123", 100.0);
        Account account = bank.getAccount("ACC123");
        Assertions.assertNotNull(account);
        Assertions.assertEquals("ACC123", account.getAccountId());
        Assertions.assertEquals(100.0, account.getBalance());
    }

    @Test
    @DisplayName("Test getAccount method with null accountId")
    void testGetAccountWithNullAccountId() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> bank.getAccount(null));
    }

    @Test
    @DisplayName("Test getAccount method with non-existing accountId")
    void testGetAccountWithNonExistingAccountId() {
        Assertions.assertThrows(IllegalStateException.class, () -> bank.getAccount("ACC123"));
    }

    @Test
    @DisplayName("Test deleteAccount method with valid input")
    void testDeleteAccount() {
        bank.createAccount("ACC123", 100.0);
        bank.deleteAccount("ACC123");
        Assertions.assertThrows(IllegalStateException.class, () -> bank.getAccount("ACC123"));
    }

    @Test
    @DisplayName("Test deleteAccount method with null accountId")
    void testDeleteAccountWithNullAccountId() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> bank.deleteAccount(null));
    }

    @Test
    @DisplayName("Test deleteAccount method with non-existing accountId")
    void testDeleteAccountWithNonExistingAccountId() {
        Assertions.assertThrows(IllegalStateException.class, () -> bank.deleteAccount("ACC123"));
    }

    @Test
    @DisplayName("Test transfer method with valid input")
    void testTransfer() {
        Account fromAccount = bank.createAccount("ACC123", 100.0);
        Account toAccount = bank.createAccount("ACC456", 0.0);
        bank.transfer("ACC123", "ACC456", 50.0);
        Assertions.assertEquals(50.0, fromAccount.getBalance());
        Assertions.assertEquals(50.0, toAccount.getBalance());
    }

    @Test
    @DisplayName("Test transfer method with non-positive amount")
    void testTransferWithNonPositiveAmount() {
        Account fromAccount = bank.createAccount("ACC123", 100.0);
        Account toAccount = bank.createAccount("ACC456", 0.0);
        Assertions.assertThrows(IllegalArgumentException.class, () -> bank.transfer("ACC123", "ACC456", -50.0));
        Assertions.assertThrows(IllegalArgumentException.class, () -> bank.transfer("ACC123", "ACC456", 0.0));
        Assertions.assertEquals(100.0, fromAccount.getBalance());
        Assertions.assertEquals(0.0, toAccount.getBalance());
    }

    @Test
    @DisplayName("Test transfer method with insufficient funds")
    void testTransferWithInsufficientFunds() {
        Account fromAccount = bank.createAccount("ACC123", 100.0);
        Account toAccount = bank.createAccount("ACC456", 0.0);
        Assertions.assertThrows(IllegalStateException.class, () -> bank.transfer("ACC123", "ACC456", 200.0));
        Assertions.assertEquals(100.0, fromAccount.getBalance());
        Assertions.assertEquals(0.0, toAccount.getBalance());
    }
}