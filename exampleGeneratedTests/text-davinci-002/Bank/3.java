import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class BankTest {

    @Test
    public void testCreateAccount() {
        Bank bank = new Bank();
        String accountId = "1";
        double initialBalance = 100;
        Account account = bank.createAccount(accountId, initialBalance);
        assertEquals(accountId, account.getAccountId());
        assertEquals(initialBalance, account.getBalance());
    }

    @Test
    public void testCreateAccount_AccountIdIsNull() {
        Bank bank = new Bank();
        String accountId = null;
        double initialBalance = 100;
        assertThrows(IllegalArgumentException.class,
                () -> bank.createAccount(accountId, initialBalance));
    }

    @Test
    public void testCreateAccount_InitialBalanceIsNegative() {
        Bank bank = new Bank();
        String accountId = "1";
        double initialBalance = -100;
        assertThrows(IllegalArgumentException.class,
                () -> bank.createAccount(accountId, initialBalance));
    }

    @Test
    public void testCreateAccount_AccountAlreadyExists() {
        Bank bank = new Bank();
        String accountId = "1";
        double initialBalance = 100;
        bank.createAccount(accountId, initialBalance);
        assertThrows(IllegalStateException.class,
                () -> bank.createAccount(accountId, initialBalance));
    }

    @Test
    public void testGetAccount() {
        Bank bank = new Bank();
        String accountId = "1";
        double initialBalance = 100;
        bank.createAccount(accountId, initialBalance);
        Account account = bank.getAccount(accountId);
        assertEquals(accountId, account.getAccountId());
        assertEquals(initialBalance, account.getBalance());
    }

    @Test
    public void testGetAccount_AccountIdIsNull() {
        Bank bank = new Bank();
        String accountId = null;
        assertThrows(IllegalArgumentException.class,
                () -> bank.getAccount(accountId));
    }

    @Test
    public void testGetAccount_AccountDoesNotExist() {
        Bank bank = new Bank();
        String accountId = "1";
        assertThrows(IllegalStateException.class,
                () -> bank.getAccount(accountId));
    }

    @Test
    public void testDeleteAccount() {
        Bank bank = new Bank();
        String accountId = "1";
        double initialBalance = 100;
        bank.createAccount(accountId, initialBalance);
        assertEquals(1, bank.accounts.size());
        bank.deleteAccount(accountId);
        assertEquals(0, bank.accounts.size());
    }

    @Test
    public void testDeleteAccount_AccountIdIsNull() {
        Bank bank = new Bank();
        String accountId = null;
        assertThrows(IllegalArgumentException.class,
                () -> bank.deleteAccount(accountId));
    }

    @Test
    public void testDeleteAccount_AccountDoesNotExist() {
        Bank bank = new Bank();
        String accountId = "1";
        assertThrows(IllegalStateException.class,
                () -> bank.deleteAccount(accountId));
    }

    @Test
    public void testTransfer() {
        Bank bank = new Bank();
        String fromAccountId = "1";
        String toAccountId = "2";
        double initialBalance = 100;
        bank.createAccount(fromAccountId, initialBalance);
        bank.createAccount(toAccountId, initialBalance);
        double amount = 50;
        bank.transfer(fromAccountId, toAccountId, amount);
        assertEquals(50, bank.getAccount(fromAccountId).getBalance());
        assertEquals(150, bank.getAccount(toAccountId).getBalance());
    }

    @Test
    public void testTransfer_FromAccountIdIsNull() {
        Bank bank = new Bank();
        String fromAccountId = null;
        String toAccountId = "2";
        double initialBalance = 100;
        bank.createAccount(toAccountId, initialBalance);
        double amount = 50;
        assertThrows(IllegalArgumentException.class,
                () -> bank.transfer(fromAccountId, toAccountId, amount));
    }

    @Test
    public void testTransfer_ToAccountIdIsNull() {
        Bank bank = new Bank();
        String fromAccountId = "1";
        String toAccountId = null;
        double initialBalance = 100;
        bank.createAccount(fromAccountId, initialBalance);
        double amount = 50;
        assertThrows(IllegalArgumentException.class,
                () -> bank.transfer(fromAccountId, toAccountId, amount));
    }

    @Test
    public void testTransfer_AmountIsZero() {
        Bank bank = new Bank();
        String fromAccountId = "1";
        String toAccountId = "2";
        double initialBalance = 100;
        bank.createAccount(fromAccountId, initialBalance);
        bank.createAccount(toAccountId, initialBalance);
        double amount = 0;
        bank.transfer(fromAccountId, toAccountId, amount);
        assertEquals(100, bank.getAccount(fromAccountId).getBalance());
        assertEquals(100, bank.getAccount(toAccountId).getBalance());
    }

    @Test
    public void testTransfer_AmountIsNegative() {
        Bank bank = new Bank();
        String fromAccountId = "1";
        String toAccountId = "2";
        double initialBalance = 100;
        bank.createAccount(fromAccountId, initialBalance);
        bank.createAccount(toAccountId, initialBalance);
        double amount = -50;
        assertThrows(IllegalArgumentException.class,
                () -> bank.transfer(fromAccountId, toAccountId, amount));
    }

    @Test
    public void testTransfer_FromAccountDoesNotExist() {
        Bank bank = new Bank();
        String fromAccountId = "1";
        String toAccountId = "2";
        double initialBalance = 100;
        bank.createAccount(toAccountId, initialBalance);
        double amount = 50;
        assertThrows(IllegalStateException.class,
                () -> bank.transfer(fromAccountId, toAccountId, amount));
    }

    @Test
    public void testTransfer_ToAccountDoesNotExist() {
        Bank bank = new Bank();
        String fromAccountId = "1";
        String toAccountId = "2";
        double initialBalance = 100;
        bank.createAccount(fromAccountId, initialBalance);
        double amount = 50;
        assertThrows(IllegalStateException.class,
                () -> bank.transfer(fromAccountId, toAccountId, amount));
    }

    @Test
    public void testTransfer_InsufficientFunds() {
        Bank bank = new Bank();
        String fromAccountId = "1";
        String toAccountId = "2";
        double initialBalance = 100;
        bank.createAccount(fromAccountId, initialBalance);
        bank.createAccount(toAccountId, initialBalance);
        double amount = 200;
        assertThrows(IllegalStateException.class,
                () -> bank.transfer(fromAccountId, toAccountId, amount));
    }

}