import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

public class BankTest {

    @Test
    @DisplayName("Test createAccount()")
    public void testCreateAccount() {
        Bank bank = new Bank();

        // Negative test for null Account ID
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> bank.createAccount(null, 0));
        assertEquals("Account ID must not be null and Initial balance must be non-negative",
            exception.getMessage());

        // Negative test for initial balance < 0
        exception = assertThrows(IllegalArgumentException.class,
            () -> bank.createAccount("1", -1));
        assertEquals("Account ID must not be null and Initial balance must be non-negative",
            exception.getMessage());

        // Negative test for existing account ID
        Account account1 = bank.createAccount("1", 0);
        exception = assertThrows(IllegalStateException.class,
            () -> bank.createAccount("1", 0));
        assertEquals("Account already exists", exception.getMessage());

        // Positive test
        Account account2 = bank.createAccount("2", 0);
        assertEquals(account2.getAccountId(), "2");
        assertEquals(account2.getBalance(), 0);
    }

    @Test
    @DisplayName("Test getAccount()")
    public void testGetAccount() {
        Bank bank = new Bank();

        // Negative test for null Account ID
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> bank.getAccount(null));
        assertEquals("Account ID must not be null", exception.getMessage());

        // Negative test for non-existing account ID
        exception = assertThrows(IllegalStateException.class,
            () -> bank.getAccount("1"));
        assertEquals("Account does not exist", exception.getMessage());

        // Positive test
        Account account1 = bank.createAccount("1", 0);
        Account account2 = bank.getAccount("1");
        assertEquals(account1.getAccountId(), account2.getAccountId());
        assertEquals(account1.getBalance(), account2.getBalance());
    }

    @Test
    @DisplayName("Test deleteAccount()")
    public void testDeleteAccount() {
        Bank bank = new Bank();

        // Negative test for null Account ID
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> bank.deleteAccount(null));
        assertEquals("Account ID must not be null", exception.getMessage());

        // Negative test for non-existing account ID
        exception = assertThrows(IllegalStateException.class,
            () -> bank.deleteAccount("1"));
        assertEquals("Account does not exist", exception.getMessage());

        // Positive test
        Account account1 = bank.createAccount("1", 0);
        bank.deleteAccount("1");
        exception = assertThrows(IllegalStateException.class,
            () -> bank.getAccount("1"));
        assertEquals("Account does not exist", exception.getMessage());
    }

    @Test
    @DisplayName("Test transfer()")
    public void testTransfer() {
        Bank bank = new Bank();

        // Negative test for amount <= 0
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> bank.transfer("1", "2", 0));
        assertEquals("Transfer amount must be positive", exception.getMessage());

        // Negative test for insufficient funds
        Account account1 = bank.createAccount("1", 0);
        Account account2 = bank.createAccount("2", 0);
        exception = assertThrows(IllegalStateException.class,
            () -> bank.transfer("1", "2", 10.0));
        assertEquals("Insufficient funds", exception.getMessage());

        // Positive test
        account1.credit(100.0);
        bank.transfer("1", "2", 10.0);
        assertEquals(account1.getBalance(), 90.0);
        assertEquals(account2.getBalance(), 10.0);
    }
}