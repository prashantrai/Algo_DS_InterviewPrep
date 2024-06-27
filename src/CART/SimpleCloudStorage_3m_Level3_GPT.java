package CART;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/*
 * All tests passed
 * 
 * Implementation Covered: Level 1, 2 and 3
 * */

public class SimpleCloudStorage_3m_Level3_GPT {

	// Main method for testing the functionality
    public static void main(String[] args) {
    	SimpleCloudStorage_3m_Level3_GPT storage = new SimpleCloudStorage_3m_Level3_GPT();

    	
    	// Level 1
    	System.out.println("\n\n---- Level 1 ---- \n");
        // Test addFile -- written by GPT in first try
        /*System.out.println(storage.addFile("/dir1/dir2/file.txt", 10)); // Should return true
        System.out.println(storage.addFile("/dir1/file.txt", 15)); // Should return true
        System.out.println(storage.addFile("/dir1/file.txt", 15)); // Should return false

        // Test copyFile -- written by GPT in first try
        System.out.println(storage.copyFile("/dir1/dir2/file.txt", "/dir1/file.txt")); // Should return false
        System.out.println(storage.copyFile("/not-existing.file", "/dir1/file.txt")); // Should return false
        System.out.println(storage.copyFile("/dir1/dir2/file.txt", "/dir1/copy_of_file.txt")); // Should return true

        // Test getFileSize -- written by GPT in first try
        System.out.println(storage.getFileSize("/dir1/file.txt")); // Should return 15
        System.out.println(storage.getFileSize("/not-existing.file")); // Should return null
		*/ 
    	
    	// All tests passed
        // Additional Tests - from problem
        System.out.println(storage.addFile("/dir1/dir2/file.txt", 10)); // Should return true
        
        System.out.println(storage.copyFile("/not-existing.file", "/dir1/file.txt")); // Should return false
        System.out.println(storage.copyFile("/dir1/dir2/file.txt", "/dir1/file.txt")); // Should return true
        
        System.out.println(storage.addFile("/dir1/file.txt", 15)); // Should return false
        
        System.out.println(storage.copyFile("/dir1/file.txt", "/dir1/dir2/file.txt")); // Should return false, already exists
        
        System.out.println(storage.getFileSize("/dir1/file.txt")); // Should return 10
        System.out.println(storage.getFileSize("/not-existing.file")); // Should return null

        
        // Level 2 - Test findFiles -- all tests passed
        System.out.println("\n\n---- Level 2 ---- \n");
        
//        System.out.println(storage.addFile("/test/prefix_suffix.txt", 20)); // Should return true
//        System.out.println(storage.addFile("/test/prefix_suffix.doc", 25)); // Should return true
//        System.out.println(storage.addFile("/test/prefix_other.txt", 30)); // Should return true
//        System.out.println(storage.addFile("/test/other_suffix.txt", 35)); // Should return true
//
//        System.out.println(storage.findFiles("/test/prefix", "suffix.txt")); // Should return ["/test/prefix_suffix.txt (20)"]
//        System.out.println(storage.findFiles("/test", ".txt")); // Should return ["/test/other_suffix.txt (35)", "/test/prefix_other.txt (30)", "/test/prefix_suffix.txt (20)"]
//        System.out.println(storage.findFiles("/invalid", ".txt")); // Should return []

        // New Tests
        System.out.println(storage.addFile("/root/dir/another dir/file.mp3", 10)); // Should return true
        System.out.println(storage.addFile("/root/file.mp3", 5)); // Should return true
        System.out.println(storage.addFile("/root/music/file.mp3", 7)); // Should return true
        System.out.println(storage.copyFile("/root/music/file.mp3", "/root/dir/file.mp3")); // Should return true

        System.out.println(storage.findFiles("/root", ".mp3")); // Should return ["/root/dir/another dir/file.mp3 (10)", "/root/dir/file.mp3 (7)", "/root/file.mp3 (5)"]
        System.out.println(storage.findFiles("/root", "file.txt")); // Should return []
        System.out.println(storage.findFiles("/dir", "file.mp3")); // Should return []
        
        

        // Level 3 Tests -- all test passed
        /** Level 3 */ 
        System.out.println("\n\n---- Level 3 ---- \n");
        System.out.println(storage.addUser("user1", 125)); // Should return true
        System.out.println(storage.addUser("user1", 100)); // Should return false
        System.out.println(storage.addUser("user2", 100)); // Should return true

        System.out.println(storage.addFileBy("user1", "/dir/file.big", 50)); // Should return 75
        System.out.println(storage.addFileBy("user1", "/file.med", 30)); // Should return 45
        System.out.println(storage.addFileBy("user2", "/file.med", 40)); // Should return null

        // Should return true
        System.out.println("Expected:true, Actual: "+storage.copyFile("/file.med", "/dir/another/file.med")); 
        // Should return false, user1 doesn't have enough storage
        System.out.println("Expected: false, Actual: "+storage.copyFile("/file.med", "/dir/another/another/file.med")); 

        System.out.println(storage.addFileBy("user1", "/dir/file.small", 10)); // Should return 5
        System.out.println(storage.addFile("/dir/admin_file", 200)); // Should return true
        System.out.println(storage.addFileBy("user1", "/dir/file.small", 5)); // Should return null
        System.out.println(storage.addFileBy("user1", "/my_folder/file.huge", 100)); // Should return null
        System.out.println(storage.addFileBy("user3", "/my_folder/file.huge", 100)); // Should return null, user3 doesn't exists

        System.out.println(storage.updateCapacity("user1", 300)); // Should return 0
        System.out.println(storage.updateCapacity("user1", 50)); // Should return 2
        
        // Should return null, this is incorrect test case in problem because null be 
        // returned only when user doesn't exists but in this case user2 exists
        // and valid output will be 0
        System.out.println(storage.updateCapacity("user2", 1000)); 
        
        // Should return null, user3 doesn't exists
        System.out.println(storage.updateCapacity("user3", 1000)); 
    }
	
