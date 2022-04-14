package interview;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Fuelx {

	public static void main(String[] args) {
		
		String s = "abcde";
		int minLength = 2;
		int maxLength = 4;
		int maxUnique = 26;
		//getMaxOccurrences(s, minLength, maxLength, maxUnique);
		test2();
	}
	
	public static void test2() {
		
		String s = "abcde";
		int minLength = 2;
		int maxLength = 3;
		int maxUnique = 26;
		
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		
		for (int i=0; i<s.length(); i++) {
			
			for(int j=minLength; j<=maxLength && j<s.length(); j++) {
				System.err.println(map);
				System.out.println("i="+i+", j="+j);
				String s1 = s.substring(i, j);
				if(map.containsKey(s1)) {
					int count = map.get(s1);
					map.put(s1, count);
				} else {
					map.put(s1, 1);
				}
			}
		}
		System.err.println(map);
		
		
	}
	
	static TrieNode root;
    static Set distinctCharacters = new HashSet();    

// Complete the getMaxOccurrences function below.
    //static int getMaxOccurrences(String s, int minLength, int maxLength, int maxUnique) {
    static int getMaxOccurrences(String s, int minLength, int maxLength, int maxUnique) {    
        
        if (s == null) {
            return 0;
        }
        if (minLength < 0 || maxLength < 0 || maxUnique <= 0) {
            return 0;
        }
        
        int n = s.length();
        
        int maxFrequency = 1;
        root = new TrieNode();
        for (int i = 0; i < n; ++i) {
            for (int j = minLength; j <= maxLength && j <= n; ++j) {
                String substring = s.substring(i,j);
                // return -1 if distincts more than m
                int currentFrequency = insertIntoTrie(substring, maxUnique);
                if (currentFrequency == -1) 
                    break;
                maxFrequency = (currentFrequency > maxFrequency) ? currentFrequency : maxFrequency;                 
            }
            minLength += 1;
            maxLength += 1;
        }
        return maxFrequency;

    }

    public static int insertIntoTrie(String word, int m) {
         distinctCharacters.clear();
         TrieNode parent = root;
         for(char c : word.toCharArray()) {
             distinctCharacters.add(c);
             if (distinctCharacters.size() > m) {
                 return -1;
             }
             int i = c - 'a';
             if(parent.next[i] == null) 
                 parent.next[i] = new TrieNode();
             parent = parent.next[i];
         }
         if (parent.word != null) {
            parent.frequency += 1;
         } else {
             parent.word = word;
             parent.frequency = 1;
         }
         return parent.frequency;
     }

     static class TrieNode {
         TrieNode[] next = new TrieNode[26];
         String word;
         int frequency = 0;
     }
	
	
}




