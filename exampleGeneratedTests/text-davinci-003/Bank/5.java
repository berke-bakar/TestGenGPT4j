//JUnit5 Unit test class
import org.example.Bank;
import org.example.Account;
import org.example.Transaction;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import java.util.Map;

@TestMethodOrder(OrderAnnotation.class)
class BankTest {
    Bank bank;

    @BeforeEach
    void init() {
        bank = new Bank();
    }

     // Test 1: createAccount()
     @Test
     @Order(1)
     @DisplayName("createAccount() successful")
     public void testCreateAccountSuccessful() {
         String accountId = "12345";
         double initialBalance = 1000;

         Account account = bank.createAccount(accountId, initialBalance);

         assertNotNull(account);
         assertEquals(accountId, account.getAccountId());
         assertEquals(initialBalance, account.getBalance());
     }

     // Test 2: createAccount()
     @Test
     @Order(2)
     @DisplayName("createAccount() - invalid accountId")
     public void testCreateAccountInvalidId() {
         String accountId = null;
         double initialBalance = 1000;

         assertThrows(IllegalArgumentException.class, () -> bank.createAccount(accountId, initialBalance));
     }

     // Test 3: createAccount()
     @Test
     @Order(3)
     @DisplayName("createAccount() - invalid balance")
     public void testCreateAccountInvalidBalance() {
         String accountId = "12345";
         double initialBalance = -1;

         assertThrows(IllegalArgumentException.class, () -> bank.createAccount(accountId, initialBalance));
     }

     // Test 4: createAccount()
     @Test
     @Order(4)
     @DisplayName("createAccount() - account already exists")
     public void testCreateAccountAlreadyExists() {
         String accountId = "12345";
         double initialBalance = 1000;

         bank.createAccount(accountId, initialBalance);
         assertThrows(IllegalStateException.class, () -> bank.createAccount(accountId, initialBalance));
     }

     // Test 5: getAccount()
     @Test
     @Order(5)
     @DisplayName("getAccount() successful")
     public void testGetAccountSuccessful() {
         String accountId = "12345";
         double initialBalance = 1000;

         Account account = bank.createAccount(accountId, initialBalance);
         Account retAccount = bank.getAccount(accountId);

         assertNotNull(retAccount);
         assertEquals(account, retAccount);
     }

     // Test 6: getAccount()
     @Test
     @Order(6)
     @DisplayName("getAccount() - invalid accountId")
     public void testGetAccountInvalidId() {
         String accountId = null;

         assertThrows(IllegalArgumentException.class, () -> bank.getAccount(accountId));
     }

     // Test 7: getAccount()
     @Test
     @Order(7)
     @DisplayName("getAccount() - account does not exist")
     public void testGetAccountDoesNotExist() {
         String accountId = "12345";

         assertThrows(IllegalStateException.class, () -> bank.getAccount(accountId));
     }

     // Test 8: deleteAccount()
     @Test
     @Order(8)
     @DisplayName("deleteAccount() successful")
     public void testDeleteAccountSuccessful() {
         String accountId = "12345";
         double initialBalance = 1000;

         Account account = bank.createAccount(accountId, initialBalance);
         bank.deleteAccount(accountId);

         Map accounts = bank.getAccounts();
         assertFalse(accounts.containsKey(accountId));
     }

     // Test 9: deleteAccount()
     @Test
     @Order(9)
     @DisplayName("deleteAccount() - invalid accountId")
     public void testDeleteAccountInvalidId() {
         String accountId = null;

         assertThrows(IllegalArgumentException.class, () -> bank.deleteAccount(accountId));
     }

     // Test 10: deleteAccount()
     @Test
     @Order(10)
     @DisplayName("deleteAccount() - account does not exist")
     public void testDeleteAccountDoesNotExist() {
         String accountId = "12345";

         assertThrows(IllegalStateException.class, () -> bank.deleteAccount(accountId));
     }

     // Test 11: transfer()
     @Test
     @Order(11)
     @DisplayName("transfer() successful")
     public void testTransferSuccessful() {
         String fromAccountId = "12345";
         String toAccountId = "23456";
         double initialBalance = 1000;
         double amount = 100;

         Account fromAccount = bank.createAccount(fromAccountId, initialBalance);
         Account toAccount = bank.createAccount(toAccountId, initialBalance);

         bank.transfer(fromAccountId, toAccountId, amount);

         assertEquals(900, fromAccount.getBalance());
         assertEquals(1100, toAccount.getBalance());

         Transaction transaction = fromAccount.getTransactions().get(0);
         assertEquals(fromAccountId, transaction.getFromAccountId());
         assertEquals(toAccountId, transaction.getToAccountId());
         assertEquals(amount, transaction.getAmount());
     }

     // Test 12: transfer()
     @Test
     @Order(12)
     @DisplayName("transfer() - invalid amount")
     public void testTransferInvalidAmount() {
         String fromAccountId = "12345";
         String toAccountId = "23456";
         double initialBalance = 1000;
         double amount = -1;

         Account fromAccount = bank.createAccount(fromAccountId, initialBalance);
         Account toAccount = bank.createAccount(toAccountId, initialBalance);

         assertThrows(IllegalArgumentException.class, () -> bank.transfer(fromAccountId, toAccountId, amount));
     }

     // Test 13: transfer()
     @Test
     @Order(13)
     @DisplayName("transfer() - insufficient funds")
     public void testTransferInsufficientFunds() {
         String fromAccountId = "12345";
         String toAccountId = "23456";
         double initialBalance = 1000;
         double amount = 1100;

         Account fromAccount = bank.createAccount(fromAccountId, initialBalance);
         Account toAccount = bank.createAccount(toAccountId, initialBalance);

         assertThrows(IllegalStateException.class, () -> bank.transfer(fromAccountId, toAccountId, amount));
     }
}