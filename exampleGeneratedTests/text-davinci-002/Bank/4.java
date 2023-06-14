import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BankTest {

    @Test
    void testCreateAccount_ValidInput_ReturnsAccount() {
        //arrange
        String accountId = "1";
        double initialBalance = 10;
        Bank bank = new Bank();

        //act
        Account account = bank.createAccount(accountId, initialBalance);

        //assert
        assertEquals(accountId, account.getAccountId());
        assertEquals(initialBalance, account.getBalance());
    }

    @Test
    void testCreateAccount_InvalidAccountId_ThrowsIllegalArgumentException() {
        //arrange
        String accountId = null;
        double initialBalance = 10;
        Bank bank = new Bank();

        //act
        //assert
        assertThrows(IllegalArgumentException.class, () -> {
            bank.createAccount(accountId, initialBalance);
        });
    }

    @Test
    void testCreateAccount_InvalidInitialBalance_ThrowsIllegalArgumentException() {
        //arrange
        String accountId = "1";
        double initialBalance = -1;
        Bank bank = new Bank();

        //act
        //assert
        assertThrows(IllegalArgumentException.class, () -> {
            bank.createAccount(accountId, initialBalance);
        });
    }

    @Test
    void testCreateAccount_SameAccountId_ThrowsIllegalStateException() {
        //arrange
        String accountId = "1";
        double initialBalance = 10;
        Bank bank = new Bank();
        bank.createAccount(accountId, initialBalance);

        //act
        //assert
        assertThrows(IllegalStateException.class, () -> {
            bank.createAccount(accountId, initialBalance);
        });
    }

    @Test
    void testGetAccount_ValidAccountId_ReturnsAccount() {
        //arrange
        String accountId = "1";
        double initialBalance = 10;
        Bank bank = new Bank();
        bank.createAccount(accountId, initialBalance);

        //act
        Account account = bank.getAccount(accountId);

        //assert
        assertEquals(accountId, account.getAccountId());
        assertEquals(initialBalance, account.getBalance());
    }

    @Test
    void testGetAccount_InvalidAccountId_ThrowsIllegalArgumentException() {
        //arrange
        String accountId = null;
        Bank bank = new Bank();

        //act
        //assert
        assertThrows(IllegalArgumentException.class, () -> {
            bank.getAccount(accountId);
        });
    }

    @Test
    void testGetAccount_NotExistingAccountId_ThrowsIllegalStateException() {
        //arrange
        String accountId = "1";
        Bank bank = new Bank();

        //act
        //assert
        assertThrows(IllegalStateException.class, () -> {
            bank.getAccount(accountId);
        });
    }

    @Test
    void testDeleteAccount_ValidAccountId_DeletesAccount() {
        //arrange
        String accountId = "1";
        double initialBalance = 10;
        Bank bank = new Bank();
        bank.createAccount(accountId, initialBalance);

        //act
        bank.deleteAccount(accountId);

        //assert
        assertThrows(IllegalStateException.class, () -> {
            bank.getAccount(accountId);
        });
    }

    @Test
    void testDeleteAccount_InvalidAccountId_ThrowsIllegalArgumentException() {
        //arrange
        String accountId = null;
        Bank bank = new Bank();

        //act
        //assert
        assertThrows(IllegalArgumentException.class, () -> {
            bank.deleteAccount(accountId);
        });
    }

    @Test
    void testDeleteAccount_NotExistingAccountId_ThrowsIllegalStateException() {
        //arrange
        String accountId = "1";
        Bank bank = new Bank();

        //act
        //assert
        assertThrows(IllegalStateException.class, () -> {
            bank.deleteAccount(accountId);
        });
    }

    @Test
    void testTransfer_ValidInput_TransfersFunds() {
        //arrange
        String fromAccountId = "1";
        String toAccountId = "2";
        double initialFromAccountBalance = 10;
        double initialToAccountBalance = 20;
        double amount = 5;
        Bank bank = new Bank();
        bank.createAccount(fromAccountId, initialFromAccountBalance);
        bank.createAccount(toAccountId, initialToAccountBalance);

        //act
        bank.transfer(fromAccountId, toAccountId, amount);

        //assert
        assertEquals(initialFromAccountBalance - amount, bank.getAccount(fromAccountId).getBalance());
        assertEquals(initialToAccountBalance + amount, bank.getAccount(toAccountId).getBalance());
    }

    @Test
    void testTransfer_InvalidFromAccountId_ThrowsIllegalArgumentException() {
        //arrange
        String fromAccountId = null;
        String toAccountId = "2";
        double amount = 5;
        Bank bank = new Bank();

        //act
        //assert
        assertThrows(IllegalArgumentException.class, () -> {
            bank.transfer(fromAccountId, toAccountId, amount);
        });
    }

    @Test
    void testTransfer_InvalidToAccountId_ThrowsIllegalArgumentException() {
        //arrange
        String fromAccountId = "1";
        String toAccountId = null;
        double amount = 5;
        Bank bank = new Bank();

        //act
        //assert
        assertThrows(IllegalArgumentException.class, () -> {
            bank.transfer(fromAccountId, toAccountId, amount);
        });
    }

    @Test
    void testTransfer_NotExistingFromAccountId_ThrowsIllegalStateException() {
        //arrange
        String fromAccountId = "1";
        String toAccountId = "2";
        double amount = 5;
        Bank bank = new Bank();

        //act
        //assert
        assertThrows(IllegalStateException.class, () -> {
            bank.transfer(fromAccountId, toAccountId, amount);
        });
    }

    @Test
    void testTransfer_NotExistingToAccountId_ThrowsIllegalStateException() {
        //arrange
        String fromAccountId = "1";
        String toAccountId = "2";
        double amount = 5;
        Bank bank = new Bank();
        bank.createAccount(fromAccountId, 10);

        //act
        //assert
        assertThrows(IllegalStateException.class, () -> {
            bank.transfer(fromAccountId, toAccountId, amount);
        });
    }

    @Test
    void testTransfer_InvalidAmount_ThrowsIllegalArgumentException() {
        //arrange
        String fromAccountId = "1";
        String toAccountId = "2";
        double amount = -1;
        Bank bank = new Bank();
        bank.createAccount(fromAccountId, 10);
        bank.createAccount(toAccountId, 10);

        //act
        //assert
        assertThrows(IllegalArgumentException.class, () -> {
            bank.transfer(fromAccountId, toAccountId, amount);
        });
    }

    @Test
    void testTransfer_InsufficientFunds_ThrowsIllegalStateException() {
        //arrange
        String fromAccountId = "1";
        String toAccountId = "2";
        double fromAccountBalance = 10;
        double amount = 15;
        Bank bank = new Bank();
        bank.createAccount(fromAccountId, fromAccountBalance);
        bank.createAccount(toAccountId, 10);

        //act
        //assert
        assertThrows(IllegalStateException.class, () -> {
            bank.transfer(fromAccountId, toAccountId, amount);
        });
    }
}