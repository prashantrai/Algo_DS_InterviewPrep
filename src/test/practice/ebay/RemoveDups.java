package test.practice.ebay;

import java.util.HashSet;
import java.util.Set;

import ctci.ch2.linkedlist.LinkedList;

public class RemoveDups {

	public static void main(String[] args) {

		LinkedList list = new LinkedList();
		fillList(list);
		
		removeDupsWithExtraBuffer(list);
		System.out.println(">> Dupes removed : "+ list);
		
		list = new LinkedList();
		fillList(list);
		removeDupsWithNoExtraBuffer(list);
		System.out.println(">> Dupes removed : "+ list);
		
	}

	
	public static void removeDupsWithExtraBuffer (LinkedList list) {
		
		Set<Integer> buffer = new HashSet<Integer>();
		
		LinkedList.Node curr = list.head;
		LinkedList.Node prev = list.head;
		
		while(curr != null) {
			
			if(buffer.contains(curr.data)) {
				prev.next = curr.next;
			} else {
				buffer.add(curr.data);
				prev  = curr;
			}
			curr = curr.next;
		}
		
		//System.out.println("buffer:: "+ buffer);
	}
	
	
	
	public static void removeDupsWithNoExtraBuffer (LinkedList list) {
		
		LinkedList.Node curr = list.head;
		
		while (curr != null) {
			LinkedList.Node runner = curr;
			while(runner.next != null) {
				if(curr.data == runner.next.data) {
					runner.next = runner.next.next;
				} else {
					runner = runner.next;
				}
			}
			curr = curr.next;
		} 
		
	}
	
	
	public static void fillList(LinkedList list) {
		list.add(5);
		list.add(2);
		list.add(13);
		list.add(3);
		list.add(4);
		list.add(12);
		list.add(12);
		list.add(4);
		list.add(1);
		list.add(2);
		list.add(4);
		list.add(5);
		list.add(5);
		list.add(1);
		
		//list.remove(0);
		
		System.out.println(list);
	}
	
	
}
