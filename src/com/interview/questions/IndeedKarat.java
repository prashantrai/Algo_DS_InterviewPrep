package com.interview.questions;


import java.io.*;
import java.util.*;

/*

Suppose we have some input data describing a graph of relationships between parents and children over multiple generations. The data is formatted as a list of (parent, child) pairs, where each individual is assigned a unique integer identifier.

For example, in this diagram, 3 is a child of 1 and 2, and 5 is a child of 4:
            
1   2   4     22
 \ /   / \     \
  3   5   8    24
   \ / \   \
    6   7   9
    

Write a function that, for two given individuals in our dataset, returns true if and only if they share at least one ancestor.

    f(input, node1, node2) => boolean
        true/false ... do the nodes have an ancestor in common

Sample input and output:

parentChildPairs, 3, 8 => false
parentChildPairs, 5, 8 => true
parentChildPairs, 6, 8 => true
parentChildPairs, 7, 8 => true

get depth of both modes
what is delta between them
go up from deepest node the delta (which way?)

1.  what data structure
    Node  {
      int data;   // "6"
      List<Node> children;
      
      Node parentLeft;  -> 3  
      Node parentRight; -> 5
    
    }


2.  how to count depth


3.  how to move up

*/

public class IndeedKarat {

	public static void main(String[] args) {
		int[][] parentChildPairs = new int[][] {
	        {1, 3}, {2, 3}, {3, 6}, {5, 6}, {5, 7},
	        {4, 5}, {4, 8}, {8, 9}
	    };
	    
	    
	    List<List<Integer>> res = helper(parentChildPairs);
	    
	    System.out.println("Res:: "+res);

	}
	
	// Q1 complexity:
	  //
	  //  time:  O(N)
	  //  space: O(N)
	  //
	public static List<List<Integer>> helper(int[][] a) {
		  
	    Map<Integer, Integer> map = new HashMap<>();
	    
	    for(int i=0; i<a.length; i++) {
	    
	      int[] temp = a[i];
	      int parent = temp[0];
	      int child  = temp[1];
	    
	      // { [3:3], [1:0], [2:0], [6:1], [] }
	      
	      if(map.containsKey(child)) {
	        int count = map.get(child);
	        map.put(child, count+1);
	      } else {
	        map.put(child, 1);        
	        
	      }
	      
	      /*if(map.containsKey(parent)) {
	        int count = map.get(parent);
	        map.put(parent, count+1);
	      }*/ //else {
	      
	      if(!map.containsKey(parent)) {
	        map.put(parent, 0);        
	        
	      }
	      
	    }
	    
	    System.out.println(map);
	    
	    Set<Integer> set = map.keySet();

	    List<Integer> zeroParen = new ArrayList<>();
	    List<Integer> oneParen = new ArrayList<>();
	    
	    List<List<Integer>> res = new ArrayList<>();
	    res.add(zeroParen);
	    res.add(oneParen);
	    
	    
	    for(int key : set) {
	      //int key = map.get(i);
	      
	      
	      //if(map.get(key) != null) continue;
	      
	      if(map.get(key) == 0) {
	        zeroParen.add(key);
	      
	      } else if( map.get(key) == 1 ) {
	        oneParen.add(key);
	        
	      }
	      
	      
	    }
	    
	 
	    

	    return res;

	    
	  
	  }
	  

}
