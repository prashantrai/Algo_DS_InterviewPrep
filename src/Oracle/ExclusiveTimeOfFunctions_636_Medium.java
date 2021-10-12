package Oracle;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class ExclusiveTimeOfFunctions_636_Medium {

	public static void main(String[] args) {

		int n = 2;
		List<String> logs = Arrays.asList("0:start:0","1:start:2","1:end:5","0:end:6");
		int[] res = exclusiveTime(n, logs);
		System.out.println("Expected: [3,4], Actual: "+ Arrays.toString(res));
	}

	/** Reference : https://leetcode.com/problems/exclusive-time-of-functions/discuss/1492801/Java-or-TC%3A-O(N)-or-SC%3A-O(N2)-or-Clean-and-Concise-solution-using-Stack
	   
	   
	   
     * Time Complexity: O(N * L) = O(N)
     *
     * Space Complexity: O(N/2) = O(N)
     *
     * N = Length of the input list of logs. L = Average length of each log. This
     * can be considered as constant.
     
     Another Approaches: 
        https://leetcode.com/problems/exclusive-time-of-functions/discuss/105062/Java-Stack-Solution-O(n)-Time-O(n)-Space
        
        https://leetcode.com/problems/exclusive-time-of-functions/discuss/153497/Java-solution-using-stack-wrapper-class-and-calculation-when-pop-element-from-the-stack.
     */
    
    public static int[] exclusiveTime(int n, List<String> logs) {
        int[] res = new int[n];
        if (n == 0 || logs == null || logs.isEmpty()) {
            return res;
        }
        Stack<Integer> stk = new Stack<>(); // to hold function_id only
        int prevTime = 0;
        
        for(String log : logs) {
            String[] logParts = log.split(":");
            int currTime = Integer.parseInt(logParts[2]);
            
            if("start".equals(logParts[1])) {
            	// Add the exclusive time of previous function
                if(!stk.isEmpty()) {
                    res[stk.peek()] += currTime - prevTime;
                }
                stk.push(Integer.parseInt(logParts[0]));
                prevTime = currTime;
            } else {
                // Function is ending now.
                // Make sure to +1 to as end takes the whole unit of time.
                res[stk.pop()] += currTime - prevTime + 1;
                
                // prevTime = resume time of the function. Thus adding 1.
                prevTime = currTime + 1;
            }
            
        }
        return res;
    }
}
