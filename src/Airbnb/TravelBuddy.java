package Airbnb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


//--https://massivealgorithms.blogspot.com/2017/07/buddy-list-airbnb.html

public class TravelBuddy {

	public static void main(String[] args) {
		Set<String> myWishList = new HashSet<>(Arrays.asList(new String[]{"a", "b", "c", "d"}));
        Set<String> wishList1 = new HashSet<>(Arrays.asList(new String[]{"a", "b", "e", "f"}));
        Set<String> wishList2 = new HashSet<>(Arrays.asList(new String[]{"a", "c", "d", "g"}));
        Set<String> wishList3 = new HashSet<>(Arrays.asList(new String[]{"c", "f", "e", "g"}));
        Map<String, Set<String>> friendWishLists = new HashMap<>();
        friendWishLists.put("Buddy1", wishList1);
        friendWishLists.put("Buddy2", wishList2);
        friendWishLists.put("Buddy3", wishList3);
        
        TravelBuddy buddy = new TravelBuddy();
        List<Buddy> buddies = buddy.getTravelBuddies(myWishList, friendWishLists);
        System.out.println(buddies);
        List<String> res = buddy.recommendCities(myWishList, buddies, 10);
        System.out.println(res);
        

	}
	
	
	public List<Buddy> getTravelBuddies(Set<String> myWishList, Map<String, Set<String>> friendWishLists) {
		List<Buddy> buddies = new ArrayList<Buddy>();

		for (String name : friendWishLists.keySet()) {
			Set<String> buddyWishList = friendWishLists.get(name);
			Set<String> intersection = new HashSet<>(buddyWishList);
			intersection.retainAll(myWishList);
			int similarity = intersection.size();
			
			if(similarity >= buddyWishList.size()/2) {  //--i.e. more than 50%
				buddies.add(new Buddy(name, similarity, buddyWishList));
			}
		}
		
		return buddies;
	}
	

	/**
	 * 
	 * @param buddies
	 * @param k : no of recommendations
	 * @return
	 */
	public List<String> recommendCities(Set<String> myWishList, List<Buddy> buddies, int k) {
		List<String> res = new ArrayList<>();
		buddies = getSortedList(buddies);
		
		int i=0;
		while (k >=0 && i < buddies.size()) {
			
			Set<String> diff = new HashSet<>(buddies.get(i).wishList);
			diff.removeAll(myWishList);  //--contains only the diff
			
			if(diff.size() <= k) {
				res.addAll(diff);
				k -= res.size();
				i++;
			} else { //--iterate the diff till k i.e. no of recommenation that need to return
				for(String s : diff) {
					if(k<0) {
						res.add(s);
						k--;
					} else {
						break;
					}
				} 
				
				/* Other way
				 * Iterator<String> it = diff.iterator();
                while (k > 0) {
                    res.add(it.next());
                    k--;
                }*/
			}
			
		}
		return res;
		
	}
	
	
	
	public List<Buddy> getSortedList(List<Buddy> buddies) {
		Collections.sort(buddies);
		List<Buddy> res = new ArrayList<>(buddies);
		return res;
	}

	
	
	//---inner class
	private static class Buddy implements Comparable<Buddy> {

		String name;
		int similarity;
		Set<String> wishList;
		
		public Buddy(String name, int similarity, Set<String> wishList) {
			this.name = name;
			this.similarity = similarity;
			this.wishList = wishList;
		}
		
		@Override
		public int compareTo(Buddy that) { //-- need only when we need to sort. We are doing this to recommend cities
			return this.similarity - that.similarity;
		}
		
		@Override
		public String toString() {
			//return "[" + name + ", "+ similarity + ", " + wishList + "]";
			return "[" + name + "]";
		}
		
	}
}
