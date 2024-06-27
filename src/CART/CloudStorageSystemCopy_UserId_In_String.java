package CART;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



/**
 * 
 * Copy of CloudStorageSystem 
 * 
 * In this code userId is string where as CloudStorageSystem  userId is Integer
 * 
 * Solution looks good for Level 1, 2 and 3. Level 4 tests are not passing.
 *
 */

public class CloudStorageSystemCopy_UserId_In_String {
	
	public static void main(String[] args) {
        CloudStorageSystemCopy_UserId_In_String cloudStorage = new CloudStorageSystemCopy_UserId_In_String();

        // Level 1 - Example usage -- Looks Good
        System.out.println(" ---- Level 1 ---- \n");
        System.out.println(cloudStorage.addFile("/dir1/dir2/file.txt", 10)); // returns true
        System.out.println(cloudStorage.addFile("/dir1/dir2/file.txt", 10)); // returns false
        System.out.println(cloudStorage.getFileSize("/dir1/dir2/file.txt")); // returns 10
        System.out.println(cloudStorage.getFileSize("/non-existing_file")); // returns null
        System.out.println(cloudStorage.deleteFile("/dir1/dir2/file.txt")); // returns 10
        System.out.println(cloudStorage.deleteFile("/non-existing_file")); // returns null
        
        // Level 2 - Example usage -- Looks Good
        System.out.println("\n\n ---- Level 2 ---- \n");
        System.out.println(cloudStorage.addFile("/dir1/dir2/file.txt", 10)); // returns true
        System.out.println(cloudStorage.addFile("/dir1/dir2/file2.txt", 20)); // returns true
        System.out.println(cloudStorage.addFile("/dir1/dir3/file3.txt", 15)); // returns true

        System.out.println(cloudStorage.getLargestFiles("/dir1", 2)); // returns ["/dir1/dir2/file2.txt", "/dir1/dir3/file3.txt"]
        System.out.println(cloudStorage.getLargestFiles("/dir1/dir2", 2)); // returns ["/dir1/dir2/file2.txt", "/dir1/dir2/file.txt"]
        System.out.println(cloudStorage.getLargestFiles("/dir1/dir2", 5)); // returns ["/dir1/dir2/file2.txt", "/dir1/dir2/file.txt"]
        
        // Level 3 - Example usage -- Looks Good
        System.out.println("\n\n ---- Level 3 ---- \n");
        System.out.println(cloudStorage.addUser("user1", 200)); // returns true
        System.out.println(cloudStorage.addUser("user1", 100)); // returns false, already exists
        
        System.out.println(cloudStorage.addFileBy("user1", "/dir/file.med", 50)); // returns 150
        System.out.println(cloudStorage.addFileBy("user1", "/file.big", 140)); // returns 10
        System.out.println(cloudStorage.addFileBy("user1", "/dir/file.small", 20)); // returns null
        
        System.out.println(cloudStorage.addFile("/dir/admin_file", 300)); // returns true
        
        System.out.println(cloudStorage.addUser("user2", 110)); // returns true
        
        
        System.out.println(cloudStorage.addFileBy("user2", "/dir/file.med", 45)); // returns null, already exist
        System.out.println(cloudStorage.addFileBy("user2", "/new_file", 50)); // returns 60
        
        
        System.out.println(cloudStorage.mergeUsers("user1", "user2")); // returns 70
        
        
        
        // Level 4 - Example usage -- need some work not all tests are passing
        System.out.println("\n\n ---- Level 4 ---- \n");
        System.out.println(cloudStorage.addUser("user1", 100)); // returns true
        System.out.println(cloudStorage.addFileBy("user1", "/dir1/file1.txt", 50)); // returns 50
        cloudStorage.backupUser("user1"); // creates a backup
        System.out.println(cloudStorage.addFileBy("user1", "/dir1/file2.txt", 30)); // returns 20
        cloudStorage.restoreUser("user1"); // restores the backup
        System.out.println(cloudStorage.getFileSize("/dir1/file2.txt")); // returns null
        System.out.println(cloudStorage.getFileSize("/dir1/file1.txt")); // returns 50

        
	
	}
	
	/**
	 * For question go to google doc: 
	 * 	https://docs.google.com/document/d/1i5B6oP3po0iS8289LeL7UMu3lNEXV2GtQQJPG4gIDiM/edit
	 * 
	 * */
	

    // A map to store file names and their sizes
    private HashMap<String, Integer> storage;
    
    // A map to store file names and their sizes
    private HashMap<String, User> users; // key=userId, value=User

    public CloudStorageSystemCopy_UserId_In_String() {
        this.storage = new HashMap<>();
        this.users = new HashMap<>();
        this.users.put("admin", new User("admin", Integer.MAX_VALUE)); // admin has unlimited capacity
    }

    /** Level 1 */
    /** 
     * NOTE: For Level 1: use this in Level 1, this will change for Level 3 where
		we will call addFileBy, use the method below this method 
     */
    // Adds a new file to the storage
    /*public boolean addFile(String name, int size) {
        if (storage.containsKey(name)) {
            return false;
        } else {
            storage.put(name, size);
            return true;
        }
    }*/
    
    /** NOTE: 'addFile' will change as below in Level 3 */
    // Why admin? : all queries calling the add_file operation implemented 
    // during Level 1 are run by the user with user_id "admin", who has unlimited storage capacity
    public boolean addFile(String name, int size) {
        return addFileBy("admin", name, size) != null;
    }
    

