package Pomelo;

import java.util.*;


class TransactionAuthorizer_PhoneScreen_Jan_3_2025 {
	
	public static void main(String[] args) {
		TransactionAuthorizer_PhoneScreen_Jan_3_2025 authorizer = new TransactionAuthorizer_PhoneScreen_Jan_3_2025();

        // Test cases
        Transaction t1 = new Transaction("1111", "1001", "2222", 0, 1000);
        Transaction t2 = new Transaction("1111", "1002", "2222", 60, 2000);
        Transaction t3 = new Transaction("1111", "1003", "2222", 3660, 2001);
        Transaction t4 = new Transaction("1111", "1004", "2222", 4000, 3000);
        Transaction t5 = new Transaction("1111", "1005", "2222", 7590, 2001);

        // Test: Valid transactions
        System.out.println(authorizer.approve(t1)); // true
        System.out.println(authorizer.approve(t2)); // true
        System.out.println(authorizer.approve(t3)); // true

        // Test: Transaction rejected due to velocity limit
        System.out.println(authorizer.approve(t4)); // false
        System.out.println(authorizer.approve(t5)); // false
    }
	
	
    private final Set<String> globalDisallowedMCCs;
    private final Map<String, Set<String>> userSpecificDisallowedMCCs;
    private final Map<String, Queue<int[]>> userTransactionHistory; // Step 3: Store user transactions (timestamp, amount)

    private static final int VELOCITY_LIMIT = 5000; // Velocity limit in dollars
    private static final int TIME_WINDOW = 3600; // Rolling time window in seconds

    // Constructor to initialize the rules
    public TransactionAuthorizer_PhoneScreen_Jan_3_2025() {
        // Step 1: Initialize global reject codes
        globalDisallowedMCCs = new HashSet<>();
        globalDisallowedMCCs.add("9999");
        globalDisallowedMCCs.add("0000");

        // Step 2: Initialize user-specific reject codes
        userSpecificDisallowedMCCs = new HashMap<>();
        userSpecificDisallowedMCCs.put("1234", new HashSet<>(Arrays.asList("1111", "2222")));
        userSpecificDisallowedMCCs.put("5678", new HashSet<>(Arrays.asList("5555", "6666")));

        // Step 3: Initialize user transaction history
        userTransactionHistory = new HashMap<>();
    }

    // Method to approve or decline a transaction
    public boolean approve(Transaction transaction) {
        // Step 1: Check global reject codes
        if (globalDisallowedMCCs.contains(transaction.mcc)) {
            return false;
        }

        // Step 2: Check user-specific reject codes
        if (userSpecificDisallowedMCCs.containsKey(transaction.userId)) {
            Set<String> disallowedMCCsForUser = userSpecificDisallowedMCCs.get(transaction.userId);
            if (disallowedMCCsForUser.contains(transaction.mcc)) {
                return false;
            }
        }

        // Step 3: Apply velocity limit rule
        if (!applyVelocityLimit(transaction)) {
            return false;
        }

        return true; // Approve all other transactions
    }

    // Step 3: Velocity limit rule implementation
    private boolean applyVelocityLimit(Transaction transaction) {
        userTransactionHistory.putIfAbsent(transaction.userId, new LinkedList<>());
        Queue<int[]> transactions = userTransactionHistory.get(transaction.userId);

        // Remove transactions outside the rolling one-hour window
        while (!transactions.isEmpty() && transactions.peek()[0] < transaction.timestamp - TIME_WINDOW) {
            transactions.poll();
        }

        // Calculate the sum of amounts in the current window
        double sum = transactions.stream().mapToDouble(t -> t[1]).sum();

        // Add the current transaction to the queue (including its amount)
        transactions.offer(new int[]{transaction.timestamp, (int) transaction.amount});

        // Check if the sum exceeds the velocity limit
        return sum + transaction.amount <= VELOCITY_LIMIT;
    }
}

class Transaction {
    String userId;
    String transactionId;
    String mcc;
    int timestamp;
    double amount;

    public Transaction(String userId, String transactionId, String mcc, int timestamp, double amount) {
        this.userId = userId;
        this.transactionId = transactionId;
        this.mcc = mcc;
        this.timestamp = timestamp;
        this.amount = amount;
    }
}


