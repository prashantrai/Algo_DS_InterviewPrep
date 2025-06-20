package leetcode;

public class ReformatPhoneNumber_1694_Easy {

	public static void main(String[] args) {
        System.out.println(reformatNumber("1-23-45 6"));    // Output: "123-456"
        System.out.println(reformatNumber("123 4-567"));    // Output: "123-45-67"
        System.out.println(reformatNumber("123 4-5678"));   // Output: "123-456-78"
    }

	// Time: O(N)
    // Space: O(N)
    public static String reformatNumber(String number) {
        String s = removeSpecialChar(number);
        int len = s.length();
        
        if(len < 4) return s;
        
        // Group into blocks of 3 until 4 or fewer digits remain
        StringBuilder res = new StringBuilder();
        int i=0;
        
        while(len - i > 4) {
            res.append(s.substring(i, i+3)).append("-");
            i += 3;
        }
        
        // Handle the remaining 2, 3, or 4 digits
        if(len - i == 4) {
            res.append(s.substring(i, i+2));
            res.append("-");
            res.append(s.substring(i+2));
        } else {
            res.append(s.substring(i));
        }
        
        return res.toString();
    }
    
    // replace all "-" and spaces with empty string
    private static String removeSpecialChar(String s) {
        // s = s.replace("-","").replace(" ","");
        return s.replaceAll("[- ]", "");
    }
    
    // Not in use: another approaceh to remove special chars
    private static String removeSpecialChar2(String number) {
        // Step 1: Remove all spaces and dashes
        StringBuilder digits = new StringBuilder();
        for (char c : number.toCharArray()) {
            if (Character.isDigit(c)) {
                digits.append(c);
            }
        }
        return digits.toString();
    }
	
}
