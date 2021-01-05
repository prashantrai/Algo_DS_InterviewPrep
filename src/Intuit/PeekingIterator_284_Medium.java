package Intuit;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;


public class PeekingIterator_284_Medium {

	public static void main(String[] args) {

		List<Integer> list = Arrays.asList(1, 2, 3, 4);
		Iterator itr = list.iterator();

		PeekingIterator myItr = new PeekingIterator(itr);

		for (int i : list) { 
			System.out.println("hasNext: " + myItr.hasNext());
			System.out.println("Peek: " + myItr.peek()); 
			System.out.println("Next: " + myItr.next()); 
		}

		System.out.println(">>hasNext: " + myItr.hasNext());
		System.out.println(">>Peek: " + myItr.peek());
		System.out.println(">>Next: " + myItr.next());

		System.out.println(">>hasNext: " + myItr.hasNext());
	}
}

//Java Iterator interface reference:
//https://docs.oracle.com/javase/8/docs/api/java/util/Iterator.html

/*
 * Complexity Analysis
 * 
 * Time Complexity : All methods: O(1).
 * 
 * The operation performed to update our peeked value are all O(1).
 * 
 * The actual operations from .next() are impossible for us to analyse, as they
 * depend on the given Iterator. By design, they are none of our concern. Our
 * addition to the time is only O(1) though.
 * 
 * Space Complexity : All methods: O(1).
 * 
 * Like with time complexity, the Iterator itself is probably using memory in
 * its own implementation. But again, this is not our concern. Our
 * implementation only uses a few variables, so is O(1).
 * 
 * 
 */

class PeekingIterator implements Iterator<Integer> {

	Iterator<Integer> iter;
	Integer data = null;

	public PeekingIterator(Iterator<Integer> iterator) {
		// initialize any member here.
		this.iter = iterator;
		moveIterator();
	}

	// Returns the next element in the iteration without advancing the iterator.
	public Integer peek() {
		return data;
	}

	// hasNext() and next() should behave the same as in the Iterator interface.
	// Override them if needed.
	@Override
	public Integer next() {
		if (data == null) {
			throw new NoSuchElementException();
		}
		Integer res = data;
		moveIterator();
		return res;
	}

	@Override
	public boolean hasNext() {
		return data != null;
	}

	private void moveIterator() {
		if (iter.hasNext()) {
			data = iter.next();
		} else {
			data = null;
		}
	}
}

/*
 * https://leetcode.com/problems/peeking-iterator/
 * 
 * Question: 284. Peeking Iterator-Medium
 * 
 * Share Given an Iterator class interface with methods: next() and hasNext(),
 * design and implement a PeekingIterator that support the peek() operation --
 * it essentially peek() at the element that will be returned by the next call
 * to next().
 * 
 * Example:
 * 
 * Assume that the iterator is initialized to the beginning of the list:
 * [1,2,3].
 * 
 * Call next() gets you 1, the first element in the list. Now you call peek()
 * and it returns 2, the next element. Calling next() after that still return 2.
 * You call next() the final time and it returns 3, the last element. Calling
 * hasNext() after that should return false. Follow up: How would you extend
 * your design to be generic and work with all types, not just integer?
 * 
 */
