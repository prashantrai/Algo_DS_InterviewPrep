package ctci.ch2.linkedlist;

public class IntersectingListsDemo {

	public static void main(String[] args) {

		LinkedList l1 = new LinkedList();
		l1.add(1);
		l1.add(2);
		l1.add(3);
		l1.add(4);
		l1.add(5);
		l1.add(6);
		l1.add(7);
		
		LinkedList l2 = new LinkedList();
		l2.add(11);
		l2.add(12);
		//l2.add(13);
		
		//--Create intersecting Linked Lists
		createIntersectingNode(l1.head, l2.head);
		
		System.out.println("l1: "+l1);
		System.out.println("l2: "+l2);
		
		LinkedList.Node node = findIntersection(l1.head, l2.head);
		if (node != null)
			System.out.println(">>> RESULT : " + node.data);
		else 
			System.err.println(">>> RESULT : No intersection found");
		
	}
	
	public static LinkedList.Node findIntersection(LinkedList.Node node1, LinkedList.Node node2) {
		
		//get the tail and size
		Results result_1 = getTailAndSize(node1);
		Results result_2 = getTailAndSize(node2);
		
		if(result_1.tail != result_2.tail) return null;
		
		//get the longer and sorter list
		LinkedList.Node shorter = result_1.size < result_2.size ? node1 : node2;
		LinkedList.Node longer  = result_1.size < result_2.size ? node2 : node1;

		//--move the pointer in the longer list based on length diff between both the lists
		longer = getKthNode(longer, Math.abs(result_1.size - result_2.size));
		System.out.println("kth node = "+ longer.data);
		
		//--iterate and compare both lists
		while (longer != null) {
			
			if(longer == shorter) {
				return longer;
			}
			longer = longer.next; 
			shorter = shorter.next; 
		}
		
		
		return null;
	}
	
	public static LinkedList.Node getKthNode(LinkedList.Node node, int k) {
		
		while (k > 0) {
			node = node.next;
			k--;
		}
		return node;
	}
	
	
	public static Results getTailAndSize(LinkedList.Node node) {
		
		int size = 0;
		
		while (node.next != null) {
			size++;
			node = node.next;
		}
		size++;
		System.out.println("tail: "+node.data);
		System.out.println("size: "+size);
		return new Results(node, size);
	}
	
	public static void createIntersectingNode(LinkedList.Node node1, LinkedList.Node node2) {
		
		while(node2.next != null) {
			node2 = node2.next;
		}
		
		while (node1 != null) {
			
			if(node1.data == 5) {
				node2.next = node1;
				break;
			}
			node1 = node1.next;
		}
		
	}

}


class Results {
	
	LinkedList.Node tail;
	int size;
	
	Results(LinkedList.Node tail, int size) {
		this .tail = tail;
		this.size = size;
	}
}