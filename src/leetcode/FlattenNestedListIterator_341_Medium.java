package leetcode;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class FlattenNestedListIterator_341_Medium {

	public static void main(String[] args) {

	}
}

/**
 * // This is the interface that allows for creating nested lists. // You should
 * not implement it, or speculate about its implementation public interface
 * NestedInteger {
 *
 * // @return true if this NestedInteger holds a single integer, rather than a
 * nested list. public boolean isInteger();
 *
 * // @return the single integer that this NestedInteger holds, if it holds a
 * single integer // Return null if this NestedInteger holds a nested list
 * public Integer getInteger();
 *
 * // @return the nested list that this NestedInteger holds, if it holds a
 * nested list // Return null if this NestedInteger holds a single integer
 * public List<NestedInteger> getList(); }
 */


//--https://leetcode.com/problems/flatten-nested-list-iterator/
//--https://leetcode.com/problems/flatten-nested-list-iterator/submissions/

class NestedIterator implements Iterator<Integer> {

	private Integer peek_data;
	private Deque<ListIterator<NestedInteger>> stackOfIterators;

	public NestedIterator(List<NestedInteger> nestedList) {
		stackOfIterators = new ArrayDeque<>();
		stackOfIterators.addFirst(nestedList.listIterator());
	}

	@Override
	public Integer next() {
		if (!hasNext()) {
			throw new NoSuchElementException();
		}

		Integer next = peek_data;
		peek_data = null;
		return next;
	}

	@Override
	public boolean hasNext() {
		// Try to set the peeked field. If any integers are remaining, it will
		// contain the next one to be returned after this call.
		setPeekData();

		return peek_data != null;

	}

	public void setPeekData() {

		// if peek_data is already set then return
		if (peek_data != null)
			return;

		while (!stackOfIterators.isEmpty()) {
			// return the top but doesn't remove it
			if (!stackOfIterators.peekFirst().hasNext()) {
				stackOfIterators.removeFirst();
				continue;
			}

			NestedInteger next = stackOfIterators.peekFirst().next();

			if (next.isInteger()) {
				peek_data = next.getInteger();
				return;
			}

			stackOfIterators.addFirst(next.getList().listIterator());
		}

	}

}

interface NestedInteger {
	// @return true if this NestedInteger holds a single integer, rather than a
	// nested list.
	public boolean isInteger();

	// @return the single integer that this NestedInteger holds, if it holds a
	// single integer
	// Return null if this NestedInteger holds a nested list
	public Integer getInteger();

	// @return the nested list that this NestedInteger holds, if it holds a nested
	// list
	// Return null if this NestedInteger holds a single integer
	public List<NestedInteger> getList();
}

/**
 * Your NestedIterator object will be instantiated and called as such:
 * NestedIterator i = new NestedIterator(nestedList); while (i.hasNext()) v[f()]
 * = i.next();
 */

/*
 * Complexity Analysis [Look at downloaded article for more details]
 * 
 * Let N be the total number of integers within the nested list, LL be the total
 * number of lists within the nested list, and DD be the maximum nesting depth
 * (maximum number of lists inside each other).
 * 
 * Time complexity: Constructor: O(1). 
 * 
 * Pushing a list onto a stack is by
 * reference in all the programming languages we're using here. This means that
 * instead of creating a new list, some information about how to get to the
 * existing list is put onto the stack. The list is not traversed, as it doesn't
 * need reversing this time, and we're not pushing the items on one-by-one. This
 * is, therefore, an O(1) operation.
 * 
 * makeStackTopAnInteger() / next() / hasNext(): O(L/N) or O(1). -- Look at the
 * downloaded article for details.
 * 
 * Same as Approach 2.
 * 
 * Space complexity : O(D).
 * 
 * At any given time, the stack contains only one nestedList reference for each
 * level. This is unlike the previous approach, wherein the worst case we need
 * to put almost all elements onto the stack.
 * 
 * Because there's one reference on the stack at each level, the worst case is
 * when we're looking at the deepest leveled list, giving a space complexity is
 * O(D).
 */
