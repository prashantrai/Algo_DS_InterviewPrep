package doordash;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

public class Search_Suggestions_System_1268_Medium {

	public static void main(String[] args) {
		
		String products[] = {"mobile","mouse","moneypot","monitor","mousepad"};
		String searchWord = "mouse";
		System.out.println("Expected: [[mobile, moneypot, monitor],[mobile, moneypot, monitor],[mouse, mousepad],[mouse, mousepad],[mouse, mousepad]]");
		System.out.println("Actual: " + suggestedProducts(products, searchWord));
	}

	
	/*
	Refer: https://leetcode.com/problems/search-suggestions-system/discuss/436674/C%2B%2BJavaPython-Sort-and-Binary-Search-the-Prefix
    
    Time: O(NlogN)
    Space: O(N), TreeMap stores all the products, which has a space complexity of O(N).
    */
    
    public static List<List<String>> suggestedProducts(String[] products, String searchWord) {
        
    	List<List<String>> res = new ArrayList<>();
        TreeMap<String, Integer> treeMap = new TreeMap<>();
        Arrays.sort(products); // O(NlogN)
        List<String> productsList = Arrays.asList(products);
        
        // O(NlogN)
        for(int i=0; i<products.length; i++) { // O(N)
            treeMap.put(products[i], i); // O(logN)
        }
        
        String key = "";
        for(char c : searchWord.toCharArray()) {
            key += c;
            String ceiling = treeMap.ceilingKey(key);
            
            // prefix + "~" helps find the upper bound, '~' is one option, 
            // any character after 'z' should be also working such as '{'.
            // In short: word < word + "~"
            String floor = treeMap.floorKey(key+"~");
            
            if(ceiling == null || floor == null) break;
            
            res.add(
                productsList.subList(
                    treeMap.get(ceiling),
                    
                    // Limiting to 3 Products
                    // when not all of the top three elements from the ceiling match 
                    // the current regex, for example "mou" will only give mouse and 
                    // mousepad. for an example like 
                    // ["mobile","mouse","moneypot","monitor","mousepad", "movie"] 
                    // ceiling+3 will give "mouse","mousepad","movie".
                    
                    // Potential ending index for 3 products: treeMap.get(ceiling) + 3
                    // Ending index within the prefix range: treeMap.get(floor) + 1
                    // The Math.min(treeMap.get(ceiling) + 3, treeMap.get(floor) + 1) 
                    // ensures that the sublist includes up to 3 products but not 
                    // exceeding the range of products that match the prefix.
                    Math.min(treeMap.get(ceiling) + 3, treeMap.get(floor)+1)
                ));
            
        }
        
        while (res.size() < searchWord.length()) res.add(new ArrayList<String>());
        
        return res;
    }
}
