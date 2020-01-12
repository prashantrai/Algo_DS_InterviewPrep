package Airbnb;

import java.util.Arrays;

public class PourWater {

	public static void main(String[] args) {
		//int[] waterLand = new int[]{5, 4, 2, 1, 2, 3, 2, 1, 0, 1, 2, 4};
		int[] heights = new int[]{2, 1, 1, 2, 1, 2, 2};
		
		pourWater(heights, 4, 3);
		System.out.println(Arrays.toString(heights));
		
		int[] heights2 = new int[]{2, 1, 1, 2, 1, 2, 2};
		pourWater2(heights2, 4, 3);
		System.out.println(Arrays.toString(heights2));
		
	}
	//--https://aaronice.gitbooks.io/lintcode/array/pour-water.html
	public static void pourWater(int[] heights, int v, int k) {
		if(heights == null | heights.length == 0) return;
		
		while (v-- > 0) {
			int index = k;
			
			//--move left
			for(int i=k-1; i >= 0; i--) {
				if(heights[i] < heights[index]) {
					index = i;
				} else if (heights[i] > heights[index]){
					break;
				}
			}
			
			if(index >=0 && index < k) {
				heights[index]++;
				continue;
			}
			
			//--move right
			for(int i=k+1; i < heights.length; i++) {
				if(heights[i] < heights[i-1]) {
					index = i;
				} else if(heights[i] > heights[i-1]) {
					break;
				}
			}
			
			if(index < heights.length && index > k) {
				heights[index]++;
			}
			
			if(index == k) {
				heights[index]++;
			}
		}
	}
	
	public static void pourWater2(int[] heights, int v, int k) {
		if(heights == null | heights.length == 0) return;
	
		for(int i=0; i<v; i++) {
			int curr = k;
			
			//--move left
			while(curr > 0 && heights[curr] >= heights[curr-1]) {
				curr--;
			}
			
			//--move right
			while(curr < heights.length-1 && heights[curr] >= heights[curr+1]) {
				curr++;
			}
			
			//--Move left to K
			while (curr > k && heights[curr] >= heights[curr-1]) {
				curr--;
			}
			heights[curr]++;
		}
		
	}

}
