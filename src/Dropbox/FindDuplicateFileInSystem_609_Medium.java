package Dropbox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FindDuplicateFileInSystem_609_Medium {

	public static void main(String[] args) {

		/*Input:
			["root/a 1.txt(abcd) 2.txt(efgh)", "root/c 3.txt(abcd)", "root/c/d 4.txt(efgh)", "root 4.txt(efgh)"]
		Output:  
			[["root/a/2.txt","root/c/d/4.txt","root/4.txt"],["root/a/1.txt","root/c/3.txt"]]
		*/
		String[] paths = 
				{"root/a 1.txt(abcd) 2.txt(efgh)", "root/c 3.txt(abcd)", "root/c/d 4.txt(efgh)", "root 4.txt(efgh)"};
		
		List<List<String>> res = findDuplicate(paths);
		
		System.out.println("Result: "+ res);
		
	}

	/*
	 * https://leetcode.com/problems/find-duplicate-file-in-system/
	 * Complexity Analysis
	 *		Time complexity : O(n*x). n strings of average length x is parsed.
	 *		Space complexity : O(n*x). map and res size grows upto n∗x.
	 * */
	
	public static List<List<String>> findDuplicate(String[] paths) {
		
		
		List<List<String>> res = new ArrayList<>();
		Map<String, List<String>> map = new HashMap<>();
		
		if(paths == null || paths.length == 0) {
			return res;
		}
		
		for(String path : paths) {
			String[] p = path.split(" ");
			
			for(int i=1; i<p.length; i++) {
				String s = p[i]; 
				String key = s.substring(s.indexOf('('));
				
				if(!map.containsKey(key)) {
					map.put(key, new ArrayList<String>());
				}
				map.get(key).add(p[0] + "/" + s);
			}
		}
		
		System.out.println(map);
		
		//List<List<String>> res2 = new ArrayList<>(map.values()); //another way
		//System.out.println(">>> "+res2);
		
		//map.values().stream().filter(predicate)
		
		for(String key : map.keySet()) {
			if(map.get(key).size() > 1) {
				res.add(map.get(key));
			}
		}
		return res;
	}
	
	
}
