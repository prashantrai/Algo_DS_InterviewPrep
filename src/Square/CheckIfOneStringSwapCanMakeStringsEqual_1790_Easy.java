package Square;

import java.util.ArrayList;
import java.util.List;

public class CheckIfOneStringSwapCanMakeStringsEqual_1790_Easy {

	public static void main(String[] args) {

		String s1 = "bank", s2 = "kanb";
		System.out.println("Expected: true, Actual: " + areAlmostEqual(s1, s2));
		
		s1 = "attack"; s2 = "defend";
		System.out.println("Expected: false, Actual: " + areAlmostEqual(s1, s2));
	}
	
	public static boolean areAlmostEqual(String s1, String s2) {
        if(s1 == null && s2 == null) return false;
        
        if(s1.length() != s2.length()) return false;
        
        List<Integer> l = new ArrayList<>();
        for (int i = 0; i < s1.length(); i++) { 
            if(s1.charAt(i) != s2.charAt(i)) 
                l.add(i);
            
            if(l.size() > 2) return false;
        }
        
        return l.size() == 0 
            || (l.size() == 2 
                && s1.charAt(l.get(0)) == s2.charAt(l.get(1))
                && s1.charAt(l.get(1)) == s2.charAt(l.get(0)));
    }

}
