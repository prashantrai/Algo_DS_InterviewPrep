package leetcode;

import java.util.HashMap;
import java.util.Map;

public class RabbitsInForest_781_Medium {

	public static void main(String[] args) {

		int[] answers = { 1, 1, 2 };
		int res = numRabbits(answers);
		System.out.println("Expected: 5, Actual: " + res);
		
		int[] answers_1 = {10, 10, 10};
		res = numRabbits(answers_1);
		System.out.println("Expected: 11, Actual: "+res);
		
		int[] answers_2 = { 1, 0, 1, 0, 0 };
		res = numRabbits(answers_2);
		System.out.println("Expected: 5, Actual: " + res);
		
		int[] answers_3 = { 0, 0, 1, 1, 1 };
		 res = numRabbits(answers_3);
		 System.out.println("Expected: 6, Actual: "+res);

	}
	
	//--https://leetcode.com/problems/rabbits-in-forest/
	//--https://leetcode.com/problems/rabbits-in-forest/discuss/533422/Java-Using-Hashmap-tried-to-explain-the-solution-in-comments

	public static int numRabbits(int[] answers) {

		HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
		int count = 0;

		for (int ans : answers) {

			if (!map.containsKey(ans)) {
				map.put(ans, 0); // count of same ans, starting the count from zero
				count += ans + 1;
			} else {
				/*
				 * if the current ans and the count of ans in map, 
				 * i.e. if freq 2 is 4 times in input array then the 4th is a 
				 * different group and we should start the count for diff group 
				 * by reseting the value on map.
				 * 
				 * Example: for {2,2,2,2} total count will be 5 (think of this you asked 3 rabbits and they answer 2 which makes total count 3 for same color but if the 4th is answering 2 too it means it doesn't belong to same color)
				 * map will look like {2=2} for the first 3 (note when ans is 2 that means total count of same color is 2+1)
				 * and when the 4th '2' encounter it definitely a different color otherwise
				 * previous answers doesn't make sense and that's where a new entry i.e. we'll update/reset the 
				 * current map value to start the count for a new color
				 * updated map will look like {2=0};
				 * 
				 * so, for till first 3 rabbit map will be {2=2} and for the 4th it
				 * will be updated to {2=1} and we will be incrementing the count by ans+1 whenever a new color encounters
				 * 
				 * 
				 */
				if (map.get(ans) == ans) {  
					map.put(ans, 0);
					count += ans + 1;
				} else {
					map.put(ans, map.get(ans) + 1);
				}
			}
		}

		return count;
	}

}
