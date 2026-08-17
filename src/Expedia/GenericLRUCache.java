package Expedia;

import java.util.HashMap;
import java.util.Map;

public class GenericLRUCache<K, V> {
	
	
	// ---------- Example usage ----------
    public static void main(String[] args) {
        LRUCache<String, Integer> cache = new LRUCache<>(3);
        cache.put("A", 1);
        cache.put("B", 2);
        cache.put("C", 3);
        cache.printList(); // C -> B -> A

        cache.get("B");
        cache.printList(); // B -> C -> A

        cache.put("D", 4);
        cache.printList(); // D -> B -> C (A evicted)

        System.out.println(cache.get("A")); // null
    }
    

    // ---------- Generic Node class ----------
    private static class Node<K, V> {
        private K key;
        private V value;
        private Node<K, V> prev;
        private Node<K, V> next;

        public Node() {}

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    // ---------- Generic LRU Cache ----------
    // Time: O(1) for both put and get
    // Space: O(capacity)
    public static class LRUCache<K, V> {
        private final int capacity;
        private final Map<K, Node<K, V>> map;
        private final Node<K, V> head; // dummy head
        private final Node<K, V> tail; // dummy tail

        public LRUCache(int capacity) {
            this.capacity = capacity;
            map = new HashMap<>();
            head = new Node<>();
            tail = new Node<>();
            head.next = tail;
            tail.prev = head;
        }

        public V get(K key) {
            Node<K, V> node = map.get(key);
            if (node == null) {
                return null;
            }
            moveToHead(node);
            return node.value;
        }

        public void put(K key, V value) {
            Node<K, V> node = map.get(key);
            if (node != null) {
                node.value = value;
                moveToHead(node);
            } else {
                if (map.size() >= capacity) {
                    Node<K, V> last = tail.prev;
                    removeNode(last);
                    map.remove(last.key);
                }
                Node<K, V> newNode = new Node<>(key, value);
                addNode(newNode);
                map.put(key, newNode);
            }
        }

        private void moveToHead(Node<K, V> node) {
            removeNode(node);
            addNode(node);
        }

        private void removeNode(Node<K, V> node) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }

        private void addNode(Node<K, V> node) {
            node.prev = head;
            node.next = head.next;
            head.next.prev = node;
            head.next = node;
        }

        // Utility method – not for interview use
        public void printList() {
            if (map.isEmpty()) {
                System.out.println("Cache is empty");
                return;
            }

            Node<K, V> current = head.next;
            System.out.print("LRU Cache (most recent → least recent): ");
            while (current != tail) {
                System.out.print("[" + current.key + "=" + current.value + "]");
                if (current.next != tail) {
                    System.out.print(" -> ");
                }
                current = current.next;
            }
            System.out.println();
        }
    }

    
}