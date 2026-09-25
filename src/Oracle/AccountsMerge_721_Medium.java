package Oracle;

import java.util.*;

public class AccountsMerge_721_Medium {

	public static void main(String[] args) {

        // Test 1 - Valid: standard merge through common email.
        List<List<String>> test1 = Arrays.asList(
            Arrays.asList(
                "John",
                "johnsmith@mail.com",
                "john_newyork@mail.com"
            ),
            Arrays.asList(
                "John",
                "johnsmith@mail.com",
                "john00@mail.com"
            ),
            Arrays.asList(
                "Mary",
                "mary@mail.com"
            ),
            Arrays.asList(
                "John",
                "johnnybravo@mail.com"
            )
        );

        System.out.println("Test 1 - Valid");
        System.out.println("Expected: 3 merged accounts");
        System.out.println("Actual:   " + accountsMerge(test1));
        System.out.println();

        // Test 2 - Valid: transitive merging across accounts.
        List<List<String>> test2 = Arrays.asList(
            Arrays.asList("Alex", "a@mail.com", "b@mail.com"),
            Arrays.asList("Alex", "b@mail.com", "c@mail.com"),
            Arrays.asList("Alex", "c@mail.com", "d@mail.com")
        );

        System.out.println("Test 2 - Valid");
        System.out.println(
            "Expected: [[Alex, a@mail.com, b@mail.com, c@mail.com, d@mail.com]]"
        );
        System.out.println("Actual:   " + accountsMerge(test2));
        System.out.println();

        // Test 3 - Edge: single account with one email.
        List<List<String>> test3 = Arrays.asList(
            Arrays.asList("John", "john@mail.com")
        );

        System.out.println("Test 3 - Edge");
        System.out.println("Expected: [[John, john@mail.com]]");
        System.out.println("Actual:   " + accountsMerge(test3));
        System.out.println();

        // Test 4 - Edge: same name but no shared emails.
        List<List<String>> test4 = Arrays.asList(
            Arrays.asList("John", "john1@mail.com"),
            Arrays.asList("John", "john2@mail.com")
        );

        System.out.println("Test 4 - Edge");
        System.out.println("Expected: 2 separate John accounts");
        System.out.println("Actual:   " + accountsMerge(test4));
        System.out.println();

        // Test 5 - Invalid: null input.
        List<List<String>> test5 = null;

        System.out.println("Test 5 - Invalid");
        System.out.println("Expected: []");
        System.out.println("Actual:   " + accountsMerge(test5));
    }
	
	
	/*
	  Thought Process

	  A natural approach is to think of every email as a graph node
	  and connect emails that appear in the same account.

	  Then the problem becomes finding connected components.

	  DFS would work, but it requires explicitly building an adjacency list.

	  Union-Find gives us the same connected-component grouping
	  without storing all graph edges, and the implementation is
	  still manageable in an interview.

	  So the approach is:
	  - Union emails that appear in the same account.
	  - Then group emails by their final root.
	*/


	/*
	  Interview Explanation Before Coding

	  I'm going to treat each unique email as an identity node.

	  Within one account, all emails belong to the same person,
	  so I'll union the first email with each remaining email.

	  I'll maintain:
	  - parent: maps each email to its parent for Union-Find.
	  - emailToName: maps each email to its account name.

	  After processing all accounts, I'll call find() on every email.

	  Emails that resolve to the same root belong to the same
	  merged account.

	  I'll then:
	  - Group the emails by their root.
	  - Sort the emails within each group.
	  - Prepend the corresponding account name.
	  - Return all merged accounts.

	  Union-Find is a good fit because the core problem is finding
	  connected components created by shared emails.
	*/


