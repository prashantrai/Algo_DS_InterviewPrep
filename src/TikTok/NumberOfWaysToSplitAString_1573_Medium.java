package TikTok;

public class NumberOfWaysToSplitAString_1573_Medium {

	public static void main(String[] args) {
		String s = "10101";
		System.out.println("Expected: 4, Actual: " + numWays(s));
		
		s = "1001";
		System.out.println("Expected: 0, Actual: " + numWays(s));
		
		s = "0000";
		System.out.println("Expected: 3, Actual: " + numWays(s));
	}
	
	/*
    Refer: 
    https://www.youtube.com/watch?v=ptBH9EdxjrY&ab_channel=NateSantti
    
    For more explaination: 
    https://leetcode.com/problems/number-of-ways-to-split-a-string/discuss/830455/JavaPython-3-Multiplication-of-the-ways-of-1st-and-2nd-cuts-w-explanation-and-analysis.
    
    Time: O(N)
    Space: O(1)
    */
    
    
    //final int MOD = (int) Math.pow(10, 9) + 7;
    final static int MOD = 1_000_000_007;
    
    public static int numWays(String s) {
        
        //count no of ones on the input string
        int numOnes = (int) s.chars().filter(c -> c =='1').count();
        
        
        // We need to split in 3 non-empty string ant each should have at least one '1'
        // i.e. if numOnes < 3 then solution is not possible 
        if(numOnes % 3 != 0) return 0;
        
        /*  If the count is 0, then we can choose 1st 0 as our 1st cut, 
            correspondingly, the 2nd cut between the other 2 non-empty subarrays 
            will have n - 2 options, where n = s.length(); 
            Similarly, we can also choose 2nd 0 as 1st cut, then we 
            have n - 3 options for the 2nd cut, ..., etc, totally, 
            the result is (n - 2) + (n - 3) + ... + 2 + 1 = (n - 2) * (n - 1) / 2;
            
        **/
        if(numOnes == 0) {
            long len = s.length();
            return (int) ((len-1)*(len-2) / 2 % MOD);
        }
        
        int onesPerGroup = numOnes/3; // i.e. how many 1s in each split
        int counter = 0;
        long firstBlock = 0;  // no of 1s in first split
        long secondBlock = 0; // no of 1s in second split
        
        for(char c : s.toCharArray()) {
            if(c == '1') {
                counter++;
            }
            if(counter == onesPerGroup) {
                firstBlock++;
                
            } else if (counter == 2 * onesPerGroup) {
                secondBlock++;
            }
        }
        
        return (int) (firstBlock * secondBlock % MOD);
        
    }
}

