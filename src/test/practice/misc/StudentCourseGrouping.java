package test.practice.misc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


/*
 * https://gist.github.com/okosodovictor/f143219a0641a67756297430761865c7
 * 
 * 
You are a developer for a university. 
Your current project is to develop a system for students to find courses they share with friends. 
The university has a system for querying courses students are enrolled in, returned as a list of (course_id, course_name) pairs.

Write a function that takes in a list of (student_id, course_name) pairs and returns, for every pair of students, a list of all courses they share.

Sample Input:

student_course_pairs_1 = [
  ["58", "Software Design"],
  ["58", "Linear Algebra"],
  ["94", "Art History"],
  ["94", "Operating Systems"],
  ["17", "Software Design"],
  ["58", "Mechanics"],
  ["58", "Economics"],
  ["17", "Linear Algebra"],
  ["17", "Political Science"],
  ["94", "Economics"],
  ["25", "Economics"],
]

Sample Output (pseudocode, in any order):

find_pairs(student_course_pairs_1) =>
{
  [58, 17]: ["Software Design", "Linear Algebra"]
  [58, 94]: ["Economics"]
  [58, 25]: ["Economics"]
  [94, 25]: ["Economics"]
  [17, 94]: []
  [17, 25]: []
}

*/
// ----------------------------------------------------
/*


Additional test cases:

Sample Input:

student_course_pairs_2 = [
  ["42", "Software Design"],
  ["0", "Advanced Mechanics"],
  ["9", "Art History"],
]

Sample output:

find_pairs(student_course_pairs_2) =>
{
  [42, 0]: []
  [42, 9]: []
  [0, 9]: []
}
// No courses are shared between students


- Read in student_course_pairs input as:
  - a map <studentId, List<courses>>
  - a map of <course_name, List<students>>
- Generate all the student pairings
  - see the courses they have in common

// O(p) where p is number of student-course pairs
- Read in student_course_pairs input as:  
  - a map <studentId, List<courses>>
// O(s^2) to generate student pairings
- Run double-nested for-loop over the keys to generate each student pairing
  // O(1) if each student has a map of <course_name, boolean>
  - For each pairing, check the courses each student is in, and compute intersection

*/

public class StudentCourseGrouping {

	public static void main(String[] args) {

		String[][] student_course_pairs_1 = {
			 {"58", "Software Design"},
			 {"58", "Linear Algebra"},
			 {"94", "Art History"},
			 {"94", "Operating Systems"},
			 {"17", "Software Design"},
			 {"58", "Mechanics"},
			 {"58", "Economics"},
			 {"17", "Linear Algebra"},
			 {"17", "Political Science"},
			 {"94", "Economics"},
			 {"25", "Economics"}
		                         
		};
		  
		String[][] student_course_pairs_2 = {
				{"42", "Software Design"},
				{"0", "Advanced Mechanics"},
				{"9", "Art History"},
		};
		
		findPair(student_course_pairs_1);
		  
	}
	
	private static void findPair(String[][] arr) {
		
		if(arr == null || arr.length == 0) return;
		
		List<List<String>> lists = new ArrayList<>();
		
		Map<String, Set<String>> map = buildCourseMap(arr);
		System.out.println("map:: "+map);
		
		List<String> ids = new ArrayList<>(map.keySet());
		for(int i=0; i<ids.size()-1; i++) {
			String key_1 = ids.get(i);
			HashSet<String> set_1 = (HashSet<String>) map.get(key_1);
			for(int j=i+1; j<ids.size(); j++) {
				HashSet<String> temp = (HashSet<String>) set_1.clone();
				
				String key_2 = ids.get(j);
				
				temp.retainAll(map.get(key_2));
				if(!temp.isEmpty()) {
					List<String> list = new ArrayList<>();
					String s = "[" + key_1 + ", "+ key_2 + "] : " + temp;
					list.add(s);
					lists.add(list);
				}
			}
		}
		System.out.println(lists);
	}

	private static Map<String, Set<String>> buildCourseMap(String[][] arr) {
		
		Map<String, Set<String>> map = new HashMap<>();
		for(String[] a : arr) {
			if(!map.containsKey(a[0])) {  //--zero index is student id
				Set<String> set = new HashSet<>();
				set.add(a[1]); //--course
				map.put(a[0], set);
				
			} else {
				Set set = map.get(a[0]);
				set.add(a[1]);
				map.put(a[0], set);
			}
		}
		
		return map;
	}
}
