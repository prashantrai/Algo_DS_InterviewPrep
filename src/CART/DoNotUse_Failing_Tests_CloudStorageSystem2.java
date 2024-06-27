package CART;

import java.util.*;

public class DoNotUse_Failing_Tests_CloudStorageSystem2 {

    private HashMap<String, Integer> storage;
    private HashMap<Integer, User> users;

    public DoNotUse_Failing_Tests_CloudStorageSystem2() {
        this.storage = new HashMap<>();
        this.users = new HashMap<>();
        this.users.put(0, new User(0, Integer.MAX_VALUE)); // User with unlimited capacity
    }

    // Level 1: Adds a file with unlimited capacity (userId 0)
    public boolean addFile(String name, int size) {
        if (storage.containsKey(name)) {
            return false;
        } else {
            storage.put(name, size);
            return true;
        }
    }

    // Level 3: Adds a user with a specified capacity
    public boolean addUser(int userId, int capacity) {
        if (users.containsKey(userId)) {
            return false;
        } else {
            users.put(userId, new User(userId, capacity));
            return true;
        }
    }

    // Level 3: Adds a file by a specific user
    public Integer addFileBy(int userId, String name, int size) {
        if (userId == 0) {
            return addFile(name, size) ? Integer.MAX_VALUE : null;
        }

        User user = users.get(userId);
        if (user == null || storage.containsKey(name) || user.remainingCapacity < size) {
            return null;
        } else {
            storage.put(name, size);
            user.addFile(name, size);
            return user.remainingCapacity;
        }
    }

    // Level 1: Retrieves the size of a file
    public Integer getFileSize(String name) {
        return storage.getOrDefault(name, null);
    }

    // Level 1: Deletes a file and returns its size
    public Integer deleteFile(String name) {
        Integer size = storage.remove(name);
        if (size != null) {
            for (User user : users.values()) {
                if (user.files.containsKey(name)) {
                    user.removeFile(name, size);
                    break;
                }
            }
        }
        return size;
    }

    // Level 2: Retrieves the largest files with a specific prefix
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

    // Level 3: Merges two users
    public Integer mergeUsers(int userId1, int userId2) {
        User user1 = users.get(userId1);
        User user2 = users.get(userId2);

        if (user1 == null || user2 == null || userId1 == userId2) {
            return null;
        }

        for (Map.Entry<String, Integer> entry : user2.files.entrySet()) {
            String fileName = entry.getKey();
            Integer fileSize = entry.getValue();
            
            // Check if the file is not already owned by user1
            if (!user1.files.containsKey(fileName)) {
                user1.addFile(fileName, fileSize);
            }
        }

        user1.remainingCapacity += user2.remainingCapacity;
        users.remove(userId2);

        return user1.remainingCapacity;
    }

    public static void main(String[] args) {
    	DoNotUse_Failing_Tests_CloudStorageSystem2 cloudStorage = new DoNotUse_Failing_Tests_CloudStorageSystem2();

        // Example usage
        System.out.println(cloudStorage.addUser(1, 50)); // returns true
        System.out.println(cloudStorage.addUser(1, 100)); // returns false
        System.out.println(cloudStorage.addFileBy(1, "/dir1/file1.txt", 40)); // returns 10
        System.out.println(cloudStorage.addFileBy(1, "/dir1/file2.txt", 20)); // returns null
        System.out.println(cloudStorage.addUser(2, 70)); // returns true
        System.out.println(cloudStorage.addFileBy(2, "/dir1/file2.txt", 20)); // returns 50
        System.out.println(cloudStorage.mergeUsers(1, 2)); // returns 60
    }

    class User {
        int userId;
        int capacity;
        int remainingCapacity;
        HashMap<String, Integer> files;

        User(int userId, int capacity) {
            this.userId = userId;
            this.capacity = capacity;
            this.remainingCapacity = capacity;
            this.files = new HashMap<>();
        }

        void addFile(String name, int size) {
            this.remainingCapacity -= size;
            this.files.put(name, size);
        }

        void removeFile(String name, int size) {
            this.remainingCapacity += size;
            this.files.remove(name);
        }
    }
}


