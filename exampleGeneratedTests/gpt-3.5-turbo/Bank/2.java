import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(OrderAnnotation.class)
public class BankTest {

    private Bank bank;
    private Account account1;
    private Account account2;

    @BeforeEach
    public void setUp() {
        bank = new Bank();
        account1 = bank.createAccount("A1", 100.0);
        account2 = bank.createAccount("A2", 200.0);
    }

    @Test
    @Order(1)
    public void testCreateAccount() {
        Account account = bank.createAccount("A3", 300.0);
        assertNotNull(account);
        assertEquals("A3", account.getAccountId());
        assertEquals(300.0, account.getBalance());
    }

    @Test
    @Order(2)
    public void testCreateAccount_InvalidAccountId() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> bank.createAccount(null, 100.0));
        assertEquals("Account ID must not be null and Initial balance must be non-negative", exception.getMessage());
    }

    @Test
    @Order(3)
    public void testCreateAccount_InvalidInitialBalance() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> bank.createAccount("A4", -100.0));
        assertEquals("Account ID must not be null and Initial balance must be non-negative", exception.getMessage());
    }

    @Test
    @Order(4)
    public void testCreateAccount_AccountAlreadyExists() {
        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> bank.createAccount("A1", 500.0));
        assertEquals("Account already exists", exception.getMessage());
    }

    @Test
    @Order(5)
    public void testGetAccount() {
        Account account = bank.getAccount("A1");
        assertNotNull(account);
        assertEquals("A1", account.getAccountId());
        assertEquals(100.0, account.getBalance());
    }

    @Test
    @Order(6)
    public void testGetAccount_InvalidAccountId() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> bank.getAccount(null));
        assertEquals("Account ID must not be null", exception.getMessage());
    }

    @Test
    @Order(7)
    public void testGetAccount_AccountDoesNotExist() {
        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> bank.getAccount("A3"));
        assertEquals("Account does not exist", exception.getMessage());
    }

    @Test
    @Order(8)
    public void testDeleteAccount() {
        bank.deleteAccount("A1");
        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> bank.getAccount("A1"));
        assertEquals("Account does not exist", exception.getMessage());
    }

    @Test
    @Order(9)
    public void testDeleteAccount_InvalidAccountId() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> bank.deleteAccount(null));
        assertEquals("Account ID must not be null", exception.getMessage());
    }

    @Test
    @Order(10)
    public void testDeleteAccount_AccountDoesNotExist() {
        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> bank.deleteAccount("A3"));
        assertEquals("Account does not exist", exception.getMessage());
    }

    @Test
    @Order(11)
    public void testTransfer() {
        bank.transfer("A1", "A2", 50.0);
        assertEquals(50.0, account1.getBalance());
        assertEquals(250.0, account2.getBalance());
    }

    @Test
    @Order(12)
    public void testTransfer_InvalidAmount() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> bank.transfer("A1", "A2", -50.0));
        assertEquals("Transfer amount must be positive", exception.getMessage());
    }

    @Test
    @Order(13)
    public void testTransfer_InsufficientFunds() {
        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> bank.transfer("A1", "A2", 150.0));
        assertEquals("Insufficient funds", exception.getMessage());
    }
}