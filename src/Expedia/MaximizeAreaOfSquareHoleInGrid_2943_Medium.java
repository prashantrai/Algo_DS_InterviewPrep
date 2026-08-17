package Expedia;

import java.util.Arrays;

public class MaximizeAreaOfSquareHoleInGrid_2943_Medium {

	public static void main(String[] args) {
        int n = 2, m = 1;
        int[] hBars = {2, 3};
        int[] vBars = {2};

        System.out.println("Expected: 4, Actual: " + maximizeSquareHoleArea(n, m, hBars, vBars)); // 4
    }
	
	/* Interview Script: 
	 	My approach is to reduce the problem to finding the largest consecutive removable 
	 	bars in each direction.
		
		If I can remove k consecutive horizontal bars, I create a vertical opening of size k + 1. 
		The same applies to vertical bars.
		
		So I sort both arrays, scan them to find the longest consecutive run, and convert that 
		run length into a usable side length by adding 1.
		
		Since the hole must be a square, the final side is limited by the smaller of the two dimensions.
		
		Therefore, the answer is (min(longestHorizontalRun, longestVerticalRun) + 1)^2
	 * */
	
	/* 
    Time complexity: O(hlogh+vlogv).
        Sorting hBars and vBars requires O(hlogh) and O(vlogv) respectively.
    
    Space complexity: O(1) extra
        (or O(log h + log v) if including sort recursion stack)
    */
    public static int maximizeSquareHoleArea(int n, int m, int[] hBars, int[] vBars) {
        int maxHeight = getLargestSegment(hBars);
        int maxWidth = getLargestSegment(vBars);

        // Why + 1? 
        // If you remove k consecutive bars, you connect k + 1 adjacent 1-unit gaps.
        int side = Math.min(maxHeight, maxWidth) + 1;
        return side * side;
    }

    private static int getLargestSegment(int[] bars) {
        if(bars == null || bars.length == 0) return 0;

        Arrays.sort(bars);

        int currentRun = 1;
        int longestRun = 1;

        for(int i=1; i<bars.length; i++) {
            if(bars[i] == bars[i-1]+1) {
                currentRun++;
            } else {
                currentRun = 1;
            }
            longestRun = Math.max(longestRun, currentRun);
        }
        return longestRun;
    } 

}
