package Square;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RankTeamsByVotes_1366_Medium {

	public static void main(String[] args) {
		
		String[] votes = {"ABC","ACB","ABC","ACB","ACB"};

		System.out.println("Expected: ACB, Atual: "+ rankTeams_UsingMap(votes));
		System.out.println("Expected: ACB, Atual: "+ rankTeams(votes)); // using array
		
		// using array, last column of array is number is alphabets positions i.e. 0 for A, 1 for B and so on
		System.out.println("Expected: ACB, Atual: "+ rankTeams2(votes));  

	}

	
	/* 
	 * using map
	 	https://leetcode.com/problems/rank-teams-by-votes/discuss/524853/Java-O(26n%2B(262-*-log26))-Sort-by-high-rank-vote-to-low-rank-vote
	
	 	Time complexity: O(26 + n * c + 26 * log 26 * c + c) = O(n).
	 	c is a constant number, and c <= 26.
			
		Space: O(1) or constant, because here O(26*len), here len can also be max 26, so, 
		in worst case space will be O(26*26)
	*/
	public static String rankTeams_UsingMap(String[] votes) {
	      Map<Character, int[]> map = new HashMap<>();
	      int l = votes[0].length();
	      for(String vote : votes){
	        for(int i = 0; i < l; i++){
	          char c = vote.charAt(i);
	          map.putIfAbsent(c, new int[l]);
	          map.get(c)[i]++;
	        }
	      }
	      
	      List<Character> list = new ArrayList<>(map.keySet());
	      Collections.sort(list, (a,b) -> {
	        for(int i = 0; i < l; i++){
	          if(map.get(a)[i] != map.get(b)[i]){
	            return map.get(b)[i] - map.get(a)[i];
	          }
	        }
	        return a - b;
	      });
	      
	      StringBuilder sb = new StringBuilder();
	      for(char c : list){
	        sb.append(c);
	      }
	      return sb.toString();
	}
	
	// https://leetcode.com/problems/rank-teams-by-votes/discuss/533307/Java-Straight-Forward-Clean-code
	public static String rankTeams(String[] votes) {
        int n = votes[0].length();
        int[][] voteCount = new int[26][n]; 
        for (String vote : votes) {
            for (int i = 0; i < n; i++) {
                char c = vote.charAt(i);
                voteCount[c-'A'][i]++; 
            }
        }
        
        char[] ans = votes[0].toCharArray();
        Character[] tmp = new Character[n];
        for (int i = 0; i < n; i++) 
            tmp[i] = ans[i];
                
        Arrays.sort(tmp, (t1, t2) -> {
            for (int i = 0; i < n; i++)
            	// If team1 and team2 don't tie on the 'ith' position
                if (voteCount[t1-'A'][i] != voteCount[t2-'A'][i]) 
                    return voteCount[t2-'A'][i] - voteCount[t1-'A'][i]; // return team with higher votes
            return t1 - t2; 
        });
        for (int i = 0; i < n; i++) 
            ans[i] = tmp[i];
        return new String(ans);
    }
	
	// https://leetcode.com/problems/rank-teams-by-votes/discuss/619556/Java-solution-with-explanation-4ms
	/*
	 * 
	 *I want to use example1 to explain this solution.
		we have candidates A, B, C. So we can record the number of votes in every position for each candidate.
		A: 5, 0, 0
		B: 0, 2, 3
		C: 0, 3, 2
		we imagine it is a 2D array. we try to sort the array. After sorting, we should make the array like this:
		A: 5, 0, 0
		C: 0, 3, 2
		B: 0, 2, 3
		Obviously, ACB is the result we want.
		
		Now, we just need to use coding to realizing it.
		
		we define an array, every row denote a candidate, every column denotes a position.
		we add an extra column to identify which candidate the row is.
		we record the number of votes in every position for each candidate by using for-loop
		sort the array. we define a method to compare candidates.
		after sorting, build string.
		
		Time complexity: O(26 + n * c + 26 * log 26 * c + c) = O(n).
		c is a constant number, and c <= 26.
		
		Space: O(1) or constant, because here O(26*len), here len can also be max 26, so, in worst case space will be O(26*26)
	 */
	public static String rankTeams2(String[] votes) {
        int len = votes[0].length();
        int[][] map = new int[26][len + 1];
        
        for(int i = 0; i < 26; i++) 
        	map[i][len] = i;
        
        for(int i = 0; i < votes.length; i++){
            String s = votes[i];
            for(int j = 0; j < len; j++){
                map[s.charAt(j) - 'A'][j]++;
            }
        }
        
        Arrays.sort(map, (a, b) ->{
            for(int i = 0; i < len; i++){
                if(a[i] < b[i]) return 1;
                if(a[i] > b[i]) return -1;
            }
            return 0;
        });
        
        StringBuilder sb = new StringBuilder();
        
        for(int i = 0; i < len; i++){
            sb.append((char)('A' + map[i][len]));
        }
        
        return sb.toString();
    }
}
