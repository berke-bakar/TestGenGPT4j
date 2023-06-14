import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BankTest {

    @Test
    void testCreateAccount() {
        Bank bank = new Bank();
        Account account = bank.createAccount("123456", 1000.0);
        Assertions.assertEquals("123456", account.getAccountId());
        Assertions.assertEquals(1000.0, account.getBalance());
    }

    @Test
    void testCreateAccountNullId() {
        Bank bank = new Bank();
        Assertions.assertThrows(IllegalArgumentException.class, () -> bank.createAccount(null, 1000.0));
    }

    @Test
    void testCreateAccountNegativeBalance() {
        Bank bank = new Bank();
        Assertions.assertThrows(IllegalArgumentException.class, () -> bank.createAccount("123456", -1000.0));
    }

    @Test
    void testCreateAccountAlreadyExists() {
        Bank bank = new Bank();
        bank.createAccount("123456", 1000.0);
        Assertions.assertThrows(IllegalStateException.class, () -> bank.createAccount("123456", 2000.0));
    }

    @Test
    void testGetAccount() {
        Bank bank = new Bank();
        bank.createAccount("123456", 1000.0);
        Account account = bank.getAccount("123456");
        Assertions.assertEquals("123456", account.getAccountId());
        Assertions.assertEquals(1000.0, account.getBalance());
    }

    @Test
    void testGetAccountNullId() {
        Bank bank = new Bank();
        Assertions.assertThrows(IllegalArgumentException.class, () -> bank.getAccount(null));
    }

    @Test
    void testGetAccountDoesNotExist() {
        Bank bank = new Bank();
        Assertions.assertThrows(IllegalStateException.class, () -> bank.getAccount("123456"));
    }

    @Test
    void testDeleteAccount() {
        Bank bank = new Bank();
        bank.createAccount("123456", 1000.0);
        bank.deleteAccount("123456");
        Assertions.assertThrows(IllegalStateException.class, () -> bank.getAccount("123456"));
    }

    @Test
    void testDeleteAccountNullId() {
        Bank bank = new Bank();
        Assertions.assertThrows(IllegalArgumentException.class, () -> bank.deleteAccount(null));
    }

    @Test
    void testDeleteAccountDoesNotExist() {
        Bank bank = new Bank();
        Assertions.assertThrows(IllegalStateException.class, () -> bank.deleteAccount("123456"));
    }

    @Test
    void testTransfer() {
        Bank bank = new Bank();
        Account account1 = bank.createAccount("123456", 1000.0);
        Account account2 = bank.createAccount("789012", 2000.0);
        bank.transfer("123456", "789012", 500.0);
        Assertions.assertEquals(500.0, account1.getBalance());
        Assertions.assertEquals(2500.0, account2.getBalance());
    }

    @Test
    void testTransferNegativeAmount() {
        Bank bank = new Bank();
        Account account1 = bank.createAccount("123456", 1000.0);
        Account account2 = bank.createAccount("789012", 2000.0);
        Assertions.assertThrows(IllegalArgumentException.class, () -> bank.transfer("123456", "789012", -500.0));
        Assertions.assertEquals(1000.0, account1.getBalance());
        Assertions.assertEquals(2000.0, account2.getBalance());
    }

    @Test
    void testTransferInsufficientFunds() {
        Bank bank = new Bank();
        Account account1 = bank.createAccount("123456", 1000.0);
        Account account2 = bank.createAccount("789012", 2000.0);
        Assertions.assertThrows(IllegalStateException.class, () -> bank.transfer("123456", "789012", 1500.0));
        Assertions.assertEquals(1000.0, account1.getBalance());
        Assertions.assertEquals(2000.0, account2.getBalance());
    }

    @Test
    void testTransferNullFromAccountId() {
        Bank bank = new Bank();
        Account account2 = bank.createAccount("789012", 2000.0);
        Assertions.assertThrows(IllegalArgumentException.class, () -> bank.transfer(null, "789012", 500.0));
        Assertions.assertEquals(2000.0, account2.getBalance());
    }

    @Test
    void testTransferNullToAccountId() {
        Bank bank = new Bank();
        Account account1 = bank.createAccount("123456", 1000.0);
        Assertions.assertThrows(IllegalArgumentException.class, () -> bank.transfer("123456", null, 500.0));
        Assertions.assertEquals(1000.0, account1.getBalance());
    }

    @Test
    void testTransferFromAccountDoesNotExist() {
        Bank bank = new Bank();
        Account account2 = bank.createAccount("789012", 2000.0);
        Assertions.assertThrows(IllegalStateException.class, () -> bank.transfer("123456", "789012", 500.0));
        Assertions.assertEquals(2000.0, account2.getBalance());
    }

    @Test
    void testTransferToAccountDoesNotExist() {
        Bank bank = new Bank();
        Account account1 = bank.createAccount("123456", 1000.0);
        Assertions.assertThrows(IllegalStateException.class, () -> bank.transfer("123456", "789012", 500.0));
        Assertions.assertEquals(1000.0, account1.getBalance());
    }
}