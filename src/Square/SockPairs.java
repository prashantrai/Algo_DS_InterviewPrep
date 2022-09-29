package Square;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class SockPairs {

	public static void main(String[] args) {
		List<String> socks 
			= Arrays.asList("black and left", "pink and right", "pink and left", "black and right", "black and right");

		
		List<Sock> res = getSockPairs(socks);
		System.out.println("res:: "+res);
		
		// another approach
		List<int[]> socksRes = getSockPair(socks);
		socksRes.forEach(x -> System.out.print(" (" + x[0] + "," + x[1] + ") "));
	}
	
	// https://leetcode.com/discuss/interview-question/1555905/square-phone-sock-pairs
	private static List<int[]> getSockPair(List<String> socks) {
        List<int[]> output = new ArrayList<>();
        Map<String, Integer> map = new HashMap<>();
        
        for(int i=0;i<socks.size();i++) {
            String[] arr = socks.get(i).split(" ");
            
            String color = arr[0];
            String foot = arr[2];
            
            String key = foot.equals("left") ? color + " and right" : color + " and left";
            
            if(map.containsKey(key)) {
                output.add(new int[]{i+1, map.get(key)+1});
                map.remove(key);// to avoid duplicates
            } else {
                map.put(socks.get(i), i);   
            }
        }
        return output;
    }
	
	public static List<Sock> getSockPairs(List<String> socks) {
		
		Map<String, List<Sock>> map = new HashMap<>();
		
		for(int i=0; i<socks.size(); i++) {
			String sock = socks.get(i);
			String[] arr = sock.split(" ");  // returns {black,and,left}
			String color = arr[0];
			String foot = arr[2];
			
			if(map.containsKey(color)) {
				// get the list, list may contain more that one pair for same color so iterate the list and match the
				// pair for unmatched sock
				List<Sock> l = map.get(color);
				
				boolean isSockFound = false;

				// iterate through each sock in the list to find missing pair and update
				for(Sock s : l) {
					if("left".equals(foot) && s.left == -1) {
						s.left = i+1;
						s.q.add(i+1);
						isSockFound = true;
						break;
					}
					if("right".equals(foot) && s.right == -1) {
						s.right = i+1;
						s.q.add(i+1);
						isSockFound = true;
						break;
					}
				}
				
				if(!isSockFound) {
					// add new sock
					Sock ss = getNewSock(i, foot); // get new sock
					l.add(ss);
				}
				
			} else {
				Sock s = getNewSock(i, foot); // get new sock
				
				List<Sock> l = new ArrayList<>();
				l.add(s);
				map.put(color, l);
			}
		}
		
		System.out.println("map: "+map);
		
		ArrayList<Sock> list = new ArrayList<>();

		for (List<Sock> e : map.values()) {
			if(!e.isEmpty())
				list.add(e.get(0));
		}
		return list;
	}
	
	private static Sock getNewSock(int i, String foot) {
		Sock s = new Sock();
		int val = i + 1;
		if("left".equals(foot)) {
			s.left = val;
		} else {
			s.right = val;
		}
		s.q.add(val);
		return s;
	}

	private static class Sock {
		int left;
		int right;
		Queue<Integer> q;
		
		public Sock() {
			left = -1;
			right = -1;
			q = new ArrayDeque<>();
		}
		
		public void setLeft(int val) {
			left = val;
			q.add(val);
		}
		public void setRight(int val) {
			right = val;
			q.add(val);
		}
		
		public String toString() {
			return q.toString();
		}
	}
}


/*

 Nov2021: [https://leetcode.com/discuss/interview-question/1555905/square-phone-sock-pairs]
Question:
You are given information on a Sock object such as color and foot (left or right). For example, consider the below as input:

	1. black and left
	2. pink and right
	3. pink and left
	4. black and right
	5. black and right
You have to write a method which takes the following input and return the list of Sock object pairs (same color, different foot) which are:

(1, 4) *OR *(1, 5)
(2, 3)

Note if a sock is repeated in a pair then only return 1 pair where that sock is used.
 
 * */
