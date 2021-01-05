package leetcode;

import java.util.HashSet;
import java.util.Set;

public class JewelsAndStones_771_Easy {

	public static void main(String[] args) {

		String jewels = "aA", stones = "aAAbbbb";
		
		// Time: O(N), Space: O(1) because or fixed size boolean array
		System.out.println("Expected: 3, Actual: " + numJewelsInStones(jewels, stones));

		// Time: O(N), Space: O(1) because or fixed size int array
		System.out.println("Expected: 3, Actual: " + numJewelsInStones(jewels, stones));
		
		// Time: O(N), Space: O(N) because of SET
		System.out.println("Expected: 3, Actual: " + numJewelsInStones(jewels, stones));
	}
    
	//https://leetcode.com/problems/jewels-and-stones/
    
	// Time: O(N), Space: O(1) because or fixed size boolean array
    public static int numJewelsInStones(String jewels, String stones) {
        boolean[] jewelFlag = new boolean[58];  // ASCII of 'z' - 'A' is 58.
        int result = 0;
        for(char j : jewels.toCharArray()) {
            jewelFlag[j-'A'] = true;  // 'A' has the smallest ASCII in 'A' - 'z'
        }
        for(char s : stones.toCharArray()) {
            if(jewelFlag[s-'A']) {
                result++;
            }
        }
        return result;
    }
    
    // Time: O(N), Space: O(1) because or fixed size int array
    public static int numJewelsInStones_2(String jewels, String stones) {
        int[] jewelFlag = new int[58]; // ASCII of 'z' - 'A' is 58.
        int result = 0;
        for(char j : jewels.toCharArray()) {
            jewelFlag[j-'A'] = 1;  // 'A' has the smallest ASCII in 'A' - 'z'
        }
        
        for(char s : stones.toCharArray()) {
            if(jewelFlag[s-'A'] == 1) {
                result++;
            }
        }
        return result;
    }
    
    // Time: O(N), Space: O(N) because of SET
    public static int numJewelsInStones_3(String jewels, String stones) {
        Set<Character> jewel = new HashSet<>();
        int result = 0;
        for(char j : jewels.toCharArray()) {
            jewel.add(j);
        }
        for(char s : stones.toCharArray()) {
            if(jewel.contains(s)) {
                result++;
            }
        }
        return result;
    }

}
