package test.practice.ebay;

import test.practice.ebay.LinkedList.Node;

/*
 * http://www.geeksforgeeks.org/a-linked-list-with-next-and-arbit-pointer/
 * http://www.geeksforgeeks.org/clone-linked-list-next-arbit-pointer-set-2/
 * 
 * */

public class CloneNextAndRandomPointerListDemo {

	public static void main(String[] args) {

		LinkedList list = new LinkedList();
		list.add(1);
		list.add(2);
		list.add(3);
		list.add(4);
		list.add(5);
		
		list.head.prev = list.head.next.next;
        list.head.next.prev = list.head.next.next.next;
        list.head.next.next.prev = list.head.next.next.next.next;
        list.head.next.next.next.prev = list.head.next.next.next.next.next;
        list.head.next.next.next.next.prev = list.head.next;
		
        print(list.head);
		
	}
	
	
	
	public static void print(Node head)
    {
        Node temp = head;
        while (temp != null)
        {
            Node random = temp.prev;
            int randomData = (random != null)? random.data: -1;
            System.out.println("Data = " + temp.data +
                               ", Random data = "+ randomData);
            temp = temp.next;
        }
    }

}
