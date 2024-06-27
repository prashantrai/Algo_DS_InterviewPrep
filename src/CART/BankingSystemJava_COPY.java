package CART;

import java.util.*;

public class BankingSystemJava_COPY {

    public static void main(String[] args) {
        
    }

    static class Account {
        String accountId;
        int balance;
        int totalOutgoing;
        int totalIncoming;

        public Account(String accountId) {
            this.accountId = accountId;
            this.balance = 0;
            this.totalOutgoing = 0;
            this.totalIncoming = 0;
        }

        // Level 3: New field to store scheduled payments
        List<ScheduledPayment> scheduledPayments = new ArrayList<>();
    }

    static class ScheduledPayment {
        int timestamp;
        String paymentId;
        int amount;
        boolean isCanceled;

        public ScheduledPayment(int timestamp, String paymentId, int amount) {
            this.timestamp = timestamp;
            this.paymentId = paymentId;
            this.amount = amount;
            this.isCanceled = false;
        }
    }

    private Map<String, Account> accounts;
    private int paymentCounter; // Level 3: Counter for payment IDs

    public BankingSystemJava_COPY() {
        accounts = new HashMap<>();
        paymentCounter = 0; // Level 3: Initialize payment counter
    }

    public boolean createAccount(int timestamp, String accountId) {
        if (accounts.containsKey(accountId)) {
            return false;
        }
        accounts.put(accountId, new Account(accountId));
        return true;
    }

    public Optional<Integer> deposit(int timestamp, String accountId, int amount) {
        processScheduledPayments(timestamp); // Level 3: Process scheduled payments first
        Account account = accounts.get(accountId);
        if (account == null) {
            return Optional.empty();
        }
        account.balance += amount;
        return Optional.of(account.balance);
    }

    public Optional<Integer> transfer(int timestamp, String sourceAccountId, String targetAccountId, int amount) {
        processScheduledPayments(timestamp); // Level 3: Process scheduled payments first
        Account sourceAccount = accounts.get(sourceAccountId);
        Account targetAccount = accounts.get(targetAccountId);
        if (sourceAccount == null || targetAccount == null) {
            return Optional.empty();
        }
        if (sourceAccountId.equals(targetAccountId)) {
            return Optional.empty();
        }
        if (sourceAccount.balance < amount) {
            return Optional.empty();
        }
        sourceAccount.balance -= amount;
        targetAccount.balance += amount;
        sourceAccount.totalOutgoing += amount;
        targetAccount.totalIncoming += amount; // Level 3: Update the totalIncoming for the target account
        return Optional.of(sourceAccount.balance);
    }

    public List<String> topSpenders(int timestamp, int n) {
        processScheduledPayments(timestamp); // Level 3: Process scheduled payments first
        List<Account> accountList = new ArrayList<>(accounts.values());
        accountList.sort((a, b) -> {
            if (b.totalOutgoing == a.totalOutgoing) {
                return a.accountId.compareTo(b.accountId);
            }
            return Integer.compare(b.totalOutgoing, a.totalOutgoing);
        });
        List<String> result = new ArrayList<>();
        for (int i = 0; i < Math.min(n, accountList.size()); i++) {
            Account account = accountList.get(i);
            result.add(account.accountId + "(" + account.totalOutgoing + ")");
        }
        return result;
    }

    // Level 3: New method to find top receivers
    public List<String> topReceivers(int timestamp, int n) {
        processScheduledPayments(timestamp); // Level 3: Process scheduled payments first
        List<Account> accountList = new ArrayList<>(accounts.values());
        accountList.sort((a, b) -> {
            if (b.totalIncoming == a.totalIncoming) {
                return a.accountId.compareTo(b.accountId);
            }
            return Integer.compare(b.totalIncoming, a.totalIncoming);
        });
        List<String> result = new ArrayList<>();
        for (int i = 0; i < Math.min(n, accountList.size()); i++) {
            Account account = accountList.get(i);
            result.add(account.accountId + "(" + account.totalIncoming + ")");
        }
        return result;
    }

    // Level 3: New method to schedule payments
    public Optional<String> schedulePayment(int timestamp, String accountId, int amount, int delay) {
        Account account = accounts.get(accountId);
        if (account == null) {
            return Optional.empty();
        }
        String paymentId = "payment" + (++paymentCounter);
        ScheduledPayment payment = new ScheduledPayment(timestamp + delay, paymentId, amount);
        account.scheduledPayments.add(payment);
        return Optional.of(paymentId);
    }

    // Level 3: New method to cancel scheduled payments
    public boolean cancelPayment(int timestamp, String accountId, String paymentId) {
        Account account = accounts.get(accountId);
        if (account == null) {
            return false;
        }
        for (ScheduledPayment payment : account.scheduledPayments) {
            if (payment.paymentId.equals(paymentId) && !payment.isCanceled) {
                payment.isCanceled = true;
                return true;
            }
        }
        return false;
    }

    // Level 3: New method to process scheduled payments
    private void processScheduledPayments(int timestamp) {
        for (Account account : accounts.values()) {
            List<ScheduledPayment> processedPayments = new ArrayList<>();
            for (ScheduledPayment payment : account.scheduledPayments) {
                if (payment.timestamp <= timestamp && !payment.isCanceled) {
                    if (account.balance >= payment.amount) {
                        account.balance -= payment.amount;
                        account.totalOutgoing += payment.amount;
                        processedPayments.add(payment);
                    }
                }
            }
            account.scheduledPayments.removeAll(processedPayments);
        }
    }
}