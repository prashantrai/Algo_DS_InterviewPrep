package leetcode;

import java.util.HashMap;
import java.util.Map;

public class PairsOfSongsWithTotalDurationsDivisibleBy60_1010_Medium {

	public static void main(String[] args) {

		int[] time = {30,20,150,100,40};
		
		System.out.println("Expected: 3, Actual: "+ numPairsDivisibleBy60(time));
	}
	
	// Asked in Rivian Phone Screen: https://leetcode.com/discuss/interview-question/1335487/rivian-senior-swe-phone-screen
	// Time: O(n)
    // Space: O(1), because size of map will never go beyond 60
	public static int numPairsDivisibleBy60(int[] time) {
        Map<Integer, Integer> count = new HashMap<>();
        int ans = 0;
        for (int t : time) {
            
            int reducedTime = t % 60;
            int other  = reducedTime == 0 ? 0 : 60 - reducedTime;
            ans += count.getOrDefault(other, 0);
            count.put(reducedTime, count.getOrDefault(reducedTime, 0)+1);
            
        }
        
        return ans;
    }

}
