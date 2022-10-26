package Bloomberg;

public class NumberOfShipsInARectangle_1274_Hard {

	public static void main(String[] args) {

	}

	/**
	 Complexity Analysis: 

		Let M be the range of possible x-coordinate values between bottomLeft[0] and topRight[0] 
		and let N be the range of possible y-coordinate values between bottomLeft[1] and topRight[1]. 
		Thus, the maximum possible number of points in the rectangle is M*N 
		
		Finally, let S be the maximum number of ships in the sea.
		
		
		
		Time Complexity: O(S*(log2 max(M,N)−log4 S))  N.B. Here log2 means log of base 2 and log4 means log of base 4
		
		Each call to countShips requires only constant time so the time complexity will be O(1) times 
		the maximum possible number of calls to countShips.
		
		The worst-case scenario is when there is the maximum number of possible 
		ships (S = 10) and they are spread out such that after S recursive calls (log4 S level of the recursion tree), 
		there are S regions that each contain 1 ship and the remaining regions are empty.
		
		Each region that contains 1 ship, will result in 4 recursive calls. 
		3 will return 0 because they do not contain a ship and 1 call will 
		result in 4 more recursive calls because it does contain a ship. This process will 
		repeat until we make a recursive call with the exact coordinates of the ship.
		
		At the latest, we will pinpoint the ship at the maximum depth of the recursion tree which is log 2 max(M,N) 
		because at each recursive call we divide the search space by half for each of the 2 dimensions.
		
		Thus, once a region contains only 1 ship, it may still take 4*(log 2 max(M,N)−log4 S) recursive calls 
		before pinpointing the location of the ship (and returning 1). And since there are S ships, the 
		total number of recursive calls after all regions contain at most 1 ship is 4*S*(log2 max(M,N)−log4 S).
		
		Summing up, the time complexity is S+4*S*(log2 max(M,N)−log4 S) which in the worst case 
		(when S = 10 and M = N = 1000) equals 342 recursive calls.
		
		
		
		Space Complexity: O(log2 max(M,N)).
			Each call to countShips uses only constant space so our space complexity is directly 
			related to the maximum height of the recursion call stack. Since we have 2 dimensions 
			of magnitudes M and N respectively, and the search space for each dimension is reduced 
			by half at each recursive call to countShips, the maximum height of the recursion 
			call stack will be log2 max(M,N).
	 */
	
	
	/*
	References: 
	https://leetcode.com/problems/number-of-ships-in-a-rectangle/discuss/1649557/Java-oror-Explained-oror-Divide-and-Conquer-oror-Explained-the-Time-complexity

	Explanation: [Good] https://leetcode.com/problems/number-of-ships-in-a-rectangle/discuss/1024086/Divide-and-Conquer-or-Visual-%2B-Explanation-or-Python
	*/
	
	public int countShips(Sea sea, int[] topRight, int[] bottomLeft) {

		// If the current rectangle does not contain any ships, return 0.
		if (bottomLeft[0] > topRight[0] && bottomLeft[1] > topRight[1])
			return 0;

		if (!sea.hasShips(topRight, bottomLeft))
			return 0;

		// If the rectangle represents a single point, a ship is located
		if (bottomLeft[0] == topRight[0] && bottomLeft[1] == topRight[1])
			return 1;

		// Recursively check each of the 4 sub-rectangles for ships
		int midX = (bottomLeft[0] + topRight[0]) / 2;
		int midY = (bottomLeft[1] + topRight[1]) / 2;

		return countShips(sea, new int[] { midX, midY }, bottomLeft) + // bottom left
				countShips(sea, new int[] { topRight[0], midY }, new int[] { midX + 1, bottomLeft[1] }) + // bottom
																											// right
				countShips(sea, topRight, new int[] { midX + 1, midY + 1 }) + // top right
				countShips(sea, new int[] { midX, topRight[1] }, new int[] { bottomLeft[0], midY + 1 }); // top left
	}

	// NOTE: Below implementation is just to avoid compilation error in editor
	// this is an interactive problem and we don't need to worry about the
	// implementation below class
	
	// This is Sea's API interface.
	// You should not implement it, or speculate about its implementation

	class Sea {
		public boolean hasShips(int[] topRight, int[] bottomLeft) {
			return true;
		}
	}

}
