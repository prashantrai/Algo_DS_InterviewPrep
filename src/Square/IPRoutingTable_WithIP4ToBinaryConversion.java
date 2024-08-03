package Square;

import java.util.HashMap;
import java.util.Map;

public class IPRoutingTable_WithIP4ToBinaryConversion {

	public static void main(String[] args) {
		IPRoutingTable_WithIP4ToBinaryConversion routingTable = new IPRoutingTable_WithIP4ToBinaryConversion();

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

    public IPRoutingTable_WithIP4ToBinaryConversion() {
        root = new TrieNode();
        defaultPort = null; // Part 2
    }

    // Part 3: Converts an IPv4 address to its binary representation
    private String ipv4ToBinary(String ip) {
        StringBuilder binaryString = new StringBuilder();
        String[] octets = ip.split("\\.");
        for (String octet : octets) {
            int octetInt = Integer.parseInt(octet);
            
            // %8s to make the value 8 bit long by adding remaining spaces and then replace
            // all spaces with 0
            System.out.println("binaryString: " + Integer.toBinaryString(octetInt));
            System.out.println("=>:" + String.format("%8s", Integer.toBinaryString(octetInt)));
            
            String binaryOctet 
            	= String.format("%8s", Integer.toBinaryString(octetInt)).replace(' ', '0');
            
            System.out.println("binaryOctet: " + binaryOctet);
            
            binaryString.append(binaryOctet);
        }
        return binaryString.toString();
    }

    public void insert(String ipPrefix, int port) {
        // Part 3: Convert IP prefix to binary if it's in IPv4 format
        if (ipPrefix.contains(".")) {
            ipPrefix = ipv4ToBinary(ipPrefix);
        }

        TrieNode currentNode = root;
        for (char bit : ipPrefix.toCharArray()) {
            currentNode.children.putIfAbsent(bit, new TrieNode());
            currentNode = currentNode.children.get(bit);
        }
        currentNode.port = port;
    }

    public int lookup(String ip) {
        // Part 3: Convert IP to binary if it's in IPv4 format
        if (ip.contains(".")) {
            ip = ipv4ToBinary(ip);
        }

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

        if (lastPort != null) {
            return lastPort;
        } else {
            return defaultPort != null ? defaultPort : -1; // Part 2: Return defaultPort if no matching prefix found
        }
    }

    public void setDefaultPort(int port) { // Part 2
        defaultPort = port;
    }
    
    
    class TrieNode {
        Map<Character, TrieNode> children;
        Integer port;

        public TrieNode() {
            children = new HashMap<>();
            port = null;
        }
    }
	
}
