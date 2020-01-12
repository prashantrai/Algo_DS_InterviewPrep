package Airbnb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class DisplayPage {

	
	//--https://leetcode.com/discuss/interview-question/125518/1st-telephone-interview-paginate-listings
	//--https://www.careercup.com/page?pid=airbnb-interview-questions
	//--https://github.com/allaboutjst/airbnb/blob/master/src/main/java/display_page/DisplayPage.java
	public static void main(String[] args) {
		
		String[] strs = new String[]{
                "1,28,310.6,SF",
                "4,5,204.1,SF",
                "20,7,203.2,Oakland",
                "6,8,202.2,SF",
                "6,10,199.1,SF",
                "1,16,190.4,SF",
                "6,29,185.2,SF",
                "7,20,180.1,SF",
                "6,21,162.1,SF",
                "2,18,161.2,SF",
                "2,30,149.1,SF",
                "3,76,146.2,SF",
                "2,14,141.1,San Jose"

        };
		
		DisplayPage displayPage =  new DisplayPage(); 
        List<String> input = new ArrayList<>(Arrays.asList(strs));
        List<String> result = displayPage.displayPages(input, 5);
        System.out.println(result);

	}
	
	public List<String> displayPages(List<String> input, int page_size) {
		List<String> res = new ArrayList<String>();
		if(input == null || input.isEmpty()) return res;
		
		Set<String> visited = new HashSet<>();
		int counter = 0; //--count to keep track of page size
		Iterator<String> input_iter = input.iterator();
		boolean reachEnd = false;
		
		while (input_iter.hasNext()) {
			
			String curr = input_iter.next();
			String hostId = curr.split(",")[0];
			
			if(!visited.contains(hostId) || !reachEnd) {
				res.add(curr);
				visited.add(hostId);
				input_iter.remove();
				counter++;
			}
			
			if(counter == page_size) {
				if(!input.isEmpty())
					res.add(" ");
				visited.clear();
				counter = 0;
				reachEnd = false;
				input_iter = input.iterator(); //-- reinitialize the iterator to start from top for next page.
			}
			
			if(!input_iter.hasNext()) {
				reachEnd = false;
				input_iter = input.iterator();
			}
			
		}
		
		return res;
		
	}

}
