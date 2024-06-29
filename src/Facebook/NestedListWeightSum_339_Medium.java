package Facebook;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class NestedListWeightSum_339_Medium {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	// DFS
    public static int depthSum(List<NestedInteger> nestedList) {
        return bfs(nestedList);
        //return dfs(nestedList, 1);
    }
    
    /*
     * NOTE: NestedInteger class implementation is added in this file at the bottom.
     * 
    Time: O(N)
    Space: O(N)
    * 
    */
    private static int bfs(List<NestedInteger> nestedList) {
        Queue<NestedInteger> q = new ArrayDeque<>();
        q.addAll(nestedList);
        
        int depth = 1;
        int total = 0;
        
        while(!q.isEmpty()) {
            int size = q.size();
            for(int i=0; i<size; i++) {
                NestedInteger nested = q.poll();
                if(nested.isInteger()) {
                    total += nested.getInteger() * depth;
                } else { 
                    // we have list, add all element to q
                    q.addAll(nested.getList());
                }
            }
            depth++;
        }
        return total;
    }
    
    
    /*
    Time: O(N), The recursive function, dfs(...) is called exactly once for each 
    		nested list. As N also includes nested integers, we know that the number of 
    		recursive calls has to be less than N. 

		    On each nested list, it iterates over all of the nested elements directly inside 
		    that list (in other words, not nested further). As each nested element can only be 
		    directly inside one list, we know that there must only be one loop iteration for
		    each nested element. This is a total of N loop iterations.
		
		    So combined, we are performing at most 2*N recursive calls and loop iterations. 
		    We drop the 2 as it is a constant, leaving us with time complexity O(N).
    
    
    Space: O(D), at most O(D) recursive calls are palced on the stack, where
    		D is the maximum level of nesting in the input.
    */
    private static int dfs(List<NestedInteger> nestedList, int depth) {
        int total= 0;
        for(NestedInteger nestedInt : nestedList) {
            if(nestedInt.isInteger()) {
                total += nestedInt.getInteger() * depth;
            } else {
                total += dfs(nestedInt.getList(), depth+1);
            }
        }
        return total;
    }
    

}

// Sample implementation of the NestedInteger interface
class NestedInteger {
    private Integer singleInteger;
    private List<NestedInteger> nestedList;
    
    // Constructor initializes an empty nested list.
    public NestedInteger() {
        this.nestedList = new ArrayList<>();
    }
    
    // Constructor initializes a single integer.
    public NestedInteger(int value) {
        this.singleInteger = value;
        this.nestedList = null;
    }
    
    // @return true if this NestedInteger holds a single integer, rather than a nested list.
    public boolean isInteger() {
        return singleInteger != null;
    }
    
    // @return the single integer that this NestedInteger holds, if it holds a single integer
    // Return null if this NestedInteger holds a nested list
    public Integer getInteger() {
        return singleInteger;
    }
    
    // Set this NestedInteger to hold a single integer.
    public void setInteger(int value) {
        this.singleInteger = value;
        this.nestedList = null;
    }
    
    // Set this NestedInteger to hold a nested list and adds a nested integer to it.
    public void add(NestedInteger ni) {
        if (this.nestedList == null) {
            this.nestedList = new ArrayList<>();
        }
        this.nestedList.add(ni);
    }
    
    // @return the nested list that this NestedInteger holds, if it holds a nested list
    // Return empty list if this NestedInteger holds a single integer
    public List<NestedInteger> getList() {
        if (this.nestedList == null) {
            return new ArrayList<>();
        }
        return this.nestedList;
    }
}

