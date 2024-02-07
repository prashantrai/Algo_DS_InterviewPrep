package TikTok;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class AmountOfNewAreaPaintedEachDay_2158_Hard {

	public static void main(String[] args) {

//		int[][] paint = {{1,4},{4,7},{5,8}};
		int[][] paint = {{1,4}, {5,8}, {4,7}};
//		int[] res = amountPainted(paint);
		int[] res = amountPainted2(paint);
		
		System.out.println("Expected: [3, 3, 1] Actual: " + Arrays.toString(res));
		
		int[][] paint2 = {{1,5}, {2,4}};
//		int[] res2 = amountPainted(paint2);
//		System.out.println("Expected: [4, 0] Actual: " + Arrays.toString(res2));
		
		int[][] paint3 = {{0,5},{0,2},{0,3},{0,4},{0,5}};
//		int[] res3 = amountPainted(paint3);
//		System.out.println("Expected: [5,0,0,0,0] Actual: " + Arrays.toString(res3));
	}
	
	// Time complexity can be improved to O(NlogN) 
	
	

	/*
    Time: O(N^2)
    Space: O(N^2), because in worst case we'll be end up storing all the combination.
    */
    
	public static int[] amountPainted(int[][] paint) {
		if(paint.length == 0)
            return new int[]{0};
		
		List<Integer> ans = new ArrayList<>();
		
		Map<Integer, Integer> map = new HashMap<>();
		/* fill map with all the area that has been painted, i.e. for every index
		 * e.g. for range [1, 4] map will be {1:4, 2:4, 3:4} and similarly add for every 
		 * wall/index in the given range
		 * 
		 * final map for input {{1,4},{4,7},{5,8}} will look like,
		 * 	{1:4, 2:4, 3:4, 4:7, 5:7, 6:7, 7:8}
		 */
		
		for(int[] p : paint) {
			int start = p[0];
			int end = p[1];
			int work = 0;
			
			while(start <  end) {
				if(!map.containsKey(start)) {
					map.put(start, end);
					start++;
					work++;
					continue;
				} else {
					int prevEnd = map.get(start);
					
					// when there are entries with same start for different ends then 
					// set the max value among both end value and set as start for next
					// iteration, e.g. if entries are {4,7}, {4,9} then start would be
					// max(7,9) i.e. 9 as 7 has already been painted
					// also update this value in map 
					// i.e. entry {4:7} in map will be updated to {4:9}
					map.put(start, Math.max(map.get(start), end));
					
					start = prevEnd;
				}
			}
			ans.add(work);
		}
		
		// List<Integer> to Primitive type array i.e. int[]
		return ans.stream().mapToInt(i->i).toArray();
	}
	
	
	/*
    Time: O(N log N)
    Space: O(N), because in worst case we'll be end up storing all the combination.
    */
	public static int[] amountPainted2(int[][] paint) {
        int n = paint.length;
        TreeMap<Integer, Integer> map = new TreeMap<>();
        int[] res = new int[n];
        
        for (int i = 0; i < n; i++) {
            int start = paint[i][0], end = paint[i][1];
            int paintArea = end - start;
            
            Map.Entry<Integer, Integer> floor = map.floorEntry(paint[i][0]);
            
            if (floor != null) {
                if (floor.getValue() >= end)
                    continue;
                else if (floor.getValue() >= start) {
                    paintArea -= floor.getValue() - start;
                    start = Math.min(start, floor.getKey());
                    map.remove(floor.getKey());
                }
            }
            
            Map.Entry<Integer, Integer> ceilings = map.ceilingEntry(paint[i][0]);
            
            while (ceilings != null && ceilings.getKey() <= end) {
                paintArea -= Math.min(end, ceilings.getValue()) - ceilings.getKey();
                map.remove(ceilings.getKey());
                end = Math.max(end, ceilings.getValue());
                ceilings = map.ceilingEntry(paint[i][0]);
            }
            
            res[i] = paintArea;
            map.put(start, end);
        }
        
        return res;
    }

}
