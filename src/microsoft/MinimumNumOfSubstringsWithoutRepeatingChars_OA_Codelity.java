package microsoft;

import java.util.HashSet;
import java.util.Set;

public class MinimumNumOfSubstringsWithoutRepeatingChars_OA_Codelity {

	public static void main(String[] args) {
		System.out.println("Expected: 1, Actual: " + minNumber("world") + "\n");
		System.out.println("Expected: 4, Actual: " + minNumber("dddd") + "\n");
		System.out.println("Expected: 2, Actual: " + minNumber("abba") + "\n");
		System.out.println("Expected: 2, Actual: " + minNumber("cycle") + "\n");
		System.out.println("Expected: 4, Actual: " + minNumber("dddde"));

		System.out.println("Expected: 2, Actual: " + subStringsWithNoRepeat("cycle"));
		System.out.println("Expected: 4, Actual: " + subStringsWithNoRepeat("dddde"));
		System.out.println("Expected: 5, Actual: " + subStringsWithNoRepeat2("world"));
		System.out.println("Expected: 2, Actual: " + subStringsWithNoRepeat2("cycle"));
		System.out.println("Expected: 4, Actual: " + subStringsWithNoRepeat2("dddde"));
	}

	/*
	 * Given a string s, find the minimum number of substrings you can create
	 * without having the same letters repeating in each substring.
	 * 
	 * E.g world -> 1, as the string has no letters that occur more than once. 
		dddd -> 4, as you can only create substring of each character.
		abba -> 2, as you can make substrings of ab, ba.	
		cycle-> 2, you can create substrings of (cy, cle) or (c, ycle)
	 * 
	 */

	private static int minNumber(String s) {

		int count = 0;
		int i = 0;
		Set<Character> set = new HashSet<>();

		while (i < s.length()) {
			if (set.contains(s.charAt(i))) {
				count++;
				System.out.println(set);
				set.clear();
			}
			set.add(s.charAt(i));
			i++;
		}

		if (!set.isEmpty()) {
			System.out.println(set);
			count++;
		}

		return count;
	}

	//another approach
	public static int subStringsWithNoRepeat(String s) {
		if (s == null || s.isEmpty()) {
			return 0;
		}
		Set set = new HashSet<>();
		int res = 1;
		for (char c : s.toCharArray()) {
			if (set.contains(c)) {
				res++;
				set.clear();
			}
			set.add(c);
		}
		return res;
	}

	//another approach
	public static int subStringsWithNoRepeat2(String s) {
        if (s == null || s.isEmpty()) {
            return 0;
        }
        String temp = "";
        int res = 1;
        for (char c : s.toCharArray()) {
            if (temp.contains(c + "")) {
                res++;
                temp = c + "";
            } else temp += c + "";
        }
        return res;
    }
	
}
