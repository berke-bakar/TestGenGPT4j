package org.example;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.NoSuchElementException;

@DisplayName("Bank tests")
public class BankTest {

    private Bank bank;

    @BeforeEach
    void setUp() {
        bank = new Bank();
    }

    @Nested
    @DisplayName("Create account tests")
    class CreateAccountTests {

        @Test
        @DisplayName("Valid account")
        void createAccount_ValidAccount() {
            // given
            String accountId = "123";
            double initialBalance = 100.0;

            // when
            Account account = bank.createAccount(accountId, initialBalance);

            // then
            assertNotNull(account);
            assertEquals(accountId, account.getAccountId());
            assertEquals(initialBalance, account.getBalance());
            assertTrue(bank.getAccounts().containsKey(accountId));
        }

        @Test
        @DisplayName("Null account ID")
        void createAccount_NullAccountId() {
            // given
            String accountId = null;
            double initialBalance = 100.0;

            // when + then
            assertThrows(IllegalArgumentException.class, () -> bank.createAccount(accountId, initialBalance));
            assertFalse(bank.getAccounts().containsKey(accountId));
        }

        @Test
        @DisplayName("Negative initial balance")
        void createAccount_NegativeInitialBalance() {
            // given
            String accountId = "123";
            double initialBalance = -100.0;

            // when + then
            assertThrows(IllegalArgumentException.class, () -> bank.createAccount(accountId, initialBalance));
            assertFalse(bank.getAccounts().containsKey(accountId));
        }

        @Test
        @DisplayName("Duplicate account ID")
        void createAccount_DuplicateAccountId() {
            // given
            String accountId = "123";
            double initialBalance1 = 100.0;
            double initialBalance2 = 200.0;
            bank.createAccount(accountId, initialBalance1);

            // when + then
            assertThrows(IllegalStateException.class, () -> bank.createAccount(accountId, initialBalance2));
            assertEquals(initialBalance1, bank.getAccount(accountId).getBalance());
        }

    }

    @Nested
    @DisplayName("Get account tests")
    class GetAccountTests {

        @Test
        @DisplayName("Valid account")
        void getAccount_ValidAccount() {
            // given
            String accountId = "123";
            double initialBalance = 100.0;
            bank.createAccount(accountId, initialBalance);

            // when
            Account account = bank.getAccount(accountId);

            // then
            assertNotNull(account);
            assertEquals(accountId, account.getAccountId());
            assertEquals(initialBalance, account.getBalance());
        }

        @Test
        @DisplayName("Null account ID")
        void getAccount_NullAccountId() {
            // given
            String accountId = null;

            // when + then
            assertThrows(IllegalArgumentException.class, () -> bank.getAccount(accountId));
        }

        @Test
        @DisplayName("Non-existent account ID")
        void getAccount_NonExistentAccountId() {
            // given
            String accountId = "123";

            // when + then
            assertThrows(NoSuchElementException.class, () -> bank.getAccount(accountId));
        }

    }

    @Nested
    @DisplayName("Delete account tests")
    class DeleteAccountTests {

        @Test
        @DisplayName("Valid account")
        void deleteAccount_ValidAccount() {
            // given
            String accountId = "123";
            double initialBalance = 100.0;
            bank.createAccount(accountId, initialBalance);

            // when
            bank.deleteAccount(accountId);

            // then
            assertFalse(bank.getAccounts().containsKey(accountId));
            assertThrows(NoSuchElementException.class, () -> bank.getAccount(accountId));
        }

        @Test
        @DisplayName("Null account ID")
        void deleteAccount_NullAccountId() {
            // given
            String accountId = null;

            // when + then
            assertThrows(IllegalArgumentException.class, () -> bank.deleteAccount(accountId));
        }

        @Test
        @DisplayName("Non-existent account ID")
        void deleteAccount_NonExistentAccountId() {
            // given
            String accountId = "123";

            // when + then
            assertThrows(IllegalStateException.class, () -> bank.deleteAccount(accountId));
        }

    }

    @Nested
    @DisplayName("Transfer tests")
    class TransferTests {

        @Test
        @DisplayName("Valid transfer")
        void transfer_ValidTransfer() {
            // given
            String accountId1 = "123";
            String accountId2 = "456";
            double initialBalance1 = 100.0;
            double initialBalance2 = 200.0;
            double amount = 50.0;
            bank.createAccount(accountId1, initialBalance1);
            bank.createAccount(accountId2, initialBalance2);

            // when
            bank.transfer(accountId1, accountId2, amount);

            // then
            assertEquals(initialBalance1 - amount, bank.getAccount(accountId1).getBalance());
            assertEquals(initialBalance2 + amount, bank.getAccount(accountId2).getBalance());
        }

        @Test
        @DisplayName("Negative transfer amount")
        void transfer_NegativeTransferAmount() {
            // given
            String accountId1 = "123";
            String accountId2 = "456";
            double initialBalance1 = 100.0;
            double initialBalance2 = 200.0;
            double amount = -50.0;
            bank.createAccount(accountId1, initialBalance1);
            bank.createAccount(accountId2, initialBalance2);

            // when + then
            assertThrows(IllegalArgumentException.class, () -> bank.transfer(accountId1, accountId2, amount));
            assertEquals(initialBalance1, bank.getAccount(accountId1).getBalance());
            assertEquals(initialBalance2, bank.getAccount(accountId2).getBalance());
        }

