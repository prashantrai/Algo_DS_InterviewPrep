package LinkedIn;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class NestedListWeightSum_II_364_medium {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	// Time & Space : O(N)

    /* This solution uses BFS + cumulative levelSum trick is mathematically equivalent to val × (maxDepth - depth + 1) 
    */

    public int depthSumInverse(List<NestedInteger> nestedList) {

        if (nestedList == null) return 0;

        Queue<NestedInteger> q = new LinkedList<>(nestedList);
        int totalSum = 0;
        int levelSum = 0;

        while(!q.isEmpty()) {
            int size = q.size();

            for(int i=0; i<size; i++) {
                NestedInteger curr = q.poll();
                if(curr.isInteger()) {
                    // Add integer to levelSum
                    levelSum += curr.getInteger(); 
                } else {
                    q.addAll(curr.getList());
                }
            }

            // Add levelSum cumulatively (bottom-up accumulation)
            totalSum += levelSum;

        }

        return totalSum;

    }


    // LC Premium
    public int depthSumInverse2(List<NestedInteger> nestedList) {
        Queue<NestedInteger> q = new LinkedList<>(nestedList);
        
        int depth = 1;
        int maxDepth = 0;
        int sumOfElements = 0;
        int sumOfProducts = 0;

        while(!q.isEmpty()) {
            int size = q.size();
            maxDepth = Math.max(maxDepth, depth);

            for(int i=0; i<size; i++) {
                NestedInteger nested = q.poll();
                if(nested.isInteger()) {
                    sumOfElements += nested.getInteger();
                    sumOfProducts += nested.getInteger() * depth;

                } else {
                    q.addAll(nested.getList());
                }
            }
            depth++;
        }

        return (maxDepth + 1) * sumOfElements - sumOfProducts;
    }

}
