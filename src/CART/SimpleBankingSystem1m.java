package CART;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/*
 *  All tests are passing
 *  
 *  Implementation Covered: Level 1, 2 and 3
 * 
 * */

public class SimpleBankingSystem1m {
	
	// Level 1 Tests - all passed
	public static void test_Level_1() {
		SimpleBankingSystem1m bankingSystem = new SimpleBankingSystem1m();

		/** Level 1 */
        System.out.println(" ---- Level 1 ---- \n");
        System.out.println(bankingSystem.createAccount(1, "account")); // returns True
        System.out.println(bankingSystem.createAccount(2, "account")); // returns False
        System.out.println(bankingSystem.createAccount(3, "account1")); // returns True
        System.out.println(bankingSystem.deposit(4, "non-existing", 2700)); // returns None
        System.out.println(bankingSystem.deposit(5, "account1", 2700)); // returns 2700
        System.out.println(bankingSystem.pay(6, "non-existing", 2700)); // returns None
        System.out.println(bankingSystem.pay(7, "account1", 2701)); // returns None
        System.out.println(bankingSystem.pay(8, "account1", 200)); // returns 2500

	}
	
	// Level 2 Tests - all passed
	public static void test_Level_2() {
		SimpleBankingSystem1m bankingSystem = new SimpleBankingSystem1m();
		
		/** Level 2 */
        System.out.println("\n\n---- Level 2 ---- \n");
        System.out.println(bankingSystem.createAccount(1, "account1")); // returns True
        System.out.println(bankingSystem.createAccount(2, "account2")); // returns True
        System.out.println(bankingSystem.createAccount(3, "account3")); // returns True
        System.out.println(bankingSystem.deposit(4, "account1", 2000)); // returns 2000
        System.out.println(bankingSystem.deposit(5, "account2", 3000)); // returns 3000
        System.out.println(bankingSystem.deposit(6, "account3", 4000)); // returns 4000
        System.out.println(bankingSystem.topActivity(7, 3)); // returns ["account3(4000)", "account2(3000)", "account1(2000)"]
        System.out.println(bankingSystem.pay(8, "account1", 1500)); // returns 500
        System.out.println(bankingSystem.pay(9, "account2", 250)); // returns 2750
        System.out.println(bankingSystem.deposit(10, "account3", 250)); // returns 4250
        System.out.println(bankingSystem.topActivity(11, 3)); // returns ["account3(4250)", "account1(3500)", "account2(3250)"]

	}
	
	// Level 3 Tests - all passed
	public static void test_Level_3() {
		SimpleBankingSystem1m bankingSystem = new SimpleBankingSystem1m();
		
		/** Level 3 */
        System.out.println("\n\n---- Level 3 ---- \n");

        System.out.println(bankingSystem.createAccount(1, "account1")); // returns True
        System.out.println(bankingSystem.createAccount(2, "account2")); // returns True
        System.out.println(bankingSystem.deposit(3, "account1", 2000)); // returns 2000
        System.out.println(bankingSystem.deposit(4, "account2", 3000)); // returns 3000

        System.out.println(bankingSystem.transfer(5, "account1", "account2", 5000)); // returns None
        System.out.println(bankingSystem.transfer(16, "account1", "account2", 1000)); // returns transfer1

        System.out.println(bankingSystem.acceptTransfer(20, "account1", "transfer1")); // returns False
        System.out.println(bankingSystem.acceptTransfer(21, "non-existing", "transfer1")); // returns False
        System.out.println(bankingSystem.acceptTransfer(22, "account1", "transfer2")); // returns False
        System.out.println(bankingSystem.acceptTransfer(25, "account2", "transfer1")); // returns True
        System.out.println(bankingSystem.acceptTransfer(30, "account2", "transfer1")); // returns False

        System.out.println(bankingSystem.transfer(40, "account1", "account2", 1000)); // returns transfer2
        System.out.println(bankingSystem.acceptTransfer(45 + (int)MILLISECONDS_IN_1_DAY, "account2", "transfer2")); // returns False

        System.out.println(bankingSystem.transfer(50 + (int)MILLISECONDS_IN_1_DAY, "account1", "account1", 1000)); // returns None

	}
	

	public static void main(String[] args) {
		test_Level_1();
		test_Level_2();
		test_Level_3();
        
    }
	
	
	// Level 1
	private Map<String, Integer> accounts;
	
	// Level 2
	private Map<String, Integer> transactionValues; 
	
	// Level 3
	private Map<String, Transfer> transfers;
    private int transferCount;
    private static final long MILLISECONDS_IN_1_DAY = 86400000L;

    public SimpleBankingSystem1m() {
        accounts = new HashMap<>();
        transactionValues = new HashMap<>(); // level 2
        
        // Level 3
        transfers = new HashMap<>();
        transferCount = 0;
    }

    public boolean createAccount(int timestamp, String accountId) {
        if (accounts.containsKey(accountId)) {
            return false;
        } else {
            accounts.put(accountId, 0);
            transactionValues.put(accountId, 0); // Level 2
            return true;
        }
    }

    public Integer deposit(int timestamp, String accountId, int amount) {
        if (!accounts.containsKey(accountId)) {
            return null;
        } else {
            int newBalance = accounts.get(accountId) + amount;
            accounts.put(accountId, newBalance);
            
            // Level 2
            transactionValues.put(accountId, transactionValues.get(accountId) + amount);
            
            return newBalance;
        }
    }

    public Integer pay(int timestamp, String accountId, int amount) {
        if (!accounts.containsKey(accountId) || accounts.get(accountId) < amount) {
            return null;
        } else {
            int newBalance = accounts.get(accountId) - amount;
            accounts.put(accountId, newBalance);
            
            // Level 2
            transactionValues.put(accountId, transactionValues.get(accountId) + amount);
            
            return newBalance;
        }
    }
    
    /** Level 2 */
    public List<String> topActivity(int timestamp, int n) {
        List<Map.Entry<String, Integer>> transactionList 
        		= new ArrayList<>(transactionValues.entrySet());
        
        transactionList.sort((a, b) -> {
            int cmp = b.getValue().compareTo(a.getValue());
            if (cmp == 0) {
                return a.getKey().compareTo(b.getKey());
            }
            return cmp;
        });

        List<String> result = new ArrayList<>();
        for (int i = 0; i < Math.min(n, transactionList.size()); i++) {
            Map.Entry<String, Integer> entry = transactionList.get(i);
            result.add(entry.getKey() + "(" + entry.getValue() + ")");
        }

        return result;
    }
    
    
    /** Level 3 */
    public String transfer(int timestamp, String sourceAccountId, 
    		String targetAccountId, int amount) {
        
    	if (sourceAccountId.equals(targetAccountId) 
        		|| !accounts.containsKey(sourceAccountId) 
        		|| !accounts.containsKey(targetAccountId) 
        		|| accounts.get(sourceAccountId) < amount) {
        	
            return null;
            
        } else {
        	
            int newBalance = accounts.get(sourceAccountId) - amount;
            accounts.put(sourceAccountId, newBalance);
            transferCount++;
            String transferId = "transfer" + transferCount;
            transfers.put(transferId, 
            		new Transfer(sourceAccountId, targetAccountId, 
            				amount, timestamp + MILLISECONDS_IN_1_DAY));
            
            return transferId;
        }
    }
    
    /** Level 3 */
    public boolean acceptTransfer(int timestamp, String accountId, String transferId) {
        if (!transfers.containsKey(transferId)) {
            return false;
        }
        
        Transfer transfer = transfers.get(transferId);

        if (transfer.expirationTime <= timestamp 
        		|| transfer.isAccepted() 
        		|| !transfer.targetAccountId.equals(accountId)) {
        	
            return false;
        }

        transfer.setAccepted(true);
        int newSourceBalance 
        	= accounts.get(transfer.sourceAccountId) + transfer.amount;
        
        int newTargetBalance = accounts.get(accountId) + transfer.amount;

        accounts.put(transfer.sourceAccountId, newSourceBalance);
        accounts.put(accountId, newTargetBalance);

        transactionValues.put(transfer.sourceAccountId, 
        		transactionValues.get(transfer.sourceAccountId) + transfer.amount);
        
        transactionValues.put(accountId, 
        		transactionValues.get(accountId) + transfer.amount);

        return true;
    }
    
    
    /** Level 3 */
    // Inner class  - 
    private static class Transfer {
        String sourceAccountId;
        String targetAccountId;
        int amount;
        long expirationTime;
        boolean accepted;

        public Transfer(String sourceAccountId, String targetAccountId, 
        		int amount, long expirationTime) {
        	
            this.sourceAccountId = sourceAccountId;
            this.targetAccountId = targetAccountId;
            this.amount = amount;
            this.expirationTime = expirationTime;
            this.accepted = false;
        }

        public boolean isAccepted() {
            return accepted;
        }

        public void setAccepted(boolean accepted) {
            this.accepted = accepted;
        }
    }

}
