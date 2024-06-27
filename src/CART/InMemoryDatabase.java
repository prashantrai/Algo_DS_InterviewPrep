package CART;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryDatabase {

    //  all tests passed
    public static void test_Level_1() {
        InMemoryDatabase db = new InMemoryDatabase();
        
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
        InMemoryDatabase db = new InMemoryDatabase();
        
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
        InMemoryDatabase db = new InMemoryDatabase();
        
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
    
	public static void main(String[] args) {
		test_Level_1();
		test_Level_2();
		test_Level_3();
	}
	
    
	
	
	
	private Map<String, Map<String, Integer>> database;
    
    // Level 3
    private Map<String, Map<String, Integer>> ttlDatabase;

    public InMemoryDatabase() {
        this.database = new HashMap<>();
        this.ttlDatabase = new HashMap<>();	 
    }

    public void set(int timestamp, String key, String field, int value) {
        database.putIfAbsent(key, new HashMap<>());
        database.get(key).put(field, value);
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
                return true;
            }
        }
        return false;
    }
    
    
    
    // Level 3
    public boolean compareAndSetWithTTL(int timestamp, String key, String field, int expectedValue, int newValue, int ttl) {
        if (compareAndSet(timestamp, key, field, expectedValue, newValue)) {
            ttlDatabase.get(key).put(field, timestamp + ttl);
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
            	
            	if(database.containsKey(key) && database.get(key).containsKey(field))
            		database.get(key).remove(field);
            	
            	if(ttlDatabase.containsKey(key) && ttlDatabase.get(key).containsKey(field))
            		ttlDatabase.get(key).remove(field);
                
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

}
