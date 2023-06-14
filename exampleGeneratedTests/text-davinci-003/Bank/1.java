import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.example.Bank;

public class BankTest {

// Test for createAccount()
    @Test
    public void createAccount_validInput_accountCreated() {
        // Arrange
        Bank bank = new Bank();
        String accountId = "123";
        double initialBalance = 100.00;
        // Act
        bank.createAccount(accountId, initialBalance);
        // Assert
        assertEquals(initialBalance, bank.getAccount(accountId).getBalance());
    }

    @Test
    public void createAccount_nullInput_throwIllegalArgumentException() {
        // Arrange
        Bank bank = new Bank();
        String accountId = null;
        double initialBalance = 100.00;
        // Act
        // Assert
        assertThrows(IllegalArgumentException.class, () -> bank.createAccount(accountId, initialBalance));
    }

    @Test
    public void createAccount_negativeInput_throwIllegalArgumentException() {
        // Arrange
        Bank bank = new Bank();
        String accountId = "123";
        double initialBalance = -100.00;
        // Act
        // Assert
        assertThrows(IllegalArgumentException.class, () -> bank.createAccount(accountId, initialBalance));
    }

    @Test
    public void createAccount_existingAccountId_throwIllegalStateException() {
        // Arrange
        Bank bank = new Bank();
        String accountId = "123";
        double initialBalance = 100.00;
        bank.createAccount(accountId, initialBalance);
        // Act
        // Assert
        assertThrows(IllegalStateException.class, () -> bank.createAccount(accountId, initialBalance));
    }

// Test for getAccount()
    @Test
    public void getAccount_validInput_returnAccount() {
        // Arrange
        Bank bank = new Bank();
        String accountId = "123";
        double initialBalance = 100.00;
        bank.createAccount(accountId, initialBalance);
        // Act
        Account account = bank.getAccount(accountId);
        // Assert
        assertEquals(accountId, account.getAccountId());
        assertEquals(initialBalance, account.getBalance());
    }

    @Test
    public void getAccount_nullInput_throwIllegalArgumentException() {
        // Arrange
        Bank bank = new Bank();
        String accountId = null;
        // Act
        // Assert
        assertThrows(IllegalArgumentException.class, () -> bank.getAccount(accountId));
    }

    @Test
    public void getAccount_nonExistentAccount_throwIllegalStateException() {
        // Arrange
        Bank bank = new Bank();
        String accountId = "123";
        // Act
        // Assert
        assertThrows(IllegalStateException.class, () -> bank.getAccount(accountId));
    }

// Test for deleteAccount()
    @Test
    public void deleteAccount_validInput_accountDeleted() {
        // Arrange
        Bank bank = new Bank();
        String accountId = "123";
        double initialBalance = 100.00;
        bank.createAccount(accountId, initialBalance);
        // Act
        bank.deleteAccount(accountId);
        // Assert
        assertThrows(IllegalStateException.class, () -> bank.getAccount(accountId));
    }

    @Test
    public void deleteAccount_nullInput_throwIllegalArgumentException() {
        // Arrange
        Bank bank = new Bank();
        String accountId = null;
        // Act
        // Assert
        assertThrows(IllegalArgumentException.class, () -> bank.deleteAccount(accountId));
    }

    @Test
    public void deleteAccount_nonExistentAccount_throwIllegalStateException() {
        // Arrange
        Bank bank = new Bank();
        String accountId = "123";
        // Act
        // Assert
        assertThrows(IllegalStateException.class, () -> bank.deleteAccount(accountId));
    }

// Test for transfer()
    @Test
    public void transfer_validInput_amountTransferred() {
        // Arrange
        Bank bank = new Bank();
        String fromAccountId = "123";
        double fromInitialBalance = 100.00;
        String toAccountId = "456";
        double toInitialBalance = 50.00;
        bank.createAccount(fromAccountId, fromInitialBalance);
        bank.createAccount(toAccountId, toInitialBalance);
        double transferAmount = 50.00;
        // Act
        bank.transfer(fromAccountId, toAccountId, transferAmount);
        // Assert
        assertEquals(fromInitialBalance - transferAmount, bank.getAccount(fromAccountId).getBalance());
        assertEquals(toInitialBalance + transferAmount, bank.getAccount(toAccountId).getBalance());
    }

    @Test
    public void transfer_nullInput_throwIllegalArgumentException() {
        // Arrange
        Bank bank = new Bank();
        String fromAccountId = "123";
        double fromInitialBalance = 100.00;
        String toAccountId = null;
        double toInitialBalance = 50.00;
        bank.createAccount(fromAccountId, fromInitialBalance);
        bank.createAccount(toAccountId, toInitialBalance);
        double transferAmount = 50.00;
        // Act
        // Assert
        assertThrows(IllegalArgumentException.class, () -> bank.transfer(fromAccountId, toAccountId, transferAmount));
    }

    @Test
    public void transfer_nonExistentAccount_throwIllegalStateException() {
        // Arrange
        Bank bank = new Bank();
        String fromAccountId = "123";
        double fromInitialBalance = 100.00;
        String toAccountId = "456";
        double transferAmount = 50.00;
        bank.createAccount(fromAccountId, fromInitialBalance);
        // Act
        // Assert
        assertThrows(IllegalStateException.class, () -> bank.transfer(fromAccountId, toAccountId, transferAmount));
    }

    @Test
    public void transfer_insufficientFunds_throwIllegalStateException() {
        // Arrange
        Bank bank = new Bank();
        String fromAccountId = "123";
        double fromInitialBalance = 50.00;
        String toAccountId = "456";
        double toInitialBalance = 100.00;
        bank.createAccount(fromAccountId, fromInitialBalance);
        bank.createAccount(toAccountId, toInitialBalance);
        double transferAmount = 100.00;
        // Act
        // Assert
        assertThrows(IllegalStateException.class, () -> bank.transfer(fromAccountId, toAccountId, transferAmount));
    }
}