package CART;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class InMemoryDatabase2_With_Level4 {

    //  all tests passed
    public static void test_Level_1() {
        InMemoryDatabase2_With_Level4 db = new InMemoryDatabase2_With_Level4();
        
        /** Level 1 */
        System.out.println(" ---- Level 1 ---- \n");

        // Test cases
        db.set(0, "A", "B", 4); // set database state: {"A": {"B": 4}}
        db.set(1, "A", "C", 6); // set database state: {"A": {"B": 4, "C": 6}}

        System.out.println(db.compareAndSet(2, "A", "B", 4, 9)); // returns True; database state: {"A": {"B": 9, "C": 6}}
        System.out.println(db.compareAndSet(3, "A", "C", 4, 9)); // returns False; field "C" in record "A" currently has a value of 6

        System.out.println(db.compareAndDelete(4, "A", "C", 6)); // returns True; database state: {"A": {"B": 9}}

        System.out.println(db.get(5, "A", "C")); // returns null; field "C" in record "A" was previously deleted
        System.out.println(db.get(6, "A", "B")); // returns 9
    }
	
    //  all tests passed
    public static void test_Level_2() {
        InMemoryDatabase2_With_Level4 db = new InMemoryDatabase2_With_Level4();
        
        /** Level 1 */
        System.out.println("\n\n---- Level 2 ---- \n");

        db.set(0, "A", "BC", 4);   // set database state: {"A": {"BC": 4}}
        db.set(10, "A", "BD", 5);  // set database state: {"A": {"BC": 4, "BD": 5}}
        db.set(20, "A", "C", 6);   // set database state: {"A": {"BC": 4, "BD": 5, "C": 6}}

        System.out.println(db.scanByPrefix(30, "A", "B")); // returns ["BC (4)", "BD (5)"]
        System.out.println(db.scan(40, "A"));               // returns ["BC (4)", "BD (5)", "C (6)"]
        System.out.println(db.scanByPrefix(50, "B", "B"));  // returns []
    }
    
    
    //  all tests passed
    public static void test_Level_3() {
        InMemoryDatabase2_With_Level4 db = new InMemoryDatabase2_With_Level4();
        
        /** Level 1 */
        System.out.println("\n\n---- Level 3 ---- \n");
        
        // Test cases
        db.set(1, "A", "B", 4);   // set database state: {"A": {"B": 4}}
        db.setWithTTL(2, "X", "Y", 5, 15); // set database state: {"A": {"B": 4}, "X": {"Y": 5}} with {"Y": 5} expiring at timestamp 17
        db.setWithTTL(4, "A", "D", 3, 6);  // set database state: {"A": {"B": 4, "D": 3}, "X": {"Y": 5}} with {"D": 3} expiring at timestamp 10

        System.out.println(db.compareAndSetWithTTL(6, "A", "D", 3, 5, 10)); // returns TRUE;   {"A": {"B": 4, "D": 5}, "X": {"Y": 5}} with {"D": 5} expiring at timestamp 16
        System.out.println(db.get(7, "A", "D")); // returns 5
        System.out.println(db.scan(15, "A"));    // returns ["B (4)", "D (5)"]
        System.out.println(db.scan(17, "A"));    // returns ["B (4)"]; the field "D" in record "A" expired at timestamp 16

    }
    
    
    // all tests passed
    public static void test_Level_4() {
    	InMemoryDatabase2_With_Level4 db = new InMemoryDatabase2_With_Level4();
    	
    	/** Level 4 */
        System.out.println("\n\n---- Level 4 ---- \n");

        db.setWithTTL(1, "A", "B", 3, 10); // set database state: {"A": {"B": 3}} with {"B": 3} expiring at timestamp 10
        
        System.out.println(db.compareAndSetWithTTL(4, "A", "B", 3, 7, 9)); // returns True; {"A": {"B": 7}} expiring at timestamp 13
        System.out.println(db.get(10, "A", "B")); // returns 7
        System.out.println(db.getWhen(13, "A", "B", 3)); // returns 3
        
        // Failing 
        System.out.println(db.getWhen(15, "A", "B", 13)); // returns NULL; field "B" in record "A" expired at timestamp 13
    }
    
	public static void main(String[] args) {
		test_Level_1();
		test_Level_2();
		test_Level_3();
		test_Level_4();
	}
	
    
	
	
	
	private Map<String, Map<String, Integer>> database;
    
    // Level 3
    private Map<String, Map<String, Integer>> ttlDatabase;
    
 // Level 4: Map to store the history of changes for each field
    private Map<String, Map<String, TreeMap<Integer, Integer>>> historyDatabase;
    // Level 4: Map to store the history of TTL changes for each field
    private Map<String, Map<String, TreeMap<Integer, Integer>>> ttlHistoryDatabase;



    public InMemoryDatabase2_With_Level4() {
        this.database = new HashMap<>();
        this.ttlDatabase = new HashMap<>();// Level 3
        
     // Level 4
        this.historyDatabase = new HashMap<>();
        // Level 4
        this.ttlHistoryDatabase = new HashMap<>();
        
    }

    public void set(int timestamp, String key, String field, int value) {
        database.putIfAbsent(key, new HashMap<>());
        database.get(key).put(field, value);
        
        // Level 4
        updateHistory(timestamp, key, field, value);
    }

    // Compare and set a field's value if it matches the expected value
    /* in Level 1
    public boolean compareAndSet(int timestamp, String key, String field, int expectedValue, int newValue) {
        if (database.containsKey(key) && database.get(key).containsKey(field)) {
            int currentValue = database.get(key).get(field);
            if (currentValue == expectedValue) {
                database.get(key).put(field, newValue);
                return true;
            }
        }
        return false;
    } */
    
    // In Level 3
    public boolean compareAndSet(int timestamp, String key, String field, int expectedValue, int newValue) {
        if (isFieldValid(timestamp, key, field)) {
            int currentValue = database.get(key).get(field);
            if (currentValue == expectedValue) {
                database.get(key).put(field, newValue);
                
                // Level 4
                updateHistory(timestamp, key, field, newValue);
                
                return true;
            }
        }
        return false;
    }
    
    
    
    // Level 3
    public boolean compareAndSetWithTTL(int timestamp, String key, String field, int expectedValue, int newValue, int ttl) {
        if (compareAndSet(timestamp, key, field, expectedValue, newValue)) {
            ttlDatabase.get(key).put(field, timestamp + ttl);
            
         // Level 4
            updateTTLHistory(timestamp, key, field, ttl);
            
            return true;
        }
        return false;
    }
    

    // Level 1 Implementation
    /*public boolean compareAndDelete(int timestamp, String key, String field, int expectedValue) {
        if (database.containsKey(key) && database.get(key).containsKey(field)) {
            int currentValue = database.get(key).get(field);
            if (currentValue == expectedValue) {
                database.get(key).remove(field);
                return true;
            }
        }
        return false;
    }*/
    
    // Level 3 Implementation
    public boolean compareAndDelete(int timestamp, String key, String field, int expectedValue) {
        if (isFieldValid(timestamp, key, field)) {
            int currentValue = database.get(key).get(field);
            if (currentValue == expectedValue) {
        		
            	if(database.containsKey(key) 
            			&& database.get(key).containsKey(field)) {
            		
            		database.get(key).remove(field);
            	}
        		
        		// Level 3
        		if(ttlDatabase.containsKey(key) 
        				&& ttlDatabase.get(key).containsKey(field)) {
        			
        			ttlDatabase.get(key).remove(field); // Level 3
        		}
        		
        		// Level 4
                updateHistory(timestamp, key, field, null);
                
            	return true;
            }
        }
        return false;
    }
    

    // Level 1 implementation
    /*public Integer get(int timestamp, String key, String field) {
        if (database.containsKey(key) && database.get(key).containsKey(field)) {
            return database.get(key).get(field);
        }
        return null;
    }*/
    
    // Level 3 implementation
    public Integer get(int timestamp, String key, String field) {
        if (isFieldValid(timestamp, key, field)) {
            return database.get(key).get(field);
        }
        return null;
    }
    

    /** Level 2 */
    public List<String> scan(int timestamp, String key) {
        if (!database.containsKey(key)) {
            return Collections.emptyList();
        }

        Map<String, Integer> record = database.get(key);
        List<String> result = new ArrayList<>();

        // Level 2 - use below
        /*for (Map.Entry<String, Integer> entry : record.entrySet()) {
            result.add(entry.getKey() + " (" + entry.getValue() + ")");
        }*/
        
        // Level 3 - use below
        for (Map.Entry<String, Integer> entry : record.entrySet()) {
            if (isFieldValid(timestamp, key, entry.getKey())) {
                result.add(entry.getKey() + " (" + entry.getValue() + ")");
            }
        }

        result.sort(Comparator.naturalOrder());
        return result;
    }

    public List<String> scanByPrefix(int timestamp, String key, String prefix) {
        if (!database.containsKey(key)) {
            return Collections.emptyList();
        }

        Map<String, Integer> record = database.get(key);
        List<String> result = new ArrayList<>();

        // Level 1 - use below  
        /* for (Map.Entry<String, Integer> entry : record.entrySet()) {
            if (entry.getKey().startsWith(prefix)) {
                result.add(entry.getKey() + " (" + entry.getValue() + ")");
            }
        } */
        
        // Level 3 - use below  [only addtional check isFieldValid compare to above]
        for (Map.Entry<String, Integer> entry : record.entrySet()) {
            if (entry.getKey().startsWith(prefix) && isFieldValid(timestamp, key, entry.getKey())) {
                result.add(entry.getKey() + " (" + entry.getValue() + ")");
            }
        }

        result.sort(Comparator.naturalOrder());
        return result;
    }
    
    
    /** Level 3 */
    // Set a field-value pair in the record associated with key with TTL
    public void setWithTTL(int timestamp, String key, String field, int value, int ttl) {
        set(timestamp, key, field, value);
        ttlDatabase.putIfAbsent(key, new HashMap<>());
        ttlDatabase.get(key).put(field, timestamp + ttl);
        
     // Level 4
        updateTTLHistory(timestamp, key, field, ttl);
    }
    
    // Level 3
    // Helper method to check if a field is still valid based on its TTL
    private boolean isFieldValid(int timestamp, String key, String field) {
        if (ttlDatabase.containsKey(key) && ttlDatabase.get(key).containsKey(field)) {
            int expirationTime = ttlDatabase.get(key).get(field);
            if (timestamp > expirationTime) {
                // Field has expired, remove it
                database.get(key).remove(field);
                ttlDatabase.get(key).remove(field);
                return false;
            }
        }
        return database.containsKey(key) && database.get(key).containsKey(field);
    }
    
    
    /** Level 4 */
    // Level 4: Get the value of a field in the record associated with key at a specific timestamp
    public Integer getWhen(int timestamp, String key, String field, int atTimestamp) {
        if (atTimestamp == 0) {
            return get(timestamp, key, field);
        }

        if (!historyDatabase.containsKey(key) || !historyDatabase.get(key).containsKey(field)) {
            return null;
        }

        TreeMap<Integer, Integer> history = historyDatabase.get(key).get(field);
        Map.Entry<Integer, Integer> entry = history.floorEntry(atTimestamp);

        if (entry == null) {
            return null;
        }

        // Level 4 modified: Ensure the value has not expired by the atTimestamp
        Integer valueAtTimestamp = entry.getValue();
        Integer setTime = entry.getKey();

        // Level 4 modified: Check TTL history to determine expiration
        if (ttlHistoryDatabase.containsKey(key) && ttlHistoryDatabase.get(key).containsKey(field)) {
            TreeMap<Integer, Integer> ttlHistory = ttlHistoryDatabase.get(key).get(field);
            Map.Entry<Integer, Integer> ttlEntry = ttlHistory.floorEntry(setTime);
            if (ttlEntry != null) {
                int expirationTime = ttlEntry.getKey() + ttlEntry.getValue();
                if (atTimestamp >= expirationTime) {
                    return null;
                }
            }
        }

        return valueAtTimestamp;
    }
 
    // Level 4: Helper method to update the history of changes for a field
    private void updateHistory(int timestamp, String key, String field, Integer value) {
        historyDatabase.putIfAbsent(key, new HashMap<>());
        historyDatabase.get(key).putIfAbsent(field, new TreeMap<>());

        if (value != null) {
            historyDatabase.get(key).get(field).put(timestamp, value);
        } else {
            historyDatabase.get(key).get(field).put(timestamp, null);
        }
    }
    
 // Level 4: Helper method to update the TTL history of changes for a field
    private void updateTTLHistory(int timestamp, String key, String field, int ttl) {
        ttlHistoryDatabase.putIfAbsent(key, new HashMap<>());
        ttlHistoryDatabase.get(key).putIfAbsent(field, new TreeMap<>());

        ttlHistoryDatabase.get(key).get(field).put(timestamp, ttl);
    }

}
