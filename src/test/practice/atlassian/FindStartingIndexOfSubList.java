package test.practice.atlassian;

import ctci.ch2.linkedlist.LinkedList;

public class FindStartingIndexOfSubList {

	
/**
	URL: https://www.glassdoor.com/Interview/Atlassian-Senior-Java-Developer-Interview-Questions-EI_IE115699.0,9_KO10,31.htm 
	
	Implement a method 'find' that will find the starting index (zero based) 
	where the second list occurs as a sub-list in the first list. 
	It should return -1 if the sub-list cannot be found. 
	Arguments are always given, not empty.
	
	Sample Input 1
	list1 = (1, 2, 3)
	list2 = (2, 3)
	Sample Output 1
	1 
	
	Sample Input 2:  
		list1 = (1, 2, 3) list2 = (3, 2) 
	Sample Output 2: 
				-1 
	
*/
	
	public static void main(String[] args) {

		LinkedList list1 = new LinkedList();
		list1.add(1);
		list1.add(2);
		list1.add(3);
		
		LinkedList list2 = new LinkedList();
		list2.add(3);
		list2.add(2);
		
		System.out.println(">>RESULT:: "+find(list1, list2));

	}
	
	public static int find(LinkedList list1, LinkedList list2) {
		
		String sList1 = listAsString(list1.head);
		String sList2 = listAsString(list2.head);
		
		System.out.println("sList1="+sList1+", sList2="+sList2);
		
		return sList1.indexOf(sList2);
	}
	
	public static String listAsString(LinkedList.Node listNode) {
		
		String s = "";
		
		while(listNode != null) {
			s += listNode.data;
			listNode = listNode.next;
		}
		
		return s;
	}

}
