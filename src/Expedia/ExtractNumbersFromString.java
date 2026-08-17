package Expedia;

import java.util.ArrayList;
import java.util.List;

public class ExtractNumbersFromString {

	public static void main(String[] args) {

        String[] input = {
            "A2D",
            "1B",
            "3F5",
            "67FE2",
            "AA"
        };

        System.out.println(extractNumbers(input));


        // Additional test cases

        String[] test1 = {
            "ABC",          // no numbers
            "123",          // only number
            "A001B",        // leading zeros
            "X9Y88Z"
        };

        System.out.println(extractNumbers(test1));
        // Output: [123, 1, 9, 88]


        String[] test2 = {
            "5A6B7",
            "",
            "100XYZ200"
        };

        System.out.println(extractNumbers(test2));
        // Output: [5, 6, 7, 100, 200]


        String[] test3 = {
            "999999999"
        };

        System.out.println(extractNumbers(test3));
        // Handles large continuous number (within Integer range)
    }
	
	
	/*
	You are given an array of strings containing letters and digits.
	
	Extract all numbers appearing inside each string.
	
	Return the list of numbers in the order they appear.
	
	Example
	
	Input:
	[
	"A2D",
	"1B",
	"3F5",
	"67FE2",
	"AA"
	]
	
	Output:
	[
	2,
	1,
	3,
	5,
	67,
	2
	]
	*/
	
	// Time: O(N)
	// Space: O(1)
	public static List<Integer> extractNumbers(String[] arr) {
		
		List<Integer> result = new ArrayList<>();
		
		for(String s : arr) {
			
			int num = 0;
			boolean hasNumber = false;
			
			for(char c : s.toCharArray()) {
				// Build number when we see a digit
				if(Character.isDigit(c)) {
					num = 10 * num + (c - '0');
					hasNumber = true;
				}
				else {
					// Number ended, store it
					if(hasNumber) {
						result.add(num);
						num = 0;
						hasNumber = false;
					}
					
				}
			}
			// Handle number at the end of string
			if(hasNumber) {
				result.add(num);
			}
			
		}
		return result;
	}

}

