package Dropbox;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

public class DesignPhoneDirectory_379_Medium {

	public static void main(String[] args) {
		
		// Init a phone directory containing a total of 3 numbers: 0, 1, and 2.
		PhoneDirectory directory = new PhoneDirectory(3);

		// It can return any available phone number. Here we assume it returns 0.
		System.out.println(directory.get());

		// Assume it returns 1.
		System.out.println(directory.get());

		// The number 2 is available, so return true.
		System.out.println(directory.check(2));

		// It returns 2, the only number that is left.
		System.out.println(directory.get());

		// The number 2 is no longer available, so return false.
		System.out.println(directory.check(2));

		// Release number 2 back to the pool.
		directory.release(2);

		// Number 2 is available again, return true.
		System.out.println(directory.check(2));


	}
	
	/**
	 * Design a Phone Directory which supports the following operations:

	get: Provide a number which is not assigned to anyone.
	check: Check if a number is available or not.
	release: Recycle or release a number.
	Example:

	// Init a phone directory containing a total of 3 numbers: 0, 1, and 2.
	PhoneDirectory directory = new PhoneDirectory(3);

	// It can return any available phone number. Here we assume it returns 0.
	directory.get();

	// Assume it returns 1.
	directory.get();

	// The number 2 is available, so return true.
	directory.check(2);

	// It returns 2, the only number that is left.
	directory.get();

	// The number 2 is no longer available, so return false.
	directory.check(2);

	// Release number 2 back to the pool.
	directory.release(2);

	// Number 2 is available again, return true.
	directory.check(2);
	 
	*/
	
	/* Refernce: https://www.programcreek.com/2014/08/leetcode-design-phone-directory-java/
	 * Another implementation : 
	 * 				https://all4win78.wordpress.com/2016/08/07/leetcode-379-design-phone-directory/
	 * 				https://massivealgorithms.blogspot.com/2016/08/leetcode-379-design-phone-directory.html
	*/
	static class PhoneDirectory {
		int max;
		Set<Integer> inUse;
		Queue<Integer> q;
		
		public PhoneDirectory(int maxNumbers) {
			this.max = maxNumbers - 1;
			inUse = new HashSet<>();
			q = new ArrayDeque<>();
			for(int i=0; i<maxNumbers; i++) {
				q.offer(i);
			}
		}
		
		public int get() {
			if(q.isEmpty()) 
				return -1;
			
			int num = q.poll();
			inUse.add(num);
			return num;
			
		}
		
		public boolean check(int num) {
			return !inUse.contains(num) 
					&& num <= max;
		}
		
		public void release(int num) {
			if(inUse.contains(num)) {
				inUse.remove(num);
				q.offer(num);
			}
		}
	}
}
