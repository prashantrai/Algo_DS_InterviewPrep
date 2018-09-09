package test.ge;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

//--Delete even nodes from a linked list

public class GETest {

	public static void main(String[] args)  throws IOException{
        Scanner in = new Scanner(System.in);
        final String fileName = System.getenv("OUTPUT_PATH");
        BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
        LinkedListNode res;
        
        int _list_size = 0;
        _list_size = Integer.parseInt(in.nextLine());
        int _list_i;
        int _list_item;
        LinkedListNode _list = null;
        LinkedListNode _list_tail = null;
        for(_list_i = 0; _list_i < _list_size; _list_i++) { 
            _list_item = Integer.parseInt(in.nextLine().trim());
            if(_list_i == 0) {
            	_list = _insert_node_into_singlylinkedlist(_list, _list_tail, _list_item);
            	_list_tail = _list;
	        }
	        else {
	        	_list_tail = _insert_node_into_singlylinkedlist(_list, _list_tail, _list_item);
	        }
        }
        
        res = deleteEven(_list);
        while (res != null) {
            bw.write(String.valueOf(res.val));
            bw.newLine();
            res = res.next;
        }
        
        bw.close();
    }
	
	public static class LinkedListNode{
        int val;
        LinkedListNode next;
    
        LinkedListNode(int node_value) {
            val = node_value;
            next = null;
        }
    };
    
    public static LinkedListNode _insert_node_into_singlylinkedlist(LinkedListNode head, LinkedListNode tail, int val){
        if(head == null) {
            head = new LinkedListNode(val);
            tail = head;
        }
        else {
            tail.next = new LinkedListNode(val);
            tail = tail.next;
        }
        return tail;
    }

	static LinkedListNode deleteEven(LinkedListNode list) {

        if(list == null) 
            return null;
        
        LinkedListNode head = list;  //saving head position
        LinkedListNode prev = list;
        
        while (prev.next != null) {
            if (isEven(prev.next))
                prev.next = prev.next.next;   // next is even: delete it
            else
                prev = prev.next;             // next is not even: proceed
        }
        
        // delete head if it's even
        if (isEven(head))
            head = head.next;
       
        return head;
    }

    private static boolean isEven(LinkedListNode node) {
        return node.val % 2 == 0;
    }
	
}
