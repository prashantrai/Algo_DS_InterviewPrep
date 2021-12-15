package Uber;

public class ConstructQuadTree_427_Medium {

	public static void main(String[] args) {

		int[][] grid = {
							{1,1,1,1,0,0,0,0},
							{1,1,1,1,0,0,0,0},
							{1,1,1,1,1,1,1,1},
							{1,1,1,1,1,1,1,1},
							{1,1,1,1,0,0,0,0},
							{1,1,1,1,0,0,0,0},
							{1,1,1,1,0,0,0,0},
							{1,1,1,1,0,0,0,0}
					 	};
		
		
		Node res = construct(grid);
	}
	
	public static Node construct(int[][] grid) {
		return helper(grid, 0, 0, grid.length);
    }
	
	/* 	Time: O(n^2), we touch each node (cell of the grid/matrix once). For more on this
    				see https://csc411.files.wordpress.com/2012/10/tutorial2.pdf
         
		Space: O(n^2) 
		
		Reference: 
		https://leetcode.com/problems/construct-quad-tree/discuss/154565/Java-recursive-solution
		https://leetcode.com/problems/construct-quad-tree/discuss/151684/Recursive-Java-Solution
		https://leetcode.com/problems/construct-quad-tree/discuss/170983/Easy-to-understand-Java-Recursive-Solution
	*/
	
	private static Node helper(int[][] grid, int r, int c, int len) {
		if(len == 1) { // i.e. only one node which is leaf
			return new Node(grid[r][c] != 0, true, null, null, null, null);
		}
		
		Node result = new Node();
		
		Node topLeft     = helper(grid, r,       c,       len/2);
		Node topRight    = helper(grid, r,       c+len/2, len/2);
		Node bottomLeft  = helper(grid, r+len/2, c,       len/2);
		Node bottomRight = helper(grid, r+len/2, c+len/2, len/2);
		
		if(topLeft.isLeaf 
				&& topRight.isLeaf 
				&& bottomLeft.isLeaf 
				&& bottomRight.isLeaf
				&& topLeft.val == topRight.val 
				&& topRight.val == bottomLeft.val 
				&& bottomLeft.val == bottomRight.val) {
			
			result.val = topLeft.val;
			result.isLeaf = true;
		} else {
			result.topLeft = topLeft;
			result.topRight = topRight;
			result.bottomLeft = bottomLeft;
			result.bottomRight = bottomRight;
		}
		
		return result;
	}



	private static class Node {
	    public boolean val;
	    public boolean isLeaf;
	    public Node topLeft;
	    public Node topRight;
	    public Node bottomLeft;
	    public Node bottomRight;

	    
	    public Node() {
	        this.val = false;
	        this.isLeaf = false;
	        this.topLeft = null;
	        this.topRight = null;
	        this.bottomLeft = null;
	        this.bottomRight = null;
	    }
	    
	    public Node(boolean val, boolean isLeaf) {
	        this.val = val;
	        this.isLeaf = isLeaf;
	        this.topLeft = null;
	        this.topRight = null;
	        this.bottomLeft = null;
	        this.bottomRight = null;
	    }
	    
	    public Node(boolean val, boolean isLeaf, Node topLeft, Node topRight, Node bottomLeft, Node bottomRight) {
	        this.val = val;
	        this.isLeaf = isLeaf;
	        this.topLeft = topLeft;
	        this.topRight = topRight;
	        this.bottomLeft = bottomLeft;
	        this.bottomRight = bottomRight;
	    }
	};

}
