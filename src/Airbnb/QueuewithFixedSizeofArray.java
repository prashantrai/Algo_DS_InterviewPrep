package Airbnb;

import java.util.ArrayList;
import java.util.List;

/*
 * http://fabian-kostadinov.github.io/2014/11/25/implementing-a-fixed-length-fifo-queue-in-java/
 * https://massivealgorithms.blogspot.com/2019/04/implement-queue-with-limited-size-of.html
 * 
 * Problem: 
 * Assume in your programming language you only have a fixed size array of length 5. 
 * Implement a queue data structure that can get unlimited number of elements.

 */
		

public class QueuewithFixedSizeofArray {

	public static void main(String[] args) {
		
		QueuewithFixedSizeofArray queue = new QueuewithFixedSizeofArray(5);
        queue.offer(1);
        queue.offer(2);
        int res = queue.poll();
        queue.offer(3);
        queue.offer(4);
        queue.offer(5);
        queue.offer(6);
        queue.offer(7);
        queue.offer(8);
        queue.offer(9);
        
        res = queue.poll();
        System.out.println("1. res:: "+ res);
        
        //assertEquals(2, res);
        res = queue.poll();
        //assertEquals(3, res);
        System.out.println("2. res:: "+ res);
		
	}


	
	private int head;
	private int tail;
	private int size;
	private int fixedSize;
	private List<Object> headList;
	private List<Object> tailList;
	
	public QueuewithFixedSizeofArray(int fixedSize) {
		this.fixedSize = fixedSize;
		headList = new ArrayList<>();
		tailList = headList;
	}
	
	/*add value
	 * 
	 * Assume fixed size = 3 and numbers that we are adding are 1,2,3,4,5,6,7,8,9
	 * add 1 : tailList = [1]		size = 1
	 * add 2 : tailList = [1, 2]	size = 2
	 * add 3 : here  size == fixedSize -1 i.e. size == 2
	 * 			a. create a new list and add in the taliList at the last left index/place in the list.
	 * 				tailList = [1, 2, [3]] and tailList is changes to newList
	 * 				tailList = [3] 
	 * add 4 : tailList = [3, 4]
	 * add 5 : tailList = [3, 5]
	 * add 6 : tailList = [3, 5, [6]]    , same as step 3
	 * 			tailList = [6]	
	 * add 7 : tailList = [6,7]
	 * add 8 : tailList = [6,7,[8]]  , , same as step 3
	 * 			tailList = [8]
	 * add 9 : tailList = [8, 9]
	 * 
	 * We have not touched the headList and it's holding all the elements
	 * i.e. headList = [1, 2, [3, 4, [5, 6, [7, 8,[9]]]] ] 
	 */
	
	public void offer (int v) {
		if(tail == fixedSize-1) {
			List<Object> newList = new ArrayList<>();
			newList.add(v);
			tailList.add(newList);
			tailList = (List<Object>) tailList.get(tail);
			tail = 0;
		} else {
			tailList.add(v);
		}
		tail++;
		size++;
		
	}
	
	/* return top/oldest value
	 * 
	 * headList = [1, 2, [3, 4, [5, 6, [7, 8,[9]]]] ]
	 * head = 0 //--current head counter value
	 * 
	 * 1. get the first index and return
	 * 		  headList = [2, [3, 4, [5, 6, [7, 8,[9]]]] ] return value is 1
	 * 			head++, i.e. head=1
	 * 
	 * 2.  headList = [3, 4, [5, 6, [7, 8,[9]]]] return value is 2 and head is now 2, i.e. head=2
	 * 		hear we head == fixedSize-1 i.e. head==2
	 * 		so we created we moved the 2nd inde value (here sublists) to a new list
	 * 		clear the headList and making the newList as headList
	 * 		and repeat the steps.
	 * 		
	 * 
	 */
	public Integer poll() {
		if(size <= 0) {
			return null;
		} 
		
		int oldestNum = (int) headList.get(head);
		head++;
		size--;
		
		if(head == fixedSize - 1) {
			List<Object> newList = (List<Object>) headList.get(head);
			headList.clear();
			headList = newList;
			head = 0;
		}
		return oldestNum;
	}
	
	public int size() {
		return size;
	}
}
