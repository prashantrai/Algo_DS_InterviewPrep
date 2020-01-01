package test.practice.misc;

import java.util.Arrays;

public class MinHeapMaxHeapDemo {

	public static void main(String[] args) {

		//int[] arr = {18, 10, 15, 9, 10, 6, 8, 5, 2, 9, 7, 3, 2, 4, 3};
		//int[] arr = {18, 10, 15, 9, 10, 6, 8};
	
		maxHeap();
		
		
		/*
		 * For min heap change is to move min element up i.e. in shiftUp() and down in shiftDown() )
		 * */
		//minHeap(); 
		
	}
	
	
	
	/***
	 * https://www.youtube.com/results?search_query=sesh+venugopal+heap
	 * Part 1 :: https://www.youtube.com/watch?v=LhhRbRXhB40
	 * Part 2 :: https://www.youtube.com/watch?v=W81Qzuz4qH0
	 * 
	 * https://www.dropbox.com/s/mpbp6kv0qgg43xp/Heap.java
	 * https://www.dropbox.com/s/0n0nop2hn28my0j/HeapApp.java
	 * 
	 * To get a child (left and right) of node at position k: 
	 * 				left child index = 2k+1 and right child index = 2k+2
	 * 
	 * To get parent of a node at position k (use the above formula to derive it):
	 *  		(k-1)/2   
	 *  
	 */
	
	
	public static void maxHeap() {
		//int[] arr = {18, 10, 15, 9, 10, 6, 8, 12};  //--new element 12 added
		int[] arr = {15, 10, 9, 8, 9, 6, 3, 4, 2, 12};  //--new element 12 added
		
		shiftUp(arr); //--add a new element in the arr/heap
		
		int[] arr2 = {12, 10, 15, 9, 10, 6, 8}; // 18 dleted and 12 has been put on top now call shiftdown to find the right place for top element
		shiftDown(arr2); //--delete an element from the arr/heap
	}


	
	private static void shiftDown(int[] arr) {
		
		int k=0;
		int l = (2*k)+1;
		
		while (l < arr.length) {
			
			int max = l, r = l+1; 
			
			if(r < arr.length) {
				if(arr[r] > arr[l]) {
					max = r;
				}
			}
			
			if(arr[k] < arr[max]) {
				
				swap(arr, k, max);
				k=max;
				l=(2*k)+1;
				
			} else 
				break;
		}
		System.out.println(Arrays.toString(arr));
	}

	private static void shiftUp(int[] arr) {  //replace arr with arraylist

		int k = arr.length-1; //new element index
		while (k > 0) {
			int ele = arr[k]; 
		
			//find the parent of the new element, if element > parent then swap
			int p = (k-1)/2;
			
			if(p < 0) break;
			
			if(arr[p] < arr[k]) {
				swap(arr, p, k);
			}
			k = p;
		}
		System.out.println(Arrays.toString(arr));
	}
	
	private static void swap(int[] arr, int i, int j) {
		int temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
	}

}
