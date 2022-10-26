package Bloomberg;

import java.util.HashSet;
import java.util.Set;

public class HappNumber_202_Easy {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	
	/*
	 Time complexity : O(log n). Builds on the analysis for the previous approach, 
	 	except this time we need to analyze how much extra work is done by keeping 
	 	track of two places instead of one, and how many times they'll need to go around 
	 	the cycle before meeting.

		 If there is no cycle, then the fast runner will get to 1, and the slow runner 
		 will get halfway to 1. Because there were 2 runners instead of 1, we know that 
		 at worst, the cost was O(2⋅log n) = O(log n).
		
		 Like above, we're treating the length of the chain to the cycle as insignificant 
		 compared to the cost of calculating the next value for the first n. Therefore, 
		 the only thing we need to do is show that the number of times the runners go back 
		 over previously seen numbers in the chain is constant.
		
		 Once both pointers are in the cycle (which will take constant time to happen) 
		 the fast runner will get one step closer to the slow runner at each cycle. 
		 Once the fast runner is one step behind the slow runner, they'll meet on the 
		 next step. Imagine there are k numbers in the cycle. If they started at 
		 k - 1 places apart (which is the farthest apart they can start), then it 
		 will take k - 1 steps for the fast runner to reach the slow runner, 
		 which again is constant for our purposes. 
		 
		 Therefore, the dominating operation is still calculating the next value for 
		 the starting n, which is O(log n).
	
	 Space complexity : O(1). For this approach, we don't need a HashSet to detect the cycles. 
	 	The pointers require constant extra space.
	 */
	
	// Using Floyd's Cycle-Finding Algorithm
     public int getNext(int n) {
        int totalSum = 0;
        while (n > 0) {
            int d = n % 10;
            n = n / 10;
            totalSum += d * d;
        }
        return totalSum;
    }

    public boolean isHappy(int n) {
        int slowRunner = n;
        int fastRunner = getNext(n);
        while (fastRunner != 1 && slowRunner != fastRunner) {
            slowRunner = getNext(slowRunner);
            fastRunner = getNext(getNext(fastRunner));
        }
        return fastRunner == 1;
    }
	

	/*
	 Time complexity : O(243⋅3 + log n+ log log n+ log log log n)... = O(log n).

	Finding the next value for a given number has a cost of O(log n) because we 
	are processing each digit in the number, and the number of digits in a 
	number is given by log n.
	
	To work out the total time complexity, we'll need to think carefully about 
	how many numbers are in the chain, and how big they are.
	
	We determined above that once a number is below 243, it is impossible 
	for it to go back up above 243. Therefore, based on our very shallow 
	analysis we know for sure that once a number is below 243, it is 
	impossible for it to take more than another 243 steps to terminate. 
	Each of these numbers has at most 3 digits. With a little more analysis, 
	we could replace the 243 with the length of the longest number chain 
	below 243, however because the constant doesn't matter anyway, we won't worry about it.
	
	For an n above 243, we need to consider the cost of each number in the chain 
	that is above 243. With a little math, we can show that in the worst case, 
	these costs will be O(log n)+O(log log n)+O(log log log n).... Luckily for us, 
	the O(log n) is the dominating part, and the others are all tiny in comparison 
	(collectively, they add up to less than log n), so we can ignore them.
	
	Space complexity : O(log n). Closely related to the time complexity, 
	and is a measure of what numbers we're putting in the HashSet, and 
	how big they are. For a large enough n, the most space will be taken by n itself.
	
	We can optimize to O(243⋅3) = O(1) easily by only saving numbers in the set 
	that are less than 243, as we have already shown that for numbers that 
	are higher, it's impossible to get back to them anyway.
	 */
	
	// Using HashSet
	public boolean isHappy2(int n) {
		Set<Integer> seen = new HashSet<>();
		while (n != 1 && !seen.contains(n)) {
			seen.add(n);
			n = getNext2(n);
		}
		return n == 1;
	}
	
	private int getNext2(int n) {
		int totalSum = 0;
		while (n > 0) {
			int d = n % 10;
			n = n / 10;
			totalSum += d * d;
		}
		return totalSum;
	}

}
