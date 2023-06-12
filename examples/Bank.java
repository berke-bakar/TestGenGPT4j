package org.example;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

public class Bank {
    private final Map<String, Account> accounts = new HashMap<>();

    public Account createAccount(String accountId, double initialBalance) {
        if (accountId == null || initialBalance < 0) {
            throw new IllegalArgumentException("Account ID must not be null and Initial balance must be non-negative");
        }

        if (accounts.containsKey(accountId)) {
            throw new IllegalStateException("Account already exists");
        }

        Account account = new Account(accountId, initialBalance);
        accounts.put(accountId, account);
        return account;
    }

    public Account getAccount(String accountId) {
        if (accountId == null) {
            throw new IllegalArgumentException("Account ID must not be null");
        }

        Account account = accounts.get(accountId);
        if (account == null) {
            throw new IllegalStateException("Account does not exist");
        }

        return account;
    }

    public void deleteAccount(String accountId) {
        if (accountId == null) {
            throw new IllegalArgumentException("Account ID must not be null");
        }

        if (!accounts.containsKey(accountId)) {
            throw new IllegalStateException("Account does not exist");
        }

        accounts.remove(accountId);
    }

    public void transfer(String fromAccountId, String toAccountId, double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Transfer amount must be positive");
        }

        Account fromAccount = getAccount(fromAccountId);
        Account toAccount = getAccount(toAccountId);

        if (fromAccount.getBalance() < amount) {
            throw new IllegalStateException("Insufficient funds");
        }

        fromAccount.debit(amount);
        toAccount.credit(amount);

        Transaction transaction = new Transaction(fromAccountId, toAccountId, amount);
        fromAccount.addTransaction(transaction);
        toAccount.addTransaction(transaction);
    }

    public Map<String, Account> getAccounts() {
        return accounts;
    }
}

class Account {
    private final String accountId;
    private double balance;
    private final List<Transaction> transactions = new ArrayList<>();

    public Account(String accountId, double initialBalance) {
        this.accountId = accountId;
        this.balance = initialBalance;
    }

    public String getAccountId() {
        return accountId;
    }

    public double getBalance() {
        return balance;
    }

    public void debit(double amount) {
        this.balance -= amount;
    }

    public void credit(double amount) {
        this.balance += amount;
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }
}

class Transaction {
    private final String fromAccountId;
    private final String toAccountId;
    private final double amount;

    public Transaction(String fromAccountId, String toAccountId, double amount) {
        this.fromAccountId = fromAccountId;
        this.toAccountId = toAccountId;
        this.amount = amount;
    }

    public String getFromAccountId() {
        return fromAccountId;
    }

    public String getToAccountId() {
        return toAccountId;
    }

    public double getAmount() {
        return amount;
    }
}