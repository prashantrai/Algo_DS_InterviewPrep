package Square;

import java.util.HashMap;
import java.util.Map;

public class IPRoutingTable_IP4_NoBinaryConversion {

	public static void main(String[] args) {
		IPRoutingTable_IP4_NoBinaryConversion routingTable = new IPRoutingTable_IP4_NoBinaryConversion();

        routingTable.insert("34.126", 301); // Part 3
        routingTable.insert("34.120", 120); // Part 3

        System.out.println(routingTable.lookup("34.126.7.1")); // Output: 301 (Part 3)
        System.out.println(routingTable.lookup("34.120.8.1")); // Output: 120 (Part 3)
        System.out.println(routingTable.lookup("192.168.0.1")); // Output: -1 (Part 3)

        routingTable.setDefaultPort(999); // Part 2
        System.out.println(routingTable.lookup("192.168.0.1")); // Output: 999 (Part 2 & Part 3)
    }

	
	private TrieNode root;
    private Integer defaultPort; // Part 2

    public IPRoutingTable_IP4_NoBinaryConversion() {
        root = new TrieNode();
        defaultPort = null; // Part 2
    }

    public void insert(String ipPrefix, int port) {
        TrieNode currentNode = root;
        String[] parts = ipPrefix.split("\\.");
        for (String part : parts) {
            currentNode.children.putIfAbsent(part, new TrieNode());
            currentNode = currentNode.children.get(part);
        }
        currentNode.port = port;
    }

    public int lookup(String ip) {
        TrieNode currentNode = root;
        Integer lastPort = null;
        String[] parts = ip.split("\\.");

        for (String part : parts) {
            if (currentNode.port != null) {
                lastPort = currentNode.port;
            }
            if (currentNode.children.containsKey(part)) {
                currentNode = currentNode.children.get(part);
            } else {
                break;
            }
        }

        if (lastPort != null) {
            return lastPort;
        } else {
            return defaultPort != null ? defaultPort : -1; // Part 2: Return defaultPort if no matching prefix found
        }
    }

    public void setDefaultPort(int port) { // Part 2
        defaultPort = port;
    }
    
	
	private static class TrieNode {
	    Map<String, TrieNode> children;  //Part 3: updated to String from Character
	    Integer port;

	    public TrieNode() {
	        children = new HashMap<>();
	        port = null;
	    }
	}
}
