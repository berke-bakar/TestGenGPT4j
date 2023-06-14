import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for Bank class
 */
public class BankTest {
    private Bank bank;

    @BeforeEach
    public void setup() {
        bank = new Bank();
    }

    @Test
    public void testCreateAccount() {
        Account account = bank.createAccount("accountId", 100);
        assertTrue(bank.getAccounts().containsValue(account));
        assertEquals(1, bank.getAccounts().size());
    }

    @Test
    public void testCreateAccountInitialBalance() {
        double initialBalance = 100;
        Account account = bank.createAccount("accountId", initialBalance);
        assertEquals(initialBalance, account.getBalance(), 0);
    }

    @Test
    public void testCreateAccountIdNull() {
        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> bank.createAccount(null, 100)
        );
        assertTrue(thrown.getMessage().contains("Account ID must not be null"));
    }

    @Test
    public void testCreateAccountIdExists() {
        bank.createAccount("accountId", 0);
        IllegalStateException thrown = assertThrows(
                IllegalStateException.class,
                () -> bank.createAccount("accountId", 0)
        );
        assertTrue(thrown.getMessage().contains("Account already exists"));
    }

    @Test
    public void testCreateAccountInitialBalanceNegative() {
        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> bank.createAccount("accountId", -1)
        );
        assertTrue(thrown.getMessage().contains("Initial balance must be non-negative"));
    }

    @Test
    public void testGetAccount() {
        Account account = bank.createAccount("accountId", 0);
        assertEquals(account, bank.getAccount("accountId"));
    }

    @Test
    public void testGetAccountIdNull() {
       IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> bank.getAccount(null)
        );
        assertTrue(thrown.getMessage().contains("Account ID must not be null"));
    }

    @Test
    public void testGetAccountDoesntExist() {
        IllegalStateException thrown = assertThrows(
                IllegalStateException.class,
                () -> bank.getAccount("accountId")
        );
        assertTrue(thrown.getMessage().contains("Account does not exist"));
    }

    @Test
    public void testDeleteAccount() {
        Account account = bank.createAccount("accountId", 0);
        bank.deleteAccount("accountId");
        assertTrue(bank.getAccounts().isEmpty());
    }

    @Test
    public void testDeleteAccountIdNull() {
        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> bank.deleteAccount(null)
        );
        assertTrue(thrown.getMessage().contains("Account ID must not be null"));
    }

    @Test
    public void testDeleteAccountDoesntExist() {
        IllegalStateException thrown = assertThrows(
                IllegalStateException.class,
                () -> bank.deleteAccount("accountId")
        );
        assertTrue(thrown.getMessage().contains("Account does not exist"));
    }

    @Test
    public void testTransfer() {
        String fromAccountId = "fromAccountId";
        String toAccountId = "toAccountId";
        bank.createAccount(fromAccountId, 100);
        bank.createAccount(toAccountId, 0);
        double amount = 50;
        bank.transfer(fromAccountId, toAccountId, amount);

        Account fromAccount = bank.getAccount(fromAccountId);
        assertEquals(50, fromAccount.getBalance(), 0);

        Account toAccount = bank.getAccount(toAccountId);
        assertEquals(50, toAccount.getBalance(), 0);

        assertEquals(1, fromAccount.getTransactions().size());
        Transaction fromTransaction = fromAccount.getTransactions().get(0);
        assertEquals(fromAccountId, fromTransaction.getFromAccountId());
        assertEquals(toAccountId, fromTransaction.getToAccountId());
        assertEquals(amount, fromTransaction.getAmount(), 0);

        assertEquals(1, toAccount.getTransactions().size());
        Transaction toTransaction = toAccount.getTransactions().get(0);
        assertEquals(fromAccountId, toTransaction.getFromAccountId());
        assertEquals(toAccountId, toTransaction.getToAccountId());
        assertEquals(amount, toTransaction.getAmount(), 0);
    }

    @Test
    public void testTransferAmountPositive() {
        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> bank.transfer("fromAccountId", "toAccountId", -1)
        );
        assertTrue(thrown.getMessage().contains("Transfer amount must be positive"));
    }

    @Test
    public void testTransferInsufficientFunds() {
        bank.createAccount("accountId", 50);
        IllegalStateException thrown = assertThrows(
                IllegalStateException.class,
                () -> bank.transfer("accountId", "toAccountId", 100)
        );
        assertTrue(thrown.getMessage().contains("Insufficient funds"));
    }
}