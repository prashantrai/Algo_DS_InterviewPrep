package Square;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Square_Phone_Screen_Oct10_2023_Final {

	public static void main(String[] args) {

		String officialDomain = "Square";

		List<String> otherDomains 
		 = List.of("Swuare", "Smuare", "Share", "Google", "Dquare", "Sqjuare", "square",
				"124457");

		// Part 1
		List<String> typoSquats = findTypoSquats(officialDomain, otherDomains);
		System.out.println("Part 1:: Typo Squats found: " + typoSquats);

		// Part 2
		List<String> likelyTypoSquats = findLikelyTypoSquats(officialDomain, otherDomains);
		System.out.println("Part 2:: Likely Typo Squats found: " + likelyTypoSquats);
	}

	// Part 1
	// Method to find typo squats
	public static List<String> findTypoSquats(String officialDomain, List<String> otherDomains) {
		List<String> typoSquats = new ArrayList<>();
		for (String domain : otherDomains) {
			if (isTypoSquat(officialDomain, domain)) {
				typoSquats.add(domain);
			}
		}
		return typoSquats;
	}

	// Part 2
	// Method to find typo squats from a list of other domains
	public static List<String> findLikelyTypoSquats(String officialDomain, List<String> otherDomains) {
		List<String> typoSquats = new ArrayList<>();

		for (String domain : otherDomains) {
			if (isLikelyTypoSquat(officialDomain, domain)) { // Part 2
				typoSquats.add(domain);
			}
		}
		return typoSquats;
	}

	// Part 1
	// Method to check if a domain is a likely typo squat of the official domain
	private static boolean isTypoSquat(String officialDomain, String domain) {
		// Check if the length is the same and only one character is different
		if (officialDomain.length() != domain.length()) {
			return false;
		}
		officialDomain = officialDomain.toLowerCase();
		domain = domain.toLowerCase();

		int diffCount = 0;
		for (int i = 0; i < officialDomain.length(); i++) {
			if (officialDomain.charAt(i) != domain.charAt(i)) {
				diffCount++;
				if (diffCount > 1) {
					return false;
				}
			}
		}

		return diffCount == 1;
	}

	// Part 2
	// Method to check if a domain is a likely typo squat of the official domain
	private static boolean isLikelyTypoSquat(String officialDomain, String domain) { // Part 2
		if (officialDomain.length() != domain.length()) {
			return false;
		}

		int diffCount = 0;
		Map<Character, List<Character>> adjacentKeys = getAdjacentKeys(); // Part 2

		officialDomain = officialDomain.toLowerCase();
		domain = domain.toLowerCase();

		for (int i = 0; i < officialDomain.length(); i++) {
			if (officialDomain.charAt(i) != domain.charAt(i)) {
				diffCount++;
				if (diffCount > 1) {
					return false;
				}
				char originalChar = officialDomain.charAt(i);
				char typoChar = domain.charAt(i);
				// Check if the typo character is an adjacent key of the original character
				if (!adjacentKeys.getOrDefault(originalChar, new ArrayList<>()).contains(typoChar)) { // Part 2
					return false;
				}
			}
		}

		return diffCount == 1;
	}

	// Part 2: Method to get adjacent keys on a QWERTY keyboard

	// During interview start only for given input because building map for all
	// qwerty chars will take time
	// e.g. for square, add adjacent keys (s,q,u,a,r,e)
//     	map.put("s", List.of("a", "d"));
//		map.put("q", List.of("w"));
//		map.put("u", List.of("y", "i"));
//		map.put("a", List.of("s"));
//		map.put("r", List.of("e", "t"));
//		map.put("e", List.of("w", "r"));

	private static Map<Character, List<Character>> getAdjacentKeys() {
		Map<Character, List<Character>> adjacentKeys = new HashMap<>();

		// if original domain is 'square' then only add below
		// to speed up the implementation
		adjacentKeys.put('s', List.of('a', 'd', 'w', 'x'));// only add this in interview
		adjacentKeys.put('q', List.of('w', 'a'));// only add this in interview
		adjacentKeys.put('u', List.of('y', 'i', 'j'));// only add this in interview
		adjacentKeys.put('a', List.of('q', 's', 'z'));// only add this in interview
		adjacentKeys.put('r', List.of('e', 't', 'f'));// only add this in interview
		adjacentKeys.put('e', List.of('w', 'r', 'd'));// only add this in interview

		adjacentKeys.put('q', List.of('w', 'a'));
		adjacentKeys.put('w', List.of('q', 'e', 's'));
		adjacentKeys.put('e', List.of('w', 'r', 'd'));
		adjacentKeys.put('r', List.of('e', 't', 'f'));
		adjacentKeys.put('t', List.of('r', 'y', 'g'));
		adjacentKeys.put('y', List.of('t', 'u', 'h'));
		adjacentKeys.put('u', List.of('y', 'i', 'j'));
		adjacentKeys.put('i', List.of('u', 'o', 'k'));
		adjacentKeys.put('o', List.of('i', 'p', 'l'));
		adjacentKeys.put('p', List.of('o'));
		adjacentKeys.put('a', List.of('q', 's', 'z'));
		adjacentKeys.put('s', List.of('a', 'd', 'w', 'x'));
		adjacentKeys.put('d', List.of('s', 'f', 'e', 'c'));
		adjacentKeys.put('f', List.of('d', 'g', 'r', 'v'));
		adjacentKeys.put('g', List.of('f', 'h', 't', 'b'));
		adjacentKeys.put('h', List.of('g', 'j', 'y', 'n'));
		adjacentKeys.put('j', List.of('h', 'k', 'u', 'm'));
		adjacentKeys.put('k', List.of('j', 'l', 'i'));
		adjacentKeys.put('l', List.of('k', 'o'));
		adjacentKeys.put('z', List.of('a', 's', 'x'));
		adjacentKeys.put('x', List.of('z', 'c', 's'));
		adjacentKeys.put('c', List.of('x', 'v', 'd'));
		adjacentKeys.put('v', List.of('c', 'b', 'f'));
		adjacentKeys.put('b', List.of('v', 'n', 'g'));
		adjacentKeys.put('n', List.of('b', 'm', 'h'));
		adjacentKeys.put('m', List.of('n', 'j'));

		return adjacentKeys;
	}

	
	// Solution ends here. 
	// Below are just another variations with better runtime
	
	////////////////////////////////////////////////////////////////////////////
	/**
	 * Better time and space 1. Precompute possible type squat for a domain and add
	 * each to a Set. 2. Then, we can simply check if each domain in the list is in
	 * this set.
	 * 
	 * This will bring: Time: Generating typo squats: - O(m⋅25) where m is the
	 * length of the domain (constant factor of 25 for the alphabet). - Checking
	 * each domain: O(n) where n is the number of other domains.
	 * 
	 * Space: O(m*25),
	 */

	// Method to generate all possible typo squats for a given domain
	private static Set<String> generateTypoSquats2(String officialDomain) {
		Set<String> typoSquats = new HashSet<>();
		char[] domainArray = officialDomain.toCharArray();

		for (int i = 0; i < domainArray.length; i++) {
			char originalChar = domainArray[i];
			for (char c = 'a'; c <= 'z'; c++) {
				if (c != originalChar) {
					domainArray[i] = c;
					typoSquats.add(new String(domainArray));
				}
			}
			domainArray[i] = originalChar; // Restore original character
		}
		return typoSquats;
	}

	// Method to find typo squats from a list of other domains
	public static List<String> findTypoSquats2(String officialDomain, List<String> otherDomains) {
		Set<String> typoSquatsSet = generateTypoSquats2(officialDomain);
		List<String> detectedTypoSquats = new ArrayList<>();

		for (String domain : otherDomains) {
			if (typoSquatsSet.contains(domain)) {
				detectedTypoSquats.add(domain);
			}
		}
		return detectedTypoSquats;
	}

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
 * Square -> Dquare Square - sq
 * 
 * Part 2: Update the tool so that it can restrict the changed character to only
 * be typos that are “likely” given the layout of the keyboard.
 * 
 * Ex. Given “Square” as our domain, we should detect "Swuare" but not "Smuare"
 * (assuming a standard QWERTY keyboard).  Q -> W is likely while Q -> M is not.
 * 
 * 
 */
