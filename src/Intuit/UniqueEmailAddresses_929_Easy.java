package Intuit;

import java.util.HashSet;
import java.util.Set;

public class UniqueEmailAddresses_929_Easy {

	public static void main(String[] args) {
		String[] emails = {"test.email+alex@leetcode.com",
							"test.e.mail+bob.cathy@leetcode.com",
		                   "testemail+david@lee.tcode.com"};
		
		System.out.println("Expected: 2, Actual: " + numUniqueEmails(emails));
		System.out.println("Expected: 2, Actual: " + numUniqueEmails2(emails));
	}
	
	
	
	public static int numUniqueEmails(String[] emails) {
        // hash set to store all the unique emails
        Set<String> uniqueEmails = new HashSet<>();

        for (String email : emails) {
            // split into two parts local and domain
            String[] parts = email.split("@");

            // split local by '+'
            String[] local = parts[0].split("\\+");

            // remove all '.', and concatenate '@' and append domain
            uniqueEmails.add(local[0].replace(".", "") + "@" + parts[1]);
        }

        return uniqueEmails.size();
    }
	
	/* 
	 * 
	 * Time: O(N*M), where N be the number of the emails and M be the average length of an email.
	 * Space:  O(N*M) : According to leetcode, in the worst case, when all emails are unique, 
	 * we will store every email address given to us in the hash set.
	 */
	public static int numUniqueEmails2(String[] emails) {
        
        Set<String> set = new HashSet<>();
        
        for(String email : emails) {
            int idx = email.indexOf("@");
            String localName = getLocalName(email.substring(0, idx));
            String domain = email.substring(idx); // e.g. @leetcode.com
            set.add(localName + domain);
        }
        //System.out.println(set);
        return set.size();
    }
    
    private static String getLocalName(String email) {
        
        StringBuffer sb = new StringBuffer();
        for(char c : email.toCharArray()) {
            if(c == '.') continue;
            if(c == '+') break;
            
            sb.append(c);
        }
        return sb.toString();
    }

}
