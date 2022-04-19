package Gusto;

public class AddBoldTagInString_616_Medium {

	public static void main(String[] args) {
		
		String s = "abcxyz123";
		String[] words = {"abc","123"};
		
		//System.out.println("Expected: <b>abc</b>xyz<b>123</b>  \nActual: " + addBoldTag(s, words));
		System.out.println("Expected: <b>abc</b>xyz<b>123</b>  \nActual: " + addBoldTag2(s, words));
	}

	
	// Time: O(length of s * length of words arr * length of each word in starts with)
	// Space: O(length of s)
	public static String addBoldTag(String s, String[] words) {
        boolean[] bold = new boolean[s.length()];
        
        for(int i=0, end=0; i<s.length(); i++) {
        	for(String word : words) {
        		if(s.startsWith(word,  i)) {
        			end = Math.max(end, i+word.length()); 
        		}
        	}
        	
        	// If end > i, meaning character at position i is within 
        	// the current continuous bold characters, we mark it as bold.
        	bold[i] = end > i;
        	
        	/* Another way we can set the value to true
        	int j = i;
        	while(end > j) {
        		bold[i] = true;
        		j++;
        	}
        	*/
        	
        }
        
        StringBuilder res = new StringBuilder();
        
        for(int i=0; i<s.length(); i++) {
        	if(!bold[i]) {
        		res.append(s.charAt(i));
        		continue;
        	}
        	
        	int j = i;
        	while(j < s.length() && bold[j])
        		j++;
        	
        	res.append("<b>")
        	.append(s.substring(i, j))
        	.append("</b>");
        	
        	i = j-1;
        }
        
        return res.toString();
    }
	
	
	// Time: O(length of s * length of words arr * length of each word in starts with)
	// Space: O(length of s)
	public static String addBoldTag2(String s, String[] dict) {
        StringBuilder sb = new StringBuilder();
        int lastLog = 0;
        int end = -1;

        for (int start = 0; start < s.length(); start++) {
            for (String d : dict) {
                if (s.startsWith(d, start)) {
                    end = Math.max(end, start + d.length());
                }
            }
            if (start == end) {
                sb.append("<b>" + s.substring(lastLog, start) + "</b>");
            }
            if (start >= end) {
                sb.append(s.charAt(start));
                lastLog = start + 1;
            } 
        }
        if (end >= s.length()) {
            sb.append("<b>" + s.substring(lastLog) + "</b>");
        }
        return sb.toString();
    }
}
