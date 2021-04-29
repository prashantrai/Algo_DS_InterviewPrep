package leetcode;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class DesignBrowserHistory_1472_Medium {

	public static void main(String[] args) {
		BrowserHistory browserHistory = new BrowserHistory("leetcode.com");
		
		browserHistory.visit("google.com");       // You are in "leetcode.com". Visit "google.com"
		browserHistory.visit("facebook.com");     // You are in "google.com". Visit "facebook.com"
		browserHistory.visit("youtube.com");      // You are in "facebook.com". Visit "youtube.com"
		
		print( browserHistory.back(1) );                   // You are in "youtube.com", move back to "facebook.com" return "facebook.com"
		print( browserHistory.back(1) );                   // You are in "facebook.com", move back to "google.com" return "google.com"
		print( browserHistory.forward(1) );                // You are in "google.com", move forward to "facebook.com" return "facebook.com"
	
		browserHistory.visit("linkedin.com");     // You are in "facebook.com". Visit "linkedin.com"
		
		print( browserHistory.forward(2) );                // You are in "linkedin.com", you cannot move forward any steps.
		print( browserHistory.back(2) );                   // You are in "linkedin.com", move back two steps to "facebook.com" then to "google.com". return "google.com"
		print( browserHistory.back(7) );                   // You are in "google.com", you can move back only one step to "leetcode.com". return "leetcode.com"
		
	}
	
	public static void print(String s) {
		System.out.println(s);
	}
	
}

// https://leetcode.com/problems/design-browser-history/submissions/

//with map - https://leetcode.com/problems/design-browser-history/discuss/1019665/Java-solution-using-hashmap-O(1)-operation
class BrowserHistory {
	Map<Integer, String> map;
	int curr;
	int max;

	public BrowserHistory(String homepage) {
		curr = max = 0;
		map = new HashMap<>();
		map.put(curr, homepage);
	}

	public void visit(String url) {
		map.put(++curr, url);
		max = curr;
	}

	public String back(int steps) {
		curr = Math.max(0, curr - steps); // we can't go negative
		return map.get(curr);
	}

	public String forward(int steps) {
		curr = Math.min(max, curr + steps); // we can't go beyond the size future/fprward which is max
     return map.get(curr);
 }
}


// with Stack - https://leetcode.com/problems/design-browser-history/discuss/674486/Two-Stacks-Pretty-code.
class BrowserHistory2 {
    private Stack<String> future;   //forward
    private Stack<String> history;
    
    public BrowserHistory2(String homepage) {
        future = new Stack<>();
        history = new Stack<>();
        history.push(homepage);
    }
    
    public void visit(String url) {
        history.push(url);
        future = new Stack<>();
    }
    
    public String back(int steps) {
        while(steps > 0 && history.size() > 1) {
            future.push(history.pop());
            steps--;
        }
        return history.peek();
    }
    
    public String forward(int steps) {
        while(steps > 0 && future.size() > 0) { // future can be empty
            history.push(future.pop());
            steps--;
        }
        
        return history.peek();
    }
}