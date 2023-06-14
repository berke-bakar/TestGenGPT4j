package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import java.util.Random;

public class BankTest {

    // Test data
    private static final String[] ACCOUNT_IDS = {"001", "002", "003", "004", "005"};
    private static final int MAX_BALANCE = 10000;
    private static final double TRANSFER_AMOUNT = 100;

    // Random number generator for test data
    private static final Random random = new Random();

    // Bank instance to be tested
    private final Bank bank = new Bank();

    @Test
    @DisplayName("Test create account with valid input")
    public void testCreateAccountValid() {
        String accountId = ACCOUNT_IDS[random.nextInt(ACCOUNT_IDS.length)];
        double initialBalance = random.nextDouble() * MAX_BALANCE;

        Account account = bank.createAccount(accountId, initialBalance);

        Assertions.assertEquals(account.getAccountId(), accountId);
        Assertions.assertEquals(account.getBalance(), initialBalance);
    }

    @Test
    @DisplayName("Test create account with null ID")
    public void testCreateAccountNullID() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            bank.createAccount(null, random.nextDouble() * MAX_BALANCE);
        });
    }

    @Test
    @DisplayName("Test create account with negative initial balance")
    public void testCreateAccountNegativeBalance() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            bank.createAccount(ACCOUNT_IDS[random.nextInt(ACCOUNT_IDS.length)], -random.nextDouble() * MAX_BALANCE);
        });
    }

    @Test
    @DisplayName("Test create account with existing ID")
    public void testCreateAccountExistingID() {
        String accountId = ACCOUNT_IDS[random.nextInt(ACCOUNT_IDS.length)];
        double initialBalance = random.nextDouble() * MAX_BALANCE;

        bank.createAccount(accountId, initialBalance);

        Assertions.assertThrows(IllegalStateException.class, () -> {
            bank.createAccount(accountId, random.nextDouble() * MAX_BALANCE);
        });
    }

    @Test
    @DisplayName("Test get account with valid input")
    public void testGetAccountValid() {
        String accountId = ACCOUNT_IDS[random.nextInt(ACCOUNT_IDS.length)];
        double initialBalance = random.nextDouble() * MAX_BALANCE;

        bank.createAccount(accountId, initialBalance);

        Account account = bank.getAccount(accountId);

        Assertions.assertEquals(account.getAccountId(), accountId);
        Assertions.assertEquals(account.getBalance(), initialBalance);
    }

    @Test
    @DisplayName("Test get account with null ID")
    public void testGetAccountNullID() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            bank.getAccount(null);
        });
    }

    @Test
    @DisplayName("Test get account with non-existing ID")
    public void testGetAccountNonExistingID() {
        Assertions.assertThrows(IllegalStateException.class, () -> {
            bank.getAccount(ACCOUNT_IDS[random.nextInt(ACCOUNT_IDS.length)]);
        });
    }

    @Test
    @DisplayName("Test delete account with valid input")
    public void testDeleteAccountValid() {
        String accountId = ACCOUNT_IDS[random.nextInt(ACCOUNT_IDS.length)];
        double initialBalance = random.nextDouble() * MAX_BALANCE;

        bank.createAccount(accountId, initialBalance);

        bank.deleteAccount(accountId);

        Assertions.assertThrows(IllegalStateException.class, () -> {
            bank.getAccount(accountId);
        });
    }

    @Test
    @DisplayName("Test delete account with null ID")
    public void testDeleteAccountNullID() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            bank.deleteAccount(null);
        });
    }

    @Test
    @DisplayName("Test delete account with non-existing ID")
    public void testDeleteAccountNonExistingID() {
        Assertions.assertThrows(IllegalStateException.class, () -> {
            bank.deleteAccount(ACCOUNT_IDS[random.nextInt(ACCOUNT_IDS.length)]);
        });
    }

    @Test
    @DisplayName("Test transfer with valid input")
    public void testTransferValid() {
        // Create two accounts with random initial balances
        String fromAccountId = ACCOUNT_IDS[random.nextInt(ACCOUNT_IDS.length)];
        double fromInitialBalance = random.nextDouble() * MAX_BALANCE;
        Account fromAccount = bank.createAccount(fromAccountId, fromInitialBalance);

        String toAccountId = ACCOUNT_IDS[random.nextInt(ACCOUNT_IDS.length)];
        double toInitialBalance = random.nextDouble() * MAX_BALANCE;
        Account toAccount = bank.createAccount(toAccountId, toInitialBalance);

        // Transfer an amount between the accounts
        double transferAmount = TRANSFER_AMOUNT;
        bank.transfer(fromAccountId, toAccountId, transferAmount);

        // Check account balances and transaction history
        Assertions.assertEquals(fromAccount.getBalance(), fromInitialBalance - transferAmount);
        Assertions.assertEquals(toAccount.getBalance(), toInitialBalance + transferAmount);
        Assertions.assertEquals(fromAccount.getTransactions().size(), 1);
        Assertions.assertEquals(toAccount.getTransactions().size(), 1);
        Transaction transaction = fromAccount.getTransactions().get(0);
        Assertions.assertEquals(transaction.getFromAccountId(), fromAccountId);
        Assertions.assertEquals(transaction.getToAccountId(), toAccountId);
        Assertions.assertEquals(transaction.getAmount(), transferAmount);
    }

    @Test
    @DisplayName("Test transfer with non-positive amount")
    public void testTransferNonPositiveAmount() {
        // Create two accounts with random initial balances
        String fromAccountId = ACCOUNT_IDS[random.nextInt(ACCOUNT_IDS.length)];
        double fromInitialBalance = random.nextDouble() * MAX_BALANCE;
        Account fromAccount = bank.createAccount(fromAccountId, fromInitialBalance);

        String toAccountId = ACCOUNT_IDS[random.nextInt(ACCOUNT_IDS.length)];
        double toInitialBalance = random.nextDouble() * MAX_BALANCE;
        Account toAccount = bank.createAccount(toAccountId, toInitialBalance);

        // Transfer a non-positive amount between the accounts
        double transferAmount = -random.nextDouble() * MAX_BALANCE;
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            bank.transfer(fromAccountId, toAccountId, transferAmount);
        });

        // Check that account balances and transaction history are unchanged
        Assertions.assertEquals(fromAccount.getBalance(), fromInitialBalance);
        Assertions.assertEquals(toAccount.getBalance(), toInitialBalance);
        Assertions.assertEquals(fromAccount.getTransactions().size(), 0);
        Assertions.assertEquals(toAccount.getTransactions().size(), 0);
    }

    @Test
    @DisplayName("Test transfer with insufficient funds")
    public void testTransferInsufficientFunds() {
        // Create two accounts with random initial balances
        String fromAccountId = ACCOUNT_IDS[random.nextInt(ACCOUNT_IDS.length)];
        double fromInitialBalance = random.nextDouble() * MAX_BALANCE;
        Account fromAccount = bank.createAccount(fromAccountId, fromInitialBalance);

        String toAccountId = ACCOUNT_IDS[random.nextInt(ACCOUNT_IDS.length)];
        double toInitialBalance = random.nextDouble() * MAX_BALANCE;
        Account toAccount = bank.createAccount(toAccountId, toInitialBalance);

        // Transfer an amount greater than the balance of the 'from' account
        double transferAmount = fromInitialBalance + random.nextDouble() * MAX_BALANCE;
        Assertions.assertThrows(IllegalStateException.class, () -> {
            bank.transfer(fromAccountId, toAccountId, transferAmount);
        });

        // Check that account balances and transaction history are unchanged
        Assertions.assertEquals(fromAccount.getBalance(), fromInitialBalance);
        Assertions.assertEquals(toAccount.getBalance(), toInitialBalance);
        Assertions.assertEquals(fromAccount.getTransactions().size(), 0);
        Assertions.assertEquals(toAccount.getTransactions().size(), 0);
    }
}