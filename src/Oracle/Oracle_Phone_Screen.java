package Oracle;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class Oracle_Phone_Screen {

	public static void main(String[] args) {
		
	    
	    Map<String, List<String>> map = new HashMap<>();
	    map.put("Bob", Arrays.asList("Sandra", "Alice", "Eric"));
	    map.put("Sandra", Arrays.asList("Bob", "Don"));
	    map.put("Alice", Arrays.asList("Bob", "John"));
	    map.put("Eric", Arrays.asList("Bob", "Kate", "Victor"));
	    map.put("Don", Arrays.asList("Sandra", "Tim"));
	    map.put("John", Arrays.asList("Tim", "Kate"));
	    map.put("Tim", Arrays.asList("Don"));
	    
	    List<Set<String>> res = getAllFriends(map, "Bob"); // Looks good
	    System.out.println("RESULT:: " + res);
	    
	    //dfs(map, "Bob");
	    //System.out.println("RESULT:: " + result);
	  }

	 
	// Suppose you are designing a social network. Every member has a list of his/her direct friends.
	// Member's direct friends are level 1 friends.
	// Member's friends' friends are level 2 friends, and so on.
	// Part 1: Write a function which takes a map (map of member name to list of direct friends) and a member name and returns a list of member's level 1, level 2, level n friends.
	// Part 2: Invoke the function from the main code and print the result.
	 
	// Example:
	// Map:
	//	      Bob    : Sandra, Alice, Eric
	//	      Sandra : Bob, Don
	//	      Alice  : Bob, John
	//	      Eric   : Bob, Kate, Victor
	//	      Don    : Sandra, Tim
	//	      John   : Tim, Kate
	//	      Tim    : Don
	// Member Name: Bob
	// Result:
	//	     Level 1 friends: Sandra, Alice, Eric
	//	     Level 2 friends: Don, John, Kate, Victor
	//	     Level 3 friends: Tim

	// Map<String, List<String>>

	  /*
	    
	    1. create a Q
	    2. Created a result list e,g,  List<List<String>>
	    3. Offer all the elemets in Q for the input name i.e. Bob
	    4. Add all the firends for the input name to the result list
	    5. while Q is not empty 
	        a. poll from Q will resturn the element/frnd
	        b. push all the frnd of step a into result list (creat a new list and add into result list)
	    
	    */
	  
	// BFS
	  public static List<Set<String>> getAllFriends(Map<String, List<String>> map, String name) {
	    
	    List<Set<String>> res = new ArrayList<>();
	    //sanity check
	    if(name == null || name.isEmpty() || map.size() == 0) return res;
	    
	    Queue<String> q = new ArrayDeque<>();
	    List<String> frnd = map.get(name);
	    q.addAll(frnd);
	    
	    Set<String> lvl = new HashSet<>();
	    lvl.addAll(frnd); //level 1 friends
	    res.add(lvl);
	    
	    Set<String> visited = new HashSet<>();
	    visited.add(name);
	    
	    // To keep track friends already visited in previous level
	    Set<String> visitedFriendList = new HashSet<>();
	    visitedFriendList.addAll(frnd);
	    
	    while(!q.isEmpty()){
	      
	      int size = q.size();
	      
	      Set<String> nextLevel = new HashSet<>();
	      for(int i=0; i<size; i++) {
	        String person = q.poll();
	        if(visited.contains(person)) continue;
	        
	        visited.add(person);
	        List<String> personList = map.get(person);
	        
	        if(personList == null) continue;
	        
	        q.addAll(personList);
	        addAndGetFriends(nextLevel, visited, visitedFriendList, personList);
	        
		    // To keep track friends already visited in previous level so that they 
	        // don't appear in another level
	        visitedFriendList.addAll(personList);
	        
	      }
	      res.add(nextLevel);
	    }
	  
	    return res;  
	    
	  }

	  public static void addAndGetFriends(Set<String> level, Set<String> visited, Set<String> visitedFriendList, List<String> personList) {
		  for(String s : personList) {
			  if(visited.contains(s) || visitedFriendList.contains(s)) continue;
			  level.add(s);
		  }
	  }
	  
	  
	  // need to debug and fix
	  public static Set<String> visited = new HashSet<>();
	  public static Set<String> visitedFriends = new HashSet<>(); //visited at one of the previous level
	  public static List<Set<String>> result = new ArrayList<>();
	  
	  public static void getAllFriendsDFS(Map<String, List<String>> map, String name) {
		  //dfs(map, name, )
	  }
	  
	// Map:
		//	      Bob    : Sandra, Alice, Eric
		//	      Sandra : Bob, Don
	  public static void dfs(Map<String, List<String>> map, String name) {
		  
		  if(!map.containsKey(name) || name == null || name.isEmpty()) return;
		  
		  if(visited.contains(name)) return;
		  visited.add(name);
		  
		  List<String> friends = map.get(name);
		  
		  Set<String> levelFrnds = new HashSet<>();
		  result.add(createFriendsLvl(friends, levelFrnds));
		  
		  visitedFriends.addAll(friends);
		  
		  for(String s : friends)
			  dfs(map, s);

		  
	  }
	  
	  public static Set<String> createFriendsLvl(List<String> friends, Set<String> levelFrnds) {
		  
		  for(String s : friends) {
			  if(visited.contains(s) || visitedFriends.contains(s)) continue;
			  //if(visited.contains(s)) continue;
			  levelFrnds.add(s);
		  }
		  return levelFrnds;
	  }
}
