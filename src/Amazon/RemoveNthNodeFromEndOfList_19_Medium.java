package Amazon;

public class RemoveNthNodeFromEndOfList_19_Medium {

	public static void main(String[] args) {
        // 1) Example: [1,2,3,4,5], n = 2  => [1,2,3,5]
        testCase(new int[]{1, 2, 3, 4, 5}, 2);

        // 2) Single node, remove that node: [1], n = 1 => []
        testCase(new int[]{1}, 1);

        // 3) Two nodes, remove last: [1,2], n = 1 => [1]
        testCase(new int[]{1, 2}, 1);

        // 4) Two nodes, remove first (head): [1,2], n = 2 => [2]
        testCase(new int[]{1, 2}, 2);

        // 5) Remove head in a larger list: [10,20,30,40], n = 4 => [20,30,40]
        testCase(new int[]{10, 20, 30, 40}, 4);

        // 6) Remove middle node: [1,2,3,4,5,6], n = 3 => [1,2,3,5,6]
        testCase(new int[]{1, 2, 3, 4, 5, 6}, 3);
    }

    private static void testCase(int[] arr, int n) {
        ListNode head = buildList(arr);
        System.out.println("Original list: " + listToString(head));
        System.out.println("n = " + n);

        head = removeNthFromEnd(head, n);

        System.out.println("After removal: " + listToString(head));
        System.out.println("------------------------------------------------");
    }
	
	/**
     * Removes the n-th node from the end of the list and returns the new head.
     * One-pass solution using two pointers and a dummy head.
     */

     // Time: O(L), Space: O(1).
    
    public static ListNode removeNthFromEnd(ListNode head, int n) {
        // Dummy node simplifies edge cases like removing the original head
        ListNode dummy = new ListNode(0);
        dummy.next = head;

        // fast and slow both start from dummy
        ListNode fast = dummy;
        ListNode slow = dummy;

        // 1) Move fast (n + 1) steps ahead to create a gap of n nodes
        // between fast and slow
        for (int i = 0; i < n + 1; i++) {
            fast = fast.next;  // constraints guarantee we won't go out of bounds
        }

        // 2) Move both pointers until fast reaches the end
        while (fast != null) {
            fast = fast.next;
            slow = slow.next;
        }

        // 3) Now slow is just before the node to delete
        // Remove slow.next
        slow.next = slow.next.next;

        // 4) Return the (possibly new) head
        return dummy.next;
    }
    
    
    // Definition for singly-linked list.
    static class ListNode {
        int val;
        ListNode next;

        ListNode(int val) {
            this.val = val;
        }
    }

    
    //--------------------------------------------
    
    // Build a linked list from an int array.
    public static ListNode buildList(int[] arr) {
        ListNode dummy = new ListNode(0);
        ListNode tail = dummy;
        for (int v : arr) {
            tail.next = new ListNode(v);
            tail = tail.next;
        }
        return dummy.next;
    }

    // Convert list to string for printing.
    public static String listToString(ListNode head) {
        StringBuilder sb = new StringBuilder();
        ListNode curr = head;
        while (curr != null) {
            sb.append(curr.val);
            if (curr.next != null) {
                sb.append(" -> ");
            }
            curr = curr.next;
        }
        return sb.toString();
    }
    
}
