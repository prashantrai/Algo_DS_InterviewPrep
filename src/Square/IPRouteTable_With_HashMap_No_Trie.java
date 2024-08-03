package Square;

import java.util.HashMap;

public class IPRouteTable_With_HashMap_No_Trie {

	public static void main(String[] args) {
		IPRouteTable_With_HashMap_No_Trie routingTable = new IPRouteTable_With_HashMap_No_Trie();
        routingTable.insert("10", 3);
        routingTable.insert("010", 4);
        routingTable.insert("1111", 5);

        System.out.println("Expected: 4, Actual: "+ routingTable.lookup("010110")); // Output: 4
        System.out.println("Expected: 5, Actual: "+ routingTable.lookup("111111")); // Output: 5
        System.out.println("Expected: -1, Actual: "+ routingTable.lookup("000000")); // Output: -1

        // Part 2: Set and test the default port
        routingTable.setDefaultPort(999);
        System.out.println("Expected: 999, Actual: "+ routingTable.lookup("000000")); // Output: 999
        
        // Part 3: Insert IPv4 prefixes and test lookup
        routingTable.insert("34.126", 301);
        routingTable.insert("34.120", 120);

        System.out.println("Expected: 301, Actual: "+ routingTable.lookup("34.126.7.1")); // Output: 301
        System.out.println("Expected: 120, Actual: "+ routingTable.lookup("34.120.8.1")); // Output: 120
        System.out.println("Expected: -1, Actual: "+ routingTable.lookup("192.168.0.1")); // Output: -1
    }
	
	
	
	private HashMap<String, Integer> prefixToPortMap;
    private int defaultPort; // Part 2: Field to store the default port
    private boolean defaultPortSet; // Part 2: Flag to check if default port is set

    
    public IPRouteTable_With_HashMap_No_Trie() {
        prefixToPortMap = new HashMap<>();
        defaultPort = -1; // Part 2: Initialize default port
        defaultPortSet = false; // Part 2: Initialize default port flag
    }

    
    // Insert a prefix and its associated port into the routing table
    public void insert(String ipPrefix, int port) {
        prefixToPortMap.put(ipPrefix, port);
    }

    // Part 2: Method to set the default port
    public void setDefaultPort(int port) {
        defaultPort = port;
        defaultPortSet = true;
    }

    // Lookup the port for a given IP based on the longest matching prefix
    public int lookup(String ip) {
        int longestMatchLength = -1;
        int port = -1;

        // Check all prefixes to find the longest matching prefix
        // Iterate over HashMap keys, this is brute force solution
        // For optimation lookup build a Trie (prefix tree)
        for (String prefix : prefixToPortMap.keySet()) {
            if (ip.startsWith(prefix) && prefix.length() > longestMatchLength) {
                longestMatchLength = prefix.length();
                port = prefixToPortMap.get(prefix);
            }
        }

        // Part 2: If no matching prefix found, return the default port if set
        if (longestMatchLength == -1 && defaultPortSet) {
            return defaultPort;
        }

        return port; // for Part 1, just return port
    }

    

}
