package Motive;
import java.util.*;

public class RestoreIPAddresses_93_Medium {

	/*
	 * "I'll use backtracking and build the IP address one segment at a time. At
	 * each position, I'll try taking one, two, or three digits because an IP
	 * segment can't contain more than three digits. 
	 * 
	 * Before recursing, I'll validate that segment. A multi-digit segment 
	 * can't start with zero, and its numeric value must be at most 255. 
	 * 
	 * I'll keep track of how many segments I've created. Once I have exactly 
	 * four segments, I'll add the result only if I've also consumed the 
	 * entire input string. 
	 * 
	 * I can also prune based on the remaining character count. If I have 
	 * k segments left, I need between k and 3k characters remaining. 
	 * 
	 * If that's impossible, there's no reason to continue
	 * that branch."
	 */
	
	/*
	 Step-by-Step Algorithm
		1. Start backtracking from index 0 with 0 segments.
		2. Calculate how many characters and segments remain.
		3. Stop if the remaining characters are too few or too many.
		4. If we've created 4 segments:
		   - Add the address only if we've consumed the whole string.
		5. Try segment lengths 1, 2, and 3.
		6. Stop if:
		   - The segment would exceed the string.
		   - A multi-digit segment starts with 0.
		7. Build the numeric value.
		8. Stop once the value exceeds 255.
		9. Add the segment to the current path.
		10. Recurse for the next segment.
		11. Remove the segment when returning.
	 * */
	
	/* Complexity Analysis: 
		Time Complexity
			Effectively O(1) for IPv4 constraints.
			Why?
			There are only:
				- 4 segments
				- At most 3 choices per segment
			So the search tree is bounded by approximately:
			3^4 = 81
			
			If we describe it in terms of input length n, the work is roughly:
			O(3^4)
			
			because valid IPv4 input can never produce useful branches beyond 12 characters.
		
		Space Complexity:  O(1)
		
		excluding the returned results.
		The recursion depth is at most 4.
	 * */
	
    public static List<String> restoreIpAddresses(String s) {
        List<String> result = new ArrayList<>();

        if (s == null || s.length() < 4 || s.length() > 12) {
            return result;
        }

        List<String> parts = new ArrayList<>();
        backtrack(s, 0, parts, result);

        return result;
    }

    private static void backtrack(String s,
                                  int index,
                                  List<String> parts,
                                  List<String> result) {

        int remainingChars = s.length() - index;
        int remainingParts = 4 - parts.size();

        // Interview script:
        // Each remaining IP part needs at least 1 and at most 3 digits.
        if (remainingChars < remainingParts ||
            remainingChars > remainingParts * 3) {
            return;
        }

        // Interview script:
        // We need exactly 4 parts and must consume the entire string.
        if (parts.size() == 4) {
            if (index == s.length()) {
                result.add(String.join(".", parts));
            }
            return;
        }

        int value = 0;

        // Interview script:
        // Try taking 1, 2, or 3 digits for the next IP segment.
        for (int end = index; end < s.length() && end < index + 3; end++) {

            // Interview script:
            // A multi-digit segment cannot start with zero.
            if (end > index && s.charAt(index) == '0') {
                break;
            }

            value = value * 10 + (s.charAt(end) - '0');

            // Interview script:
            // IP segments must be in the range 0 to 255.
            if (value > 255) {
                break;
            }

            parts.add(s.substring(index, end + 1));

            backtrack(s, end + 1, parts, result);

            // Backtrack so we can try another segment length.
            parts.remove(parts.size() - 1);
        }
    }

    public static void main(String[] args) {

        runTest(
            "Test 1 - Normal",
            "25525511135",
            Arrays.asList(
                "255.255.11.135",
                "255.255.111.35"
            )
        );

        runTest(
            "Test 2 - All zeros",
            "0000",
            Arrays.asList(
                "0.0.0.0"
            )
        );

        runTest(
            "Test 3 - Normal with zeros",
            "101023",
            Arrays.asList(
                "1.0.10.23",
                "1.0.102.3",
                "10.1.0.23",
                "10.10.2.3",
                "101.0.2.3"
            )
        );

        runTest(
            "Test 4 - Minimum length",
            "1111",
            Arrays.asList(
                "1.1.1.1"
            )
        );

        runTest(
            "Test 5 - Invalid / too short",
            "123",
            Collections.emptyList()
        );

        runTest(
            "Test 6 - Segment greater than 255",
            "99999999",
            Collections.emptyList()
        );
    }

    private static void runTest(String name,
                                String input,
                                List<String> expected) {

        List<String> actual = restoreIpAddresses(input);

        Collections.sort(expected);
        Collections.sort(actual);

        System.out.println(name);
        System.out.println("Input    : " + input);
        System.out.println("Expected : " + expected);
        System.out.println("Actual   : " + actual);
        System.out.println();
    }
}