package doordash;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Menu_Tree_PhoneScreen {

	public static void main(String[] args) {
        // Test Example 1
        Node existing1 = new Node("a", 1, true);
        existing1.children.add(new Node("b", 2, true));
        existing1.children.add(new Node("c", 3, true));
        existing1.children.get(0).children.add(new Node("d", 4, true));
        existing1.children.get(0).children.add(new Node("e", 5, true));
        existing1.children.get(1).children.add(new Node("f", 6, true));

        Node updated1 = new Node("a", 1, true);
        updated1.children.add(new Node("c", 3, false));
        updated1.children.get(0).children.add(new Node("f", 66, true));

        System.out.println(countTotalChangedNodes(existing1, updated1)); // Expected output: 5

        // Test Example 2
        Node existing2 = new Node("a", 1, true);
        existing2.children.add(new Node("b", 2, true));
        existing2.children.add(new Node("c", 3, true));
        existing2.children.get(0).children.add(new Node("d", 4, true));
        existing2.children.get(0).children.add(new Node("e", 5, true));
        existing2.children.get(1).children.add(new Node("g", 7, true));

        Node updated2 = new Node("a", 1, true);
        updated2.children.add(new Node("b", 2, true));
        updated2.children.add(new Node("c", 3, true));
        updated2.children.get(0).children.add(new Node("d", 4, true));
        updated2.children.get(0).children.add(new Node("e", 5, true));
        updated2.children.get(0).children.add(new Node("f", 6, true));
        updated2.children.get(1).children.add(new Node("g", 7, false));

        System.out.println(countTotalChangedNodes(existing2, updated2)); // Expected output: 2
    }
	
	public static int countChangedNodes(Node existing, Node updated) {
        Map<String, Node> existingMap = new HashMap<>();
        buildMap(existing, existingMap);

        return compareTrees(updated, existingMap);
    }

    private static void buildMap(Node node, Map<String, Node> map) {
        if (node == null) {
            return;
        }
        map.put(node.key, node);
        for (Node child : node.children) {
            buildMap(child, map);
        }
    }

    private static int compareTrees(Node updated, Map<String, Node> existingMap) {
        if (updated == null) {
            return 0;
        }

        int changedCount = 0;
        Node existingNode = existingMap.get(updated.key);

        if (existingNode == null || existingNode.value != updated.value || existingNode.active != updated.active) {
            changedCount++;
        }

        if (existingNode != null) {
            existingMap.remove(updated.key);
        }

        for (Node child : updated.children) {
            changedCount += compareTrees(child, existingMap);
        }

        return changedCount;
    }

    public static int markInactiveNodes(Map<String, Node> existingMap) {
        int inactiveCount = 0;
        for (Node node : existingMap.values()) {
            if (node.active) {
                inactiveCount++;
            }
        }
        return inactiveCount;
    }

    public static int countTotalChangedNodes(Node existing, Node updated) {
        Map<String, Node> existingMap = new HashMap<>();
        buildMap(existing, existingMap);

        int changedCount = compareTrees(updated, existingMap);
        changedCount += markInactiveNodes(existingMap);

        return changedCount;
    }
	
	// Node class
	private static class Node {
	    String key;
	    int value;
	    boolean active;
	    List<Node> children;

	    Node(String key, int value, boolean active) {
	        this.key = key;
	        this.value = value;
	        this.active = active;
	        this.children = new ArrayList<>();
	    }
	}

}

/* List of questions: https://leetcode.com/company/doordash/discuss/1583430/Doordash-Questions-Consolidated
 * 
 * Source: https://leetcode.com/discuss/interview-question/1265810/Doordash-PhoneScreen/1634199
 *      other: https://leetcode.com/discuss/interview-question/1528907/doordash-phone-creen
 
// At DoorDash, menus are updated daily even hourly to keep them up-to-date. 
// Each menu can be regarded as a tree.
// When the merchant sends us the latest menu, can we calculate how many nodes has changed?

// Assume each Node structure is as below:

class Node {
    String key;
    int value;
    boolean active;
    List children;
}
// Assume there are no duplicate nodes with the same key.

// Output: Return the number of changed nodes in the tree.

// Example 1
// Existing Menu in our system:

// Existing tree
//                           a(1, T)
//                           /     \
//                    b(2, T)     c(3, T)
//                      / \              \
//            d(4, T) e(5, T)        f(6, T)
//
// Legend: In "a(1, T)", a is the key, 1 is the value, T is True for active status 

// New Menu sent by the Merchant:

// New tree
//                  a(1, T)
//                     \
//                    c(3, F)
//                     \
//                     f(66, T)
//
// Expected Answer: 5 
// Explanation: Node b, Node d, Node e are automatically set to inactive.
// The active status of Node c and the value of Node f changed as well.

// Example 2
// Existing Menu in our system:

// Existing tree
//                           a(1, T)
//                            /    \
//                    b(2, T)      c(3, T)
//                       / \           \
//             d(4, T) e(5, T)     g(7, T)
  
// New Menu sent by the Merchant:

// New tree
//                         a(1, T)
//                       /          \
//                 b(2, T)         c(3, T)
//                /  |    \              \
//       d(4, T) e(5, T)  f(6, T)      g(7, F)
//
// Expected Answer: 2 
// Explanation: Node f is a newly-added node. Node g changed from Active to inactive
 
 * */


//At DoorDash, menus are updated daily even hourly to keep them up-to-date. 
//Each menu can be regarded as a tree.
//When the merchant sends us the latest menu, can we calculate how many nodes has changed?
//
//Assume each Node structure is as below:
//
//class Node {
// String key;
// int value;
// boolean active;
// List children;
//}
//Assume there are no duplicate nodes with the same key.
//
//Output: Return the number of changed nodes in the tree.
//
//Example 1
//Existing Menu in our system:
//
//Existing tree
//                        a(1, T)
//                        /     \
//                 b(2, T)     c(3, T)
//                   / \              \
//         d(4, T) e(5, T)        f(6, T)
//
//Legend: In "a(1, T)", a is the key, 1 is the value, T is True for active status 
//
//New Menu sent by the Merchant:
//
//New tree
//               a(1, T)
//                  \
//                 c(3, F)
//                  \
//                  f(66, T)
//
//Expected Answer: 5 
//Explanation: Node b, Node d, Node e are automatically set to inactive.
//The active status of Node c and the value of Node f changed as well.
//
//Example 2
//Existing Menu in our system:
//
//Existing tree
//                        a(1, T)
//                         /    \
//                 b(2, T)      c(3, T)
//                    / \           \
//          d(4, T) e(5, T)     g(7, T)
//
//New Menu sent by the Merchant:
//
//New tree
//                      a(1, T)
//                    /          \
//              b(2, T)         c(3, T)
//             /  |    \              \
//    d(4, T) e(5, T)  f(6, T)      g(7, F)
//
//Expected Answer: 2 
//Explanation: Node f is a newly-added node. Node g changed from Active to inactive
 