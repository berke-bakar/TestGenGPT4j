import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class BankTest {

    @Test
    public void testCreateAccount_nullAccountId_expectIllegalArgumentException() {
        Bank bank = new Bank();
        assertThrows(IllegalArgumentException.class, () -> {
            bank.createAccount(null, 100.0);
        });
    }

    @Test
    public void testCreateAccount_negativeInitialBalance_expectIllegalArgumentException() {
        Bank bank = new Bank();
        assertThrows(IllegalArgumentException.class, () -> {
            bank.createAccount("12345", -50.0);
        });
    }

    @Test
    public void testCreateAccount_validParameters_expectSuccessfulAccountCreation() {
        Bank bank = new Bank();
        Account account = bank.createAccount("12345", 100.0);
        assertNotNull(account);
        assertEquals("12345", account.getAccountId());
        assertEquals(100.0, account.getBalance());
    }

    @Test
    public void testCreateAccount_duplicateAccountId_expectIllegalStateException() {
        Bank bank = new Bank();
        bank.createAccount("12345", 100.0);
        assertThrows(IllegalStateException.class, () -> {
            bank.createAccount("12345", 100.0);
        });
    }

    @Test
    public void testGetAccount_nullAccountId_expectIllegalArgumentException() {
        Bank bank = new Bank();
        assertThrows(IllegalArgumentException.class, () -> {
            bank.getAccount(null);
        });
    }

    @Test
    public void testGetAccount_nonexistentAccountId_expectIllegalStateException() {
        Bank bank = new Bank();
        assertThrows(IllegalStateException.class, () -> {
            bank.getAccount("12345");
        });
    }

    @Test
    public void testGetAccount_validAccountId_expectAccountObject() {
        Bank bank = new Bank();
        bank.createAccount("12345", 100.0);
        Account account = bank.getAccount("12345");
        assertNotNull(account);
        assertEquals("12345", account.getAccountId());
        assertEquals(100.0, account.getBalance());
    }

    @Test
    public void testDeleteAccount_nullAccountId_expectIllegalArgumentException() {
        Bank bank = new Bank();
        assertThrows(IllegalArgumentException.class, () -> {
            bank.deleteAccount(null);
        });
    }

    @Test
    public void testDeleteAccount_nonexistentAccountId_expectIllegalStateException() {
        Bank bank = new Bank();
        assertThrows(IllegalStateException.class, () -> {
            bank.deleteAccount("12345");
        });
    }

    @Test
    public void testDeleteAccount_validAccountId_expectSuccessfulAccountDeletion() {
        Bank bank = new Bank();
        bank.createAccount("12345", 100.0);
        bank.deleteAccount("12345");
        assertThrows(IllegalStateException.class, () -> {
            bank.getAccount("12345");
        });
    }

    @ParameterizedTest
    @ValueSource(doubles = {-1.0, 0.0})
    public void testTransfer_invalidAmount_expectIllegalArgumentException(double amount) {
        Bank bank = new Bank();
        bank.createAccount("12345", 100.0);
        bank.createAccount("56789", 50.0);
        assertThrows(IllegalArgumentException.class, () -> {
            bank.transfer("12345", "56789", amount);
        });
    }

    @Test
    public void testTransfer_insufficientFunds_expectIllegalStateException() {
        Bank bank = new Bank();
        bank.createAccount("12345", 50.0);
        bank.createAccount("56789", 50.0);
        assertThrows(IllegalStateException.class, () -> {
            bank.transfer("12345", "56789", 100.0);
        });
    }

    @Test
    public void testTransfer_validParameters_expectSuccessfulTransaction() {
        Bank bank = new Bank();
        bank.createAccount("12345", 100.0);
        bank.createAccount("56789", 50.0);
        bank.transfer("12345", "56789", 50.0);

        Account fromAccount = bank.getAccount("12345");
        Account toAccount = bank.getAccount("56789");
        assertEquals(50.0, fromAccount.getBalance());
        assertEquals(100.0, toAccount.getBalance());

        List<Transaction> fromTransactions = fromAccount.transactions;
        List<Transaction> toTransactions = toAccount.transactions;
        assertEquals(1, fromTransactions.size());
        assertEquals(1, toTransactions.size());

        Transaction fromTransaction = fromTransactions.get(0);
        Transaction toTransaction = toTransactions.get(0);
        assertEquals("12345", fromTransaction.getFromAccountId());
        assertEquals("56789", fromTransaction.getToAccountId());
        assertEquals(50.0, fromTransaction.getAmount());
        assertEquals(fromTransaction, toTransaction);
    }
}