import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

public class BankTest {

    private Bank bank;

    @BeforeEach
    public void setup() {
        bank = new Bank();
    }

    @Test
    public void testCreateAccount() {
        // Tests the createAccount method
        // Checks the following:
        // 1. Creating an account with a valid account ID and initial balance succeeds
        // 2. Creating an account with a null account ID fails
        // 3. Creating an account with a negative initial balance fails
        // 4. Creating an account with an account ID that is already in use fails

        // Test 1
        String accountId = "123456";
        double initialBalance = 100.0;
        bank.createAccount(accountId, initialBalance);

        // Test 2
        accountId = null;
        initialBalance = 100.0;
        assertThrows(IllegalArgumentException.class, () -> bank.createAccount(accountId, initialBalance));

        // Test 3
        accountId = "123456";
        initialBalance = -100.0;
        assertThrows(IllegalArgumentException.class, () -> bank.createAccount(accountId, initialBalance));

        // Test 4
        accountId = "123456";
        initialBalance = 100.0;
        bank.createAccount(accountId, initialBalance);
        assertThrows(IllegalStateException.class, () -> bank.createAccount(accountId, initialBalance));
    }

    @Test
    public void testGetAccount() {
        // Tests the getAccount method
        // Checks the following:
        // 1. Getting an account that exists succeeds
        // 2. Getting an account with a null account ID fails
        // 3. Getting an account that does not exist fails

        // Test 1
        String accountId = "123456";
        double initialBalance = 100.0;
        bank.createAccount(accountId, initialBalance);
        bank.getAccount(accountId);

        // Test 2
        accountId = null;
        assertThrows(IllegalArgumentException.class, () -> bank.getAccount(accountId));

        // Test 3
        accountId = "123456";
        assertThrows(IllegalStateException.class, () -> bank.getAccount(accountId));
    }

    @Test
    public void testDeleteAccount() {
        // Tests the deleteAccount method
        // Checks the following:
        // 1. Deleting an account that exists succeeds
        // 2. Deleting an account with a null account ID fails
        // 3. Deleting an account that does not exist fails

        // Test 1
        String accountId = "123456";
        double initialBalance = 100.0;
        bank.createAccount(accountId, initialBalance);
        bank.deleteAccount(accountId);

        // Test 2
        accountId = null;
        assertThrows(IllegalArgumentException.class, () -> bank.deleteAccount(accountId));

        // Test 3
        accountId = "123456";
        assertThrows(IllegalStateException.class, () -> bank.deleteAccount(accountId));
    }

    @Test
    public void testTransfer() {
        // Tests the transfer method
        // Checks the following:
        // 1. Transferring a positive amount from one account to another succeeds
        // 2. Transferring a negative amount fails
        // 3. Transferring from an account with insufficient funds fails
        // 4. Transferring to or from a null account ID fails

        // Test 1
        String fromAccountId = "111111";
        String toAccountId = "222222";
        double initialBalance = 100.0;
        double amount = 50.0;
        bank.createAccount(fromAccountId, initialBalance);
        bank.createAccount(toAccountId, initialBalance);
        bank.transfer(fromAccountId, toAccountId, amount);

        // Test 2
        amount = -50.0;
        assertThrows(IllegalArgumentException.class, () -> bank.transfer(fromAccountId, toAccountId, amount));

        // Test 3
        amount = 150.0;
        assertThrows(IllegalStateException.class, () -> bank.transfer(fromAccountId, toAccountId, amount));

        // Test 4
        fromAccountId = null;
        toAccountId = "222222";
        amount = 50.0;
        assertThrows(IllegalArgumentException.class, () -> bank.transfer(fromAccountId, toAccountId, amount));

        fromAccountId = "111111";
        toAccountId = null;
        assertThrows(IllegalArgumentException.class, () -> bank.transfer(fromAccountId, toAccountId, amount));
    }
}