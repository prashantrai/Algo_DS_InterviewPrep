package test.practice.misc;

public class TrappingRainWater {

	public static void main(String[] args) {
		
		int[] a = {3,0,0,2,0,4};
		
		trappedWater(a);
	}
	
	public static void trappedWater(int[] a) {
		
		int totalWater = 0;
		int n = a.length;
		
		int left=0, right=0;
		int leftMax = 0;
		int rightMax = 0;
		for (int i=1; i<n; i++) {
			
			leftMax = Math.max(a[i-1], a[i]);
			rightMax = Math.max(a[n-1], a[i]);
			//incomplete
			
		}
		
	}

}
