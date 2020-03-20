package leetcode;

public class WaterAndJugProblem_365_Medium {

	public static void main(String[] args) {

		System.out.println(canMeasureWater(3, 5, 4));
		
	}
	
	/**
	 * https://leetcode.com/problems/water-and-jug-problem/
	 * https://leetcode.com/problems/water-and-jug-problem/discuss/523146/Java-use-GCD 
	 */
	public static boolean canMeasureWater(int x, int y, int z) {
        
        if(x+y < z) return false;
        if(x == z || y == z || x+y == z) return true;
        
        return z % gcd(x,y) == 0;        
    }
    
    public static int gcd(int x, int y) {
        
        while(y != 0) {
            int temp = y;
            y = x % y;
            x = temp;
        }
        return x;
    }

}
