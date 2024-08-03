package Square;
import java.util.*;

public class CompareIntervals {
	
	public static void main(String[] args) {
		CompareIntervals solution = new CompareIntervals();
        
        // Example usage
        List<double[]> playerA = Arrays.asList(new double[][] {
            {50, 60}, {100.33, 40}, {200, 80}
        });
        List<double[]> playerB = Arrays.asList(new double[][] {
            {1, 75}, {50.5, 70}
        });
        double gameWindow = 300.0;
        
        double percentage = solution.calculatePercentage(playerA, playerB, gameWindow);
        System.out.printf("Percentage of time A > B: %.2f %%\n", percentage);
    }

	public double calculatePercentage(List<double[]> playerA, List<double[]> playerB, double gameWindow) {
        // Step 1: Merge intervals
        List<double[]> mergedIntervals = mergeIntervals(playerA, playerB);
        
        // Step 2: Calculate total overlap duration where A > B
        double totalOverlapDuration = calculateOverlapDuration(mergedIntervals, gameWindow);
        
        // Step 3: Calculate percentage
        double percentage = (totalOverlapDuration / gameWindow) * 100.0;
        
        return percentage;
    }
    
    private List<double[]> mergeIntervals(List<double[]> playerA, List<double[]> playerB) {
        List<double[]> merged = new ArrayList<>();
        merged.addAll(playerA);
        merged.addAll(playerB);
        
        Collections.sort(merged, (a, b) -> Double.compare(a[0], b[0]));
        
        List<double[]> result = new ArrayList<>();
        double[] current = merged.get(0);
        
        for (int i = 1; i < merged.size(); i++) {
            double[] interval = merged.get(i);
            if (interval[0] <= current[1]) {
                current[1] = Math.max(current[1], interval[1]);
            } else {
                result.add(current);
                current = interval;
            }
        }
        
        result.add(current);
        
        return result;
    }
    
    private double calculateOverlapDuration(List<double[]> intervals, double gameWindow) {
        double overlapDuration = 0.0;
        
        for (int i = 1; i < intervals.size(); i++) {
            double[] prev = intervals.get(i - 1);
            double[] curr = intervals.get(i);
            
            if (prev[1] > curr[0]) {
                double start = Math.max(prev[0], curr[0]);
                double end = Math.min(prev[1], curr[1]);
                
                if (start < end) {
                    overlapDuration += end - start;
                }
            }
        }
        
        // Add the remaining time if the last interval ends before gameWindow
        double[] lastInterval = intervals.get(intervals.size() - 1);
        if (lastInterval[1] < gameWindow) {
            overlapDuration += gameWindow - lastInterval[1];
        }
        
        return overlapDuration;
    }
    
}


/*
https://leetcode.com/company/square/discuss/3228427/Square-Block-or-Phone-or-Compare-Intervals

You are given a list of [time, power] for player A and player B where 
the first element is the timestamp and second element is the power output 
the player reaches at that timestamp. You are also given a window for how 
long the game will last. Calculate the percentage of time for which player 
A has greater power than player B.

Example:
total game window: 300.0 seconds

player A [ [50, 60], [100.33, 40], [200, 80] ]
player B [ [1, 75] , [50.5, 70] ]

percentage of time A > B ~= 33 %

Explanation: 
From 200 -> 300 is when player A has higher power output that player B. 100/300 ~= 33%

*/
