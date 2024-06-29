package Facebook;

import java.util.Random;

public class RandomPickIndex_398_Medium {

	public static void main(String[] args) {
		
		int[] nums = {1, 2, 3, 3, 3};
		int target = 3;
		
		RandomPickIndex randomPickIndex = new RandomPickIndex(nums);
		int index = randomPickIndex.pick(target);
		System.out.println("Expected: should return either index 2, 3, or 4 randomly. \nActual: " + index);
	}
	
	
	static class RandomPickIndex {

	    /*
	    The code uses a technique called "Reservoir Sampling" to ensure 
	    that each index of the target value is picked with equal probability.
	    
	    Reservoir Sampling Explanation: 
	    When we encounter a target value, we want to give it a fair chance of being picked, 
	    just as any previous occurrences of the target. Here's a step-by-step explanation:

	    First Occurrence:
	    When we first encounter the target, there's no previous occurrence to compete with. 
	    Therefore, we pick this index with probability 1 (since count is 1, randNum can only be 0).
	    
	    Subsequent Occurrences:
	    As we encounter more target values, we need to decide whether to replace our 
	    current pick with the new occurrence or keep the current pick.
	    
	    Why randNum == 0?
	        At each step, we generate a random number randNum between 0 and count-1. 
	        By checking if randNum == 0, we ensure that each target index gets a fair chance:

	        When the target is encountered for the i-th time (let's say this is the 
	        i-th occurrence of the target), we increment count to i+1 and generate 
	        randNum in the range [0, i].

	        The probability that randNum is 0 is 1/(i+1).
	        
	        By updating the index only when randNum == 0, we ensure that each occurrence 
	        has an equal chance of being the final picked index. This probability diminishes 
	        equally as new targets are encountered, balancing the selection process across 
	        all target indices.
	    
	    
	    
	    Time Complexity: O(n) per call to pick, where n is the length of the nums array.
	                    
	                     The time complexity of generating a random number in Java is 
	                     O(1) because it involves a straightforward computation using 
	                     the underlying random number generator algorithm.
	                    
	    Space Complexity: O(1) (excluding the input array).
	    
	    
	    */
	    
	    
	    int[] nums;
	    Random random;
	    public RandomPickIndex(int[] nums) {
	        this.nums = nums;
	        random = new Random();
	    }
	    
	    public int pick(int target) {
	        // keep track of the number of times the target has been hit so far.
	        // used as bound in Random and for starting 
	        // it must be 1 to have bound as 0 to 1 in the Random
	        int count = 1; 
	        int index = -1; // start with and invalid index
	        
	        for(int i=0; i<nums.length; i++) {
	            if(nums[i] != target) 
	                continue;
	            
	            // generates a random integer in the range [0, count)
	            // Initially, count is 1, so can only generate 0.
	            int randNum = random.nextInt(count); 
	            
	            // By always checking randNum == 0, we ensure that every 
	            // occurrence of the target has an equal probability of 
	            // being chosen, which is the hallmark of fair and unbiased sampling.
	            if(randNum == 0)
	                index = i;
	            
	            count++;
	            
	        }
	        return index;
	    }
	}

}