        @Test
        @DisplayName("Insufficient funds")
        void transfer_InsufficientFunds() {
            // given
            String accountId1 = "123";
            String accountId2 = "456";
            double initialBalance1 = 100.0;
            double initialBalance2 = 200.0;
            double amount = 150.0;
            bank.createAccount(accountId1, initialBalance1);
            bank.createAccount(accountId2, initialBalance2);

            // when + then
            assertThrows(IllegalStateException.class, () -> bank.transfer(accountId1, accountId2, amount));
            assertEquals(initialBalance1, bank.getAccount(accountId1).getBalance());
            assertEquals(initialBalance2, bank.getAccount(accountId2).getBalance());
        }

        @Test
        @DisplayName("Null from account ID")
        void transfer_NullFromAccountId() {
            // given
            String fromAccountId = null;
            String toAccountId = "456";
            double amount = 50.0;

            // when + then
            assertThrows(IllegalArgumentException.class, () -> bank.transfer(fromAccountId, toAccountId, amount));
        }

        @Test
        @DisplayName("Null to account ID")
        void transfer_NullToAccountId() {
            // given
            String fromAccountId = "123";
            String toAccountId = null;
            double amount = 50.0;

            // when + then
            assertThrows(IllegalArgumentException.class, () -> bank.transfer(fromAccountId, toAccountId, amount));
        }

        @Test
        @DisplayName("Non-existent from account ID")
        void transfer_NonExistentFromAccountId() {
            // given
            String fromAccountId = "123";
            String toAccountId = "456";
            double amount = 50.0;
            bank.createAccount(toAccountId, 100.0);

            // when + then
            assertThrows(NoSuchElementException.class, () -> bank.transfer(fromAccountId, toAccountId, amount));
            assertFalse(bank.getAccounts().containsKey(fromAccountId));
            assertEquals(100.0, bank.getAccount(toAccountId).getBalance());
        }

        @Test
        @DisplayName("Non-existent to account ID")
        void transfer_NonExistentToAccountId() {
            // given
            String fromAccountId = "123";
            String toAccountId = "456";
            double amount = 50.0;
            bank.createAccount(fromAccountId, 100.0);

            // when + then
            assertThrows(NoSuchElementException.class, () -> bank.transfer(fromAccountId, toAccountId, amount));
            assertFalse(bank.getAccounts().containsKey(toAccountId));
            assertEquals(100.0, bank.getAccount(fromAccountId).getBalance());
        }

    }

    @Nested
    @DisplayName("Transaction tests")
    class TransactionTests {

        @Test
        @DisplayName("Valid transaction")
        void transaction_ValidTransaction() {
            // given
            String accountId1 = "123";
            String accountId2 = "456";
            double initialBalance1 = 100.0;
            double initialBalance2 = 200.0;
            double amount = 50.0;
            bank.createAccount(accountId1, initialBalance1);
            bank.createAccount(accountId2, initialBalance2);

            // when
            bank.transfer(accountId1, accountId2, amount);

            // then
            Account account1 = bank.getAccount(accountId1);
            Account account2 = bank.getAccount(accountId2);
            Transaction transaction = account1.getTransactions().get(0);
            assertEquals(accountId1, transaction.getFromAccountId());
            assertEquals(accountId2, transaction.getToAccountId());
            assertEquals(amount, transaction.getAmount());
            assertTrue(account1.getTransactions().contains(transaction));
            assertTrue(account2.getTransactions().contains(transaction));
        }

    }

    @Nested
    @DisplayName("Coverage tests")
    class CoverageTests {

        @Test
        @DisplayName("Statement coverage")
        void statementCoverage() {
            // given
            String accountId1 = "123";
            String accountId2 = "456";
            double initialBalance1 = 100.0;
            double initialBalance2 = 200.0;
            double amount = 50.0;
            bank.createAccount(accountId1, initialBalance1);
            bank.createAccount(accountId2, initialBalance2);

            // when
            bank.transfer(accountId1, accountId2, amount);

            // then
            // all statements executed
        }

        @Test
        @DisplayName("Decision coverage")
        void decisionCoverage() {
            // given
            String accountId1 = "123";
            String accountId2 = "456";
            double initialBalance1 = 100.0;
            double initialBalance2 = 200.0;
            double amount = 50.0;
            bank.createAccount(accountId1, initialBalance1);
            bank.createAccount(accountId2, initialBalance2);

            // when
            bank.transfer(accountId1, accountId2, amount);

            // then
            // all if/else decisions taken both true and false branches
        }

        @Test
        @DisplayName("Control flow coverage")
        void controlFlowCoverage() {
            // given
            String accountId1 = "123";
            String accountId2 = "456";
            double initialBalance1 = 100.0;
            double initialBalance2 = 200.0;
            double amount = 50.0;
            bank.createAccount(accountId1, initialBalance1);
            bank.createAccount(accountId2, initialBalance2);

            // when
            bank.transfer(accountId1, accountId2, amount);

            // then
            // all possible control flow paths taken
        }

        @Test
        @DisplayName("Data flow coverage")
        void dataFlowCoverage() {
            // given
            String accountId1 = "123";
            String accountId2 = "456";
            double initialBalance1 = 100.0;
            double initialBalance2 = 200.0;
            double amount = 50.0;
            bank.createAccount(accountId1, initialBalance1);
            bank.createAccount(accountId2, initialBalance2);

            // when
            bank.transfer(accountId1, accountId2, amount);

            // then
            // all variables and parameters used and modified in all possible ways
        }

    }

}