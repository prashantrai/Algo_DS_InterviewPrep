package CART;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class SimpleBankingSystem_Witout_Optional {

	
	// Level 1 Tests - all passed
	public static void test_Level_1() {
		SimpleBankingSystem_Witout_Optional bankingSystem = new SimpleBankingSystem_Witout_Optional();

		/** Level 1 */
        System.out.println(" ---- Level 1 ---- \n");
        // Create accounts
        System.out.println(bankingSystem.createAccount(1, "A")); // true
        System.out.println(bankingSystem.createAccount(2, "B")); // true
        System.out.println(bankingSystem.createAccount(3, "A")); // false (already exists)

        // Deposit money
        System.out.println(bankingSystem.deposit(4, "A", 100)); // Optional[100]
        System.out.println(bankingSystem.deposit(5, "C", 50));  // Optional.empty (account does not exist)

        // Transfer money
        System.out.println(bankingSystem.transfer(6, "A", "B", 50)); // Optional[50]
        System.out.println(bankingSystem.transfer(7, "A", "B", 100)); // Optional.empty (insufficient funds)
        System.out.println(bankingSystem.transfer(8, "A", "C", 50));  // Optional.empty (target account does not exist)
        System.out.println(bankingSystem.transfer(9, "A", "A", 50));  // Optional.empty (same source and target account)
	}
	
	// Level 2 Tests - all passed
	public static void test_Level_2() {
		SimpleBankingSystem_Witout_Optional bankingSystem = new SimpleBankingSystem_Witout_Optional();

		/** Level 2 */
        System.out.println("\n\n---- Level 2 ---- \n");
        
        // Create accounts
        System.out.println(bankingSystem.createAccount(1, "account3")); // true
        System.out.println(bankingSystem.createAccount(2, "account2")); // true
        System.out.println(bankingSystem.createAccount(3, "account1")); // true

        // Deposit money
        System.out.println(bankingSystem.deposit(4, "account1", 2000)); // Optional[2000]
        System.out.println(bankingSystem.deposit(5, "account2", 3000)); // Optional[3000]
        System.out.println(bankingSystem.deposit(6, "account3", 4000)); // Optional[4000]

        // Top spenders
        System.out.println(bankingSystem.topSpenders(7, 3)); // ["account1 (0)", "account2 (0)", "account3 (0)"]

        // Transfer money
        System.out.println(bankingSystem.transfer(8, "account3", "account2", 500));  // Optional[3500]
        System.out.println(bankingSystem.transfer(9, "account3", "account1", 1000)); // Optional[2500]
        System.out.println(bankingSystem.transfer(10, "account1", "account2", 2500));// Optional[500]

        // Top spenders
        System.out.println(bankingSystem.topSpenders(11, 3)); // ["account1 (2500)", "account3 (1500)", "account2 (0)"]
  
        	
	}
	
	// Level 3 Tests - all passed
	public static void test_Level_3() {
		SimpleBankingSystem_Witout_Optional bankingSystem = new SimpleBankingSystem_Witout_Optional();
		
		/** Level 3 */
        System.out.println("\n\n---- Level 3 ---- \n");
        
        // Create accounts
        System.out.println(bankingSystem.createAccount(1, "account1")); // true
        System.out.println(bankingSystem.createAccount(2, "account2")); // true

        // Deposit money
        System.out.println(bankingSystem.deposit(3, "account1", 2000)); // Optional[2000]

        // Make payments
        System.out.println(bankingSystem.pay(4, "account1", 1000)); // Optional[payment1]
        System.out.println(bankingSystem.pay(100, "account1", 1000)); // Optional[payment2]

        // Check payment status
        System.out.println(bankingSystem.getPaymentStatus(101, "non-existing", "payment1")); // Optional.empty
        System.out.println(bankingSystem.getPaymentStatus(102, "account2", "payment1")); // Optional.empty
        System.out.println(bankingSystem.getPaymentStatus(103, "account1", "payment1")); // Optional[IN PROGRESS]

        // Top spenders
        System.out.println(bankingSystem.topSpenders(104, 2)); // ["account1 (2000)", "account2 (0)"]

        // Time passes, process cashback
        System.out.println(bankingSystem.deposit(3 + (int) MILLISECONDS_IN_1_DAY, "account1", 100)); // returns 100 ; Optional[100 + 20 cashback = 120]
        System.out.println(bankingSystem.getPaymentStatus(4 + (int) MILLISECONDS_IN_1_DAY, "account1", "payment1")); // returns CASHBACK RECEIVED  ; Optional[CASHBACK RECEIVED]
        System.out.println(bankingSystem.deposit(5 + (int) MILLISECONDS_IN_1_DAY, "account1", 100)); // returns 220; Optional[120 + 100 + 20 cashback = 240]
        System.out.println(bankingSystem.deposit(99 + (int) MILLISECONDS_IN_1_DAY, "account1", 100)); // returns 320; Optional[240 + 100 = 340]
        System.out.println(bankingSystem.deposit(100 + (int) MILLISECONDS_IN_1_DAY, "account1", 100)); // returns 440; Optional[340 + 100 + 20 cashback = 460]

        
	}
	
	public static void main(String[] args) {
		test_Level_1();
		test_Level_2();
		test_Level_3();
	}

	// Class to represent a Bank Account
    private static class Account {
        String accountId;
        int balance;
        int totalOutgoing; // Level 2
        List<Payment> payments; // Level 3

        Account(String accountId) {
            this.accountId = accountId;
            this.balance = 0;
            this.totalOutgoing = 0;
            this.payments = new ArrayList<>();
        }
    }
    
    // Level 3
    // Class to represent a Payment
    private static class Payment {
        String paymentId;
        int amount;
        int cashback;
        long cashbackTime;
        boolean cashbackReceived;

        Payment(String paymentId, int amount, long timestamp) {
            this.paymentId = paymentId;
            this.amount = amount;
            this.cashback = (int) Math.floor(amount * 0.02);
            this.cashbackTime = timestamp + MILLISECONDS_IN_1_DAY;
            this.cashbackReceived = false;
        }
    }
    

    // A map to store all accounts
    private Map<String, Account> accounts;

    // Level 3
    private Map<String, Payment> paymentMap;
    private int paymentCounter;
    private static final long MILLISECONDS_IN_1_DAY = 86400000L;

    
    
    // Constructor to initialize the accounts map
    public SimpleBankingSystem_Witout_Optional() {
        accounts = new HashMap<>();
        paymentMap = new HashMap<>(); // Level 3
        paymentCounter = 0;	 // Level 3
    }

    // Level 1
    public boolean createAccount(int timestamp, String accountId) {
        if (accounts.containsKey(accountId)) {
            return false;
        }
        accounts.put(accountId, new Account(accountId));
        return true;
    }

    // Level 1
    public Integer deposit(int timestamp, String accountId, int amount) {
        Account account = accounts.get(accountId);
        if (account == null) {
        	return null;
        }
        
        // Level 3
        // Process any pending cashback for the account before depositing the new amount
        processCashback(timestamp);
        
        account.balance += amount;
        return account.balance;
    }

    // Level 1
    // Transfer money from one account to another
    public Integer transfer(int timestamp, String sourceAccountId, String targetAccountId, int amount) {
        Account sourceAccount = accounts.get(sourceAccountId);
        Account targetAccount = accounts.get(targetAccountId);

        if (sourceAccount == null || targetAccount == null 
        		|| sourceAccountId.equals(targetAccountId) 
        		|| sourceAccount.balance < amount) {
        	
            return null;
        }
        
        // Level 3
        // Process any pending cashback for the source account before making the transfer
        processCashback(timestamp); // Level 3


        sourceAccount.balance -= amount;
        targetAccount.balance += amount;
        sourceAccount.totalOutgoing += amount;

        return sourceAccount.balance;
    }
	

    
    // Level 2
    // Get the top spenders based on outgoing transactions
    public List<String> topSpenders(int timestamp, int n) {
    	processCashback(timestamp); // Level 3
    	
        List<Account> accountList = new ArrayList<>(accounts.values());

        // Sort the accounts based on total outgoing amount in descending order
        // In case of a tie, sort alphabetically by accountId in ascending order
        accountList.sort((a, b) -> {
            if (b.totalOutgoing == a.totalOutgoing) {
                return a.accountId.compareTo(b.accountId);
            }
            return Integer.compare(b.totalOutgoing, a.totalOutgoing);
        });

        // Create the result list
        List<String> result = new ArrayList<>();
        for (int i = 0; i < Math.min(n, accountList.size()); i++) {
            Account account = accountList.get(i);
            result.add(account.accountId + " (" + account.totalOutgoing + ")");
        }

        return result;
    }
    
    
    // Level 3
    public String pay(int timestamp, String accountId, int amount) {
        Account account = accounts.get(accountId);
        if (account == null || account.balance < amount) {
            return null;
        }

        // Process any pending cashback for the account before making the payment
        processCashback(timestamp);

        account.balance -= amount;
        account.totalOutgoing += amount;
        paymentCounter++;
        String paymentId = "payment" + paymentCounter;
        Payment payment = new Payment(paymentId, amount, timestamp);
        account.payments.add(payment);
        paymentMap.put(paymentId, payment);

        return paymentId;
    }
    
    
    // Level 3
    public String getPaymentStatus(int timestamp, String accountId, String paymentId) {
        Account account = accounts.get(accountId);
        if (account == null) {
            return null;
        }
        Payment payment = paymentMap.get(paymentId);
        if (payment == null || !account.payments.contains(payment)) {
            return null;
        }
        if (timestamp >= payment.cashbackTime) {
            return "CASHBACK RECEIVED";
        }
        return "IN PROGRESS";
    }

    
    // Level 3
    private void processCashback(long timestamp) {
        for (Account account : accounts.values()) {
            for (Payment payment : account.payments) {
                if (!payment.cashbackReceived && timestamp >= payment.cashbackTime) {
                    account.balance += payment.cashback;
                    payment.cashbackReceived = true;
                }
            }
        }
    }
    
}