    /** Level 1 */
    // Retrieves the size of the file if it exists
    public Integer getFileSize(String name) {
        return storage.getOrDefault(name, null);
    }

    /** Level 1 */
    // Deletes the file and returns its size if it was successful
    public Integer deleteFile(String name) {
        //return storage.remove(name);
    	Integer size = storage.remove(name);
    	
    	// Added during Level 3 implementation -- look at this, trace and understand 
        if (size != null) {
            for (User user : users.values()) {
                if (user.files.containsKey(name)) {
                    user.removeFile(size);
                    break;
                }
            }
        }
        
        return size;
    }

    
    /** Level 2 */
    // Retrieves the list of the largest files with the specified prefix
    public List<String> getLargestFiles(String prefix, int k) {
        
    	List<Map.Entry<String, Integer>> filteredFiles = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : storage.entrySet()) {
            if (entry.getKey().startsWith(prefix)) {
                filteredFiles.add(entry);
            }
        }

        filteredFiles.sort((a, b) -> {
            int sizeCompare = b.getValue().compareTo(a.getValue());
            return sizeCompare != 0 ? sizeCompare : a.getKey().compareTo(b.getKey());
        });

        List<String> result = new ArrayList<>();
        for (int i = 0; i < Math.min(k, filteredFiles.size()); i++) {
            result.add(filteredFiles.get(i).getKey());
        }

        return result;
    }
    
    
    /** Level 3 */
    public boolean addUser(String userId, int capacity) {
        if (users.containsKey(userId)) {
            return false;
        } else {
            users.put(userId, new User(userId, capacity));
            return true;
        }
    }
    
    /** Level 3 */
    /* 
     * should behave in the same way as the `add_file` function in Level 1, 
     * but the added file should be owned by the user with `user_id`.
     * 
     * A new file cannot be added to the storage if doing so will exceed the user’s capacity limit.
     * Returns the remaining capacity of the user if the file is added successfully, or `nil` otherwise.
    */
    // Adds a file by a specific user
    public Integer addFileBy(String userId, String name, int size) {
        // This is probably not needed but add if any test case fails
    	if ("user0".equals(userId)) {
    	//if (userId == 0) {
            return addFile(name, size) ? Integer.MAX_VALUE : null;
        }

        User user = users.get(userId);
        if (user == null || storage.containsKey(name) || user.remainingCapacity < size) {
            return null;
        } else {
            storage.put(name, size);
            user.addFile(size);
            return user.remainingCapacity;
        }
    }
    
    
    /*
     * Should merge the account of `user_id2` with `user_id1`.
     * Ownership of all of `user_id2`'s files is transferred to `user_id1`, 
     * and any remaining storage capacity is also added to `user_id1`.
     * 
     * The operation fails if either of the users do not exist or if `user_id1` is equal to `user_id2`.
     * 
     * Returns the remaining capacity of `user_id1` after the merge, or `nil` otherwise.

     * It is guaranteed that neither `user_id1` nor `user_id2` will be `0`.
     * */
    
    // Level 3: Merges two users
    public Integer mergeUsers(String userId1, String userId2) {
        User user1 = users.get(userId1);
        User user2 = users.get(userId2);

        if (user1 == null || user2 == null || userId1 == userId2) {
            return null;
        }

        for (Map.Entry<String, Integer> entry : storage.entrySet()) {
            if (user2.files.containsKey(entry.getKey())) {
                user1.addFile(entry.getValue());  // updating capacity
                user1.files.put(entry.getKey(), entry.getValue());
            }
        }
        
        user1.remainingCapacity += user2.remainingCapacity;
        users.remove(userId2);
        
        return user1.remainingCapacity;
    }
    
    
    // Level 4: Backups the user's files and remaining capacity
    public void backupUser(String userId) {
        User user = users.get(userId);
        if (user != null) {
            user.backup();
        }
    }

    // Level 4: Restores the user's files and remaining capacity from the most recent backup
    public void restoreUser(String userId) {
        User user = users.get(userId);
        if (user != null) {
            user.restore();
        }
    }
    
    
    // User class 
    class User {
        String userId;
        int capacity;
        int remainingCapacity;
        Map<String, Integer> files;
        HashMap<String, Integer> backupFiles;
        int backupRemainingCapacity;

        User(String userId, int capacity) {
            this.userId = userId;
            this.capacity = capacity;
            this.remainingCapacity = capacity;
            this.files = new HashMap<>();
            this.backupFiles = new HashMap<>();
        }

        void addFile(int size) {
            this.remainingCapacity -= size;
            //this.files.put(name, size);
        }

        void removeFile(int size) {
            this.remainingCapacity += size;
            //this.files.remove(name);
        }
        
        // check if we need this
        void addFile(String name, int size) {
            this.remainingCapacity -= size;
            this.files.put(name, size);
        }

        void removeFile(String name, int size) {
            this.remainingCapacity += size;
            this.files.remove(name);
        }
        
        // Level 4
        void backup() {
            this.backupFiles = new HashMap<>(this.files);
            this.backupRemainingCapacity = this.remainingCapacity;
        }

        // Level 4
        void restore() {
            this.files = new HashMap<>(this.backupFiles);
            this.remainingCapacity = this.backupRemainingCapacity;
        }
    }
    
}


