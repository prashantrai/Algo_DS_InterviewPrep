package Apple;

public class StringCompression_443_Medium {

	public static void main(String[] args) {
		
		char[] chars = {'a','a','b','b','c','c','c'};
		// ["a","2","b","2","c","3"]
		System.out.println("Expected: 6, Actual: " + compress(chars)); 
		
		char[] chars2 = {'a'};
		System.out.println("Expected: 1, Actual: " + compress(chars2));
		
		char[] chars3 = {'a','b','b','b','b','b','b','b','b','b','b','b','b'};
		// ["a","b","1","2"]
		System.out.println("Expected: 4, Actual: " + compress(chars3));
		
		

	}
    
	/*
    Time: O(N)
    Space: O(1), in place
    */
    public static int compress(char[] chars) {
        int write = 0; // Position to write compressed characters
        int i = 0;     // Position to read original characters

        while (i < chars.length) {
            char currentChar = chars[i];
            int count = 0;

            // Count occurrences of current character
            while (i < chars.length && chars[i] == currentChar) {
                i++;
                count++;
            }

            // Write the character
            chars[write++] = currentChar;

            // Write count if greater than 1
            if (count > 1) {

                /* Although String.valueOf(count) creates a temporary string, 
                its size is bounded by the maximum possible count, which is 
                limited by the input constraints. Since the number of digits 
                does not grow with input size asymptotically, it is considered 
                O(1) auxiliary space. */    

                String countStr = String.valueOf(count);
                for (int j = 0; j < countStr.length(); j++) {
                    chars[write++] = countStr.charAt(j);
                }
            }

            // Implement below solution only if insisted
            // It achieves true O(1) space
            /*
            if (count > 1) {
                // Calculate number of digits
                int digits = 0;
                int temp = count;
                while (temp > 0) {
                    digits++;
                    temp /= 10;
                }

                // Write digits in correct order (from highest place)
                write += digits;
                temp = count;
                for (int j = write - 1; j >= write - digits; j--) {
                    chars[j] = (char)('0' + (temp % 10));
                    temp /= 10;
                }
            } */

        }

        return write; // New length of compressed array
    }


}
