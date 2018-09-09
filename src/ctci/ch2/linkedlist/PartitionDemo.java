package ctci.ch2.linkedlist;

public class PartitionDemo {

	public static void main(String[] args) {

		LinkedList list = new LinkedList();
		list.add(3);
		list.add(5);
		list.add(8);
		list.add(5);
		list.add(10);
		list.add(2);
		list.add(1);

		// partition(list, 5);

//		LinkedList.Node node = partition_2(list, 5);
		LinkedList.Node node = partition_3(list.head, 5);
		//list.head = node;

		System.out.println(">>result = " + list);
	}

	public static void partition(LinkedList list, int x) {

		LinkedList.Node node = list.head;
		LinkedList beforePartitionList = new LinkedList();
		LinkedList.Node beforePartitionNode = null;
		LinkedList afterPartitionList = new LinkedList();
		LinkedList.Node afterPartitionListNode = null;

		// iterate list
		while (node != null) {
			if (node.data < x) {
				beforePartitionList.add(node.data);
			} else {
				afterPartitionList.add(node.data);
			}
			node = node.next;
		}

		System.out.println("beforePartitionList = " + beforePartitionList);
		System.out.println("afterPartitionList = " + afterPartitionList);

		beforePartitionNode = beforePartitionList.tail;
		afterPartitionListNode = afterPartitionList.head;

		System.out.println("beforePartitionNode = " + beforePartitionNode.data);
		System.out.println("afterPartitionListNode = " + afterPartitionListNode.data);

		beforePartitionNode.next = afterPartitionListNode;

		System.out.println(">>Result : " + beforePartitionList);

	}

	public static LinkedList.Node partition_3(LinkedList.Node node, int x) {
		 LinkedList.Node head = node;
		 LinkedList.Node tail = node;
		
		 while (node != null) {
			 LinkedList.Node next = node.next;
			 if (node.data < x) {
				 /* Insert node at head. */
				 node.next= head;
				 head= node;
			 } else {
				 /* Insert node at tail. */
				 tail.next= node;
				 tail= node;
			 }
			 node = next;
		 }
		 tail.next= null;
		
		 // The head has changed, so we need to return it to the user.
		 return head;
	}

	public static LinkedList.Node partition_2(LinkedList list, int x) {

		LinkedList.Node node = list.head;
		LinkedList.Node head = node;
		LinkedList.Node tail = node;

		System.out.println("node.data = " + node.data);

		while (node != null) {
			System.out.println("node.data = " + node.data);
			if (node.data < x) {
				System.err.println("------");
				node.next = head;
				head = node;

			} else {

				System.out.println("******");
				tail.next = node;
				tail = node;
			}
			node = node.next;
		}

		tail.next = null;
		return head;

	}

}
