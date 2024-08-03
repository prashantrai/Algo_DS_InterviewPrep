package Square;

import java.util.HashMap;
import java.util.Map;

public class IPRoutingTable {

	public static void main(String[] args) {
        IPRoutingTable routingTable = new IPRoutingTable();

        routingTable.insert("10", 3);
        routingTable.insert("010", 4);
        routingTable.insert("1111", 5);

        System.out.println(routingTable.lookup("010110")); // Output: 4
        System.out.println(routingTable.lookup("111111")); // Output: 5
        System.out.println(routingTable.lookup("100000")); // Output: 3
        System.out.println(routingTable.lookup("000000")); // Output: -1 (no matching prefix)
        
        // Part 2: default port
        routingTable.setDefaultPort(999); // Part 2
        System.out.println(routingTable.lookup("000000")); // Output: 999
        
    }
	
	/* Part 1: 
	 * To implement an efficient IP routing table with the ability to insert IP prefixes 
	 * and perform lookups based on the longest matching prefix, we can use 
	 * a Trie (prefix tree) data structure. This structure is suitable for prefix matching 
	 * problems because it allows efficient insertion and lookup operations.
	 */
	
	private TrieNode root;
    private Integer defaultPort; // Part 2

    public IPRoutingTable() {
        root = new TrieNode();
        defaultPort = null; // Part 2
    }

    public void insert(String ipPrefix, int port) { // Part 2
        TrieNode currentNode = root;
        for (char bit : ipPrefix.toCharArray()) {
            currentNode.children.putIfAbsent(bit, new TrieNode());
            currentNode = currentNode.children.get(bit);
        }
        currentNode.port = port;
    }

    public int lookup(String ip) { // Part 2
        TrieNode currentNode = root;
        Integer lastPort = null;

        for (char bit : ip.toCharArray()) {
            if (currentNode.port != null) {
                lastPort = currentNode.port;
            }
            if (currentNode.children.containsKey(bit)) {
                currentNode = currentNode.children.get(bit);
            } else {
                break;
            }
        }

        // part 1
        // return lastPort != null ? lastPort : -1; // Return -1 if no matching prefix found

        // Part 2 - update as below
        if (lastPort != null) {
            return lastPort;
        } else {
        	// Part 2: Return defaultPort if no matching prefix found
            return defaultPort != null ? defaultPort : -1; 
        }
    }

    public void setDefaultPort(int port) { // Part 2
        defaultPort = port;
    }
	
    
	private static class TrieNode {
	    Map<Character, TrieNode> children;
	    Integer port;

	    public TrieNode() {
	        children = new HashMap<>();
	        port = null;
	    }
	}
	
}


/*
Problem: [https://leetcode.com/company/square/discuss/3973772/Square-or-Phone-or-IP-Routing-Table]

Part 1: 
We want to construct an IP routing table that matches binary IP addresses with a port 
based on the longest matching prefix. It needs to support 2 functions: 
	insert(ipPrefix: string, port: number): void and 
	lookup(ip: string): number. 

IP addresses are represented as binary strings that are 6 characters long. 

Examples:

IP Prefix    Port
==================
10          => 3
010         => 4
1111        => 5

lookup("010110") = 4
lookup("111111") = 5


Part 2: Now we want to add support for a default port for when no entry in the table matches. We should be able to set this and have it returned no matter the state of our table. For example:

const ip = new RoutingTable();
ip.insert("10", 3);
ip.insert("010", 4);
ip.insert("1111", 5);

console.log(ip.lookup("010110")); // 4
console.log(ip.lookup("111111")); // 5
console.log(ip.lookup("000000")); // -1

ip.setDefaultPort(999);
console.log(ip.lookup("000000")); // 999






*/