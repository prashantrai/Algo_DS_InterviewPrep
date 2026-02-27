package Geico;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CinemaSeatAllocation_1386_Medium {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	// Time: O(N)
    // Space: O(N)
    public static int maxNumberOfFamilies(int n, int[][] reservedSeats) {

        // group reservedSeats by row, use Map<row, set of seat#>
        Map<Integer, Set<Integer>> rowMap = new HashMap<>();
        for(int[] seat : reservedSeats) {
            rowMap.putIfAbsent(seat[0], new HashSet<Integer>());
            rowMap.get(seat[0]).add(seat[1]);
        }
        
        // start with max possible groups: each row can fit 2 groups, if empty
        int totalGroups = n * 2;

        // process each row with reservations
        for(int row : rowMap.keySet()) {
            Set<Integer> reserved = rowMap.get(row);
            // check possible 4-seat groups for left, middle, right
            boolean isLeftAvailable = 
                !reserved.contains(2) && !reserved.contains(3) 
                && !reserved.contains(4) && !reserved.contains(5);

            boolean isMidAvailable = 
                !reserved.contains(4) && !reserved.contains(5) 
                && !reserved.contains(6) && !reserved.contains(7);

            boolean isRightAvailable = 
                !reserved.contains(6) && !reserved.contains(7) 
                && !reserved.contains(8) && !reserved.contains(9);

            // determine, number of group can fit in a row
            int groupInARow = 0;
            if(isLeftAvailable && isRightAvailable) 
                groupInARow = 2;
            else if (isLeftAvailable || isMidAvailable || isRightAvailable)
                groupInARow = 1;

            // Adjust total: remove assumed 2 gourps, add actual groups
            totalGroups = totalGroups - 2 + groupInARow;
        }
        
        return totalGroups;       
    }

}
