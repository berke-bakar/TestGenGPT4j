import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

class BankTest {
    Bank bank;

    @BeforeEach
    void setup() {
        bank = new Bank();
    }

    @Test
    void testCreateAccount() {
        bank.createAccount("1", 100);
        bank.createAccount("2", 0);
        bank.createAccount("3", -100);
    }

    @Test
    void testCreateAccount_shouldThrowExceptionForNullAccountId() {
        assertThrows(IllegalArgumentException.class, () ->
                bank.createAccount(null, 100)
        );
    }

    @Test
    void testCreateAccount_shouldThrowExceptionForNegativeInitialBalance() {
        assertThrows(IllegalArgumentException.class, () ->
                bank.createAccount("1", -100)
        );
    }

    @Test
    void testCreateAccount_shouldThrowExceptionForExistingAccountId() {
        bank.createAccount("1", 100);
        assertThrows(IllegalStateException.class, () ->
                bank.createAccount("1", 0)
        );
    }

    @Test
    void testGetAccount() {
        bank.createAccount("1", 100);
        Account account = bank.getAccount("1");
        assertEquals("1", account.getAccountId());
        assertEquals(100, account.getBalance(), 0.001);
    }

    @Test
    void testGetAccount_shouldThrowExceptionForNullAccountId() {
        assertThrows(IllegalArgumentException.class, () ->
                bank.getAccount(null)
        );
    }

    @Test
    void testGetAccount_shouldThrowExceptionForNonExistingAccountId() {
        assertThrows(IllegalStateException.class, () ->
                bank.getAccount("1")
        );
    }

    @Test
    void testDeleteAccount() {
        bank.createAccount("1", 100);
        bank.deleteAccount("1");
    }

    @Test
    void testDeleteAccount_shouldThrowExceptionForNullAccountId() {
        assertThrows(IllegalArgumentException.class, () ->
                bank.deleteAccount(null)
        );
    }

    @Test
    void testDeleteAccount_shouldThrowExceptionForNonExistingAccountId() {
        assertThrows(IllegalStateException.class, () ->
                bank.deleteAccount("1")
        );
    }

    @Test
    void testTransfer() {
        bank.createAccount("1", 100);
        bank.createAccount("2", 0);
        bank.transfer("1", "2", 50);
    }

    @Test
    void testTransfer_shouldThrowExceptionForNullFromAccountId() {
        bank.createAccount("2", 0);
        assertThrows(IllegalArgumentException.class, () ->
                bank.transfer(null, "2", 50)
        );
    }

    @Test
    void testTransfer_shouldThrowExceptionForNullToAccountId() {
        bank.createAccount("1", 100);
        assertThrows(IllegalArgumentException.class, () ->
                bank.transfer("1", null, 50)
        );
    }

    @Test
    void testTransfer_shouldThrowExceptionForNegativeAmount() {
        bank.createAccount("1", 100);
        bank.createAccount("2", 0);
        assertThrows(IllegalArgumentException.class, () ->
                bank.transfer("1", "2", -50)
        );
    }

    @Test
    void testTransfer_shouldThrowExceptionForNonExistingFromAccountId() {
        bank.createAccount("2", 0);
        assertThrows(IllegalStateException.class, () ->
                bank.transfer("1", "2", 50)
        );
    }

    @Test
    void testTransfer_shouldThrowExceptionForNonExistingToAccountId() {
        bank.createAccount("1", 100);
        assertThrows(IllegalStateException.class, () ->
                bank.transfer("1", "2", 50)
        );
    }

    @Test
    void testTransfer_shouldThrowExceptionForInsufficientFunds() {
        bank.createAccount("1", 100);
        bank.createAccount("2", 0);
        assertThrows(IllegalStateException.class, () ->
                bank.transfer("1", "2", 150)
        );
    }
}