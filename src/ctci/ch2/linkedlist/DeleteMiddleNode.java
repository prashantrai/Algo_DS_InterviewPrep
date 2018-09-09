package ctci.ch2.linkedlist;

public class DeleteMiddleNode {

	public static void main(String[] args) {


		LinkedList list = new LinkedList();
		list.add(1);
		list.add(12);
		list.add(3);
		list.add(4);
		list.add(5);
		
		list.remove(3);
		
		System.out.println(list);
		
		
	
	}

}