	/*
	  Complexity Analysis

	  Let:
	  N = total number of email occurrences across all accounts.
	  E = number of unique emails.

	  Time Complexity:
	  O(N * alpha(E) + E log E)

	  - Union-Find operations take approximately O(N * alpha(E)).
	  - alpha(E) is the inverse Ackermann function and is effectively constant.
	  - Sorting emails across the merged groups takes up to O(E log E).

	  Practically, we can think of the complexity as:
	  O(N + E log E)

	  Space Complexity:
	  O(E)

	  We store:
	  - Union-Find parent map.
	  - Email-to-name map.
	  - Grouped emails.
	*/


	/*
	  Step-by-Step Algorithm

	  1. Create two maps:

	     parent:
	       email -> parent email

	     emailToName:
	       email -> account name


	  2. For every account:

	     - Take the first email as the representative email.

	     - Initialize every email in the Union-Find structure.

	     - Store the account name for each email.

	     - Union every other email with the first email.

	     Example:

	       John -> a@mail.com, b@mail.com, c@mail.com

	       Union:
	       a@mail.com <-> b@mail.com
	       a@mail.com <-> c@mail.com


	  3. Create a grouping map:

	       root email -> list of emails


	  4. For every unique email:

	     - Call find(email) to get its final root.

	     - Add the email to the list belonging to that root.

	     Example:

	       find(a@mail.com) -> a@mail.com
	       find(b@mail.com) -> a@mail.com
	       find(c@mail.com) -> a@mail.com

	     So they are grouped together:

	       a@mail.com -> [a@mail.com, b@mail.com, c@mail.com]


	  5. For every group:

	     - Sort the emails.

	     - Find the corresponding account name.

	     - Add the account name at index 0.

	     Example:

	       [a@mail.com, b@mail.com, c@mail.com]

	     becomes:

	       [John, a@mail.com, b@mail.com, c@mail.com]


	  6. Return all merged accounts.
	*/
	
	public static List<List<String>> accountsMerge(List<List<String>> accounts) {
		if(accounts == null) 
			return new ArrayList<List<String>>();
		
		
		Map<String, String> parent = new HashMap<>();
        Map<String, String> emailToName = new HashMap<>();
        
        // Build Union-Find.
        for(List<String> account : accounts) {
        	String name = account.get(0);
        	String firstEmail = account.get(1);
        	
        	parent.putIfAbsent(firstEmail, firstEmail);
        	emailToName.putIfAbsent(firstEmail, name);
        	
        	for(int i=2; i<account.size(); i++) {
        		String email = account.get(i);
        		
        		parent.putIfAbsent(email, email);
        		emailToName.putIfAbsent(email, name);
        		
        		// All emails in this account belong together.
        		union(firstEmail, email, parent);
        		
        	}
        }
        
        // Group emails by their final root.
        Map<String, List<String>> groups = new HashMap<>();
        
        for(String email : parent.keySet()) {
        	String root = find(email, parent);
        	
        	groups.computeIfAbsent(root, k -> new ArrayList<>()).add(email);
        }

        List<List<String>> result = new ArrayList<>();
        
        // Build final merged accounts.
        for(Map.Entry<String, List<String>> entry : groups.entrySet()) {
        	String root = entry.getKey();
        	List<String> emails = entry.getValue();
        	
        	Collections.sort(emails);
        	
        	List<String> merged = new ArrayList<>();
        	merged.add(emailToName.get(root));
        	merged.addAll(emails);
        	
        	result.add(merged);
        }
        
        return result;
	}
	
	// "find() gives me the ultimate group leader, 
	// and path compression makes future finds faster."
	// walk upward until I find the leader of this email's group.
	private static String find(String email, Map<String, String> parent) {
		if(!parent.get(email).equals(email)) {
			parent.put(
				email, 
					// Starting from this email, keep following 
					// parent pointers until I reach the top/root.
					find(parent.get(email), parent));
		}
		
		return parent.get(email);
	}
	
	private static void union(String email1, String email2, 
			Map<String, String> parent) {
		
		String root1 = find(email1, parent);
		String root2 = find(email2, parent);
		
		if(!root1.equals(root2)) {
			parent.put(root2, root1);
		}
	}
	
}
