package Square;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Square_Phone_Screen_Oct10_2022 {

	public static void main(String[] args) {

//		List<String> input = Arrays.asList("Swuare", "Smuare", "Share", "Google", "Ssquare", "square", "124457");
//
//		String official = "Square";
//
//		List<String> res = getSquats(official, input);
//		System.out.println("Res:: " + res);
//
//		List<String> input2 = Arrays.asList("Swuare", "Smuare");
//		buildKeyMap();
//		List<String> res2 = getTypos(official, input2);
//		System.out.println("Res2:: " + res2);
		
		// chatGPT
		String officialDomain = "Square";
		List<String> otherDomains = new ArrayList<>();
        otherDomains.add("Swuare");
        otherDomains.add("Smuare");
        otherDomains.add("Share");
        otherDomains.add("Google");
        otherDomains.add("Dquare");
        otherDomains.add("Sqjuare");
        otherDomains.add("Ssquare");
        otherDomains.add("square");
        otherDomains.add("124457");

        List<String> typoSquats = getSquats(officialDomain, otherDomains);
        System.out.println("Typo Squats found: " + typoSquats);
		
		
		

	}

	/*
	 * Build a tool that, when given an official domain and a list of other domains,
	 * alerts the user to all the possible typo squats of the official domain that
	 * are present in the list of other domains.
	 * 
	 * Typo squatting is where a malicious attacker buys domain names that are one
	 * typo off from a domain that their target owns, in hopes that users will make
	 * a mistake and be redirected to the attacker’s site instead.”
	 * 
	 * Part 1: The first kind of typo we want to detect (there will be more later)
	 * are typos such that a single character from our domain has been changed into
	 * a different character. 
	 * 
	 * Ex. Given “Square” as one of our domains, we should detect "Swuare" and
	 * "Smuare" but not "Share" or "Google". 
	 * 
	 * Square -> Dquare
	 * Square - sq
	 * 
	 * Part 2: Update the tool so that it can restrict the changed character to only
	 * be typos that are “likely” given the layout of the keyboard.
	 * 
	 * Ex. Given “Square” as our domain, we should detect "Swuare" but not "Smuare"
	 * (assuming a standard QWERTY keyboard).  Q -> W is likely while Q -> M is not.
	 */

	private static List<String> getSquats(String offcial, List<String> domains) {

		List<String> res = new ArrayList<>();
		for (String d : domains) {
			d = d.toLowerCase();
			offcial = offcial.toLowerCase();
			if (d.length() != offcial.length())
				continue;
			if (helper(d, offcial)) {
				res.add(d);
			}
		}

		return res;

	}

	private static List<String> getTypos(String offcial, List<String> domains) {

		List<String> res = new ArrayList<>();
		for (String d : domains) {
			d = d.toLowerCase();
			offcial = offcial.toLowerCase();
			if (d.length() != offcial.length())
				continue;
			if (isTypo(d, offcial)) {
				res.add(d);
			}
		}

		return res;
	}

	final static Map<String, List<String>> map = new HashMap<>();

	private static boolean isTypo(String d, String offcial) {

		List<Integer> diff = new ArrayList<>();

		for (int i = 0; i < d.length(); i++) {
			if (d.charAt(i) != offcial.charAt(i)) {
				diff.add(i);
			}
		}

		if (diff.size() == 1) {

			String s1 = "" + offcial.charAt(diff.get(0));
			String s2 = "" + d.charAt(diff.get(0));

			System.out.println("s1=" + s1);
			System.out.println("s2=" + s2);

			if (map.containsKey(s1)) {
				List<String> l = map.get(s1);
				if (!l.contains(s2))
					return false;
			}

			return true;
		}

		return false;

	}

	/*
	 * Build this map where key is the character from the word and values are list of adjacent
	 * characters that could be in typos
	 */
	private static void buildKeyMap() {
		map.put("s", Arrays.asList("a", "d"));
		map.put("q", Arrays.asList("w"));
		map.put("u", Arrays.asList("y", "i"));
		map.put("a", Arrays.asList("s"));
		map.put("r", Arrays.asList("e", "t"));
		map.put("e", Arrays.asList("w", "r"));
	}

	// Leetcode 859: refer BuddyStrings_859_Easy.java
	private static boolean helper(String d, String offcial) {
//		List<Integer> diff = new ArrayList<>();
		int diffCount = 0;

		for (int i = 0; i < d.length(); i++) {
			if (d.charAt(i) != offcial.charAt(i)) {
				//diff.add(i);
				diffCount++;
				if (diffCount > 1) { // optimized/tuned
                    return false;
                }
			}
		}

//		return diff.size() == 1;
		return diffCount == 1;

	}

}




 