    // Inner class to represent a file with its size and owner
    private static class File {
        String name;
        int size;
        String owner;

        File(String name, int size, String owner) {
            this.name = name;
            this.size = size;
            this.owner = owner;
        }
    }

    // Inner class to represent a user with a capacity limit
    private static class User {
        String userId;
        int capacity;
        int usedCapacity;

        User(String userId, int capacity) {
            this.userId = userId;
            this.capacity = capacity;
            this.usedCapacity = 0;
        }
    }

    // HashMaps to store files and users
    private Map<String, File> storage;
    private Map<String, User> users;

    public SimpleCloudStorage_3m_Level3_GPT() {
        this.storage = new HashMap<>();
        this.users = new HashMap<>();
        this.users.put("admin", new User("admin", Integer.MAX_VALUE)); // admin has unlimited capacity
    }

    // Level 1 Methods

    public boolean addFile(String name, int size) {
        return addFileBy("admin", name, size) != null;
    }

    public boolean copyFile(String nameFrom, String nameTo) {
        if (!storage.containsKey(nameFrom) || storage.containsKey(nameTo)) {
            return false;
        }
        File originalFile = storage.get(nameFrom);
        int size = originalFile.size;
        
        // NOTE: in Level 1 only, this will change in Level 3
        // storage.put(nameTo, new File(nameTo, size));
        
        
        // Level 3: Update the as below in Level 3 only and not before that
        
        // check for remaining capacity  before copy
        String userId = originalFile.owner;
        User user = users.get(userId);
        int requiredCapacity = user.usedCapacity + size;
        
        if (requiredCapacity > user.capacity) {
            return false;
        }
        // Update the usedCapacity
        user.usedCapacity +=size; 
        
        // add to storage
        storage.put(nameTo, new File(nameTo, originalFile.size, originalFile.owner));
        
        return true;
    }
    

    public Integer getFileSize(String name) {
        File file = storage.get(name);
        if (file == null) {
            return null;
        }
        return file.size;
    }

    public List<String> findFiles(String prefix, String suffix) {
        List<File> matchingFiles = new ArrayList<>();

        for (File file : storage.values()) {
            if (file.name.startsWith(prefix) && file.name.endsWith(suffix)) {
                matchingFiles.add(file);
            }
        }

        Collections.sort(matchingFiles, new Comparator<File>() {
            @Override
            public int compare(File f1, File f2) {
                if (f1.size != f2.size) {
                    return Integer.compare(f2.size, f1.size);
                } else {
                    return f1.name.compareTo(f2.name);
                }
            }
        });

        List<String> result = new ArrayList<>();
        for (File file : matchingFiles) {
            result.add(String.format("%s (%d)", file.name, file.size));
        }

        return result;
    }

    // Level 3 Methods

    /**
     * Adds a new user to the system.
     * 
     * @param userId   The user ID
     * @param capacity The storage capacity for the user in bytes
     * @return True if the user was added successfully, False if the user already exists
     */
    public boolean addUser(String userId, int capacity) {
        if (users.containsKey(userId)) {
            return false;
        }
        users.put(userId, new User(userId, capacity));
        return true;
    }

    /**
     * Adds a new file to the storage owned by a specific user.
     * 
     * @param userId The user ID
     * @param name   The name of the file
     * @param size   The size of the file in bytes
     * @return The remaining capacity for the user if the file was added successfully, None otherwise
     */
    public Integer addFileBy(String userId, String name, int size) {
        if (!users.containsKey(userId) || storage.containsKey(name)) {
            return null;
        }
        User user = users.get(userId);
        if (user.usedCapacity + size > user.capacity) {
            return null;
        }
        storage.put(name, new File(name, size, userId));
        user.usedCapacity += size;
        return user.capacity - user.usedCapacity;
    }

    /**
     * Updates the capacity of a user and removes files if necessary.
     * 
     * @param userId   The user ID
     * @param capacity The new capacity for the user in bytes
     * @return The number of removed files, or None if the user does not exist
     */
    public Integer updateCapacity(String userId, int capacity) {
        if (!users.containsKey(userId)) {
            return null;
        }
        User user = users.get(userId);
        user.capacity = capacity;

        // Check if we need to remove files
        if (user.usedCapacity <= capacity) {
            return 0;
        }

        // Find and sort user's files by size descending and name lexicographically
        List<File> userFiles = new ArrayList<>();
        for (File file : storage.values()) {
            if (file.owner.equals(userId)) {
                userFiles.add(file);
            }
        }
        Collections.sort(userFiles, new Comparator<File>() {
            @Override
            public int compare(File f1, File f2) {
                if (f1.size != f2.size) {
                    return Integer.compare(f2.size, f1.size);
                } else {
                    return f1.name.compareTo(f2.name);
                }
            }
        });

        // Remove largest files until the used capacity is within the new limit
        int removedFilesCount = 0;
        for (File file : userFiles) {
            storage.remove(file.name);
            user.usedCapacity -= file.size;
            removedFilesCount++;
            if (user.usedCapacity <= capacity) {
                break;
            }
        }
        return removedFilesCount;
    }

    
}
