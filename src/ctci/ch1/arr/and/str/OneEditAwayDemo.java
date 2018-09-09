package ctci.ch1.arr.and.str;

public class OneEditAwayDemo {

	public static void main(String[] args) {

		String s1 = "pale", s2 = "bale";
		boolean oneEditAway = isOneEditAway(s1, s2);
		//System.out.println(">>Is one REPLACE away: "+ oneEditAway);
		
		s1 = "ple"; s2 = "pale";
		oneEditAway = isOneEditAway(s1, s2);
//		System.out.println(">>Is one INSERT away: "+ oneEditAway);

		//s1 = "ple"; s2 = "pale";		
		//s1 = "apple"; s2 = "aple";
		oneEditAway = isOneEditAway(s2, s1);
		//System.out.println(">>Is one DELETE away: "+ oneEditAway);

		
		//--Solution 2 : Single method
		s1 = "pale"; s2 = "bale";  //--One REPLACE away
		oneEditAway = isOneEditAway_Sol2(s1, s2);
		System.out.println(">>SOL_2 :: Is one REPLACE away: "+ oneEditAway);
		
		s1 = "ble"; s2 = "bale";  //--One INSERT away
		oneEditAway = isOneEditAway_Sol2(s1, s2);
		System.out.println(">>SOL_2 :: Is one INSERT away: "+ oneEditAway);
		
		
		//-- We can use INSERT logic to check the One Delete Away. If and s2 needs only element to become s1
		//--it means if we remove one char which is missing in s2, then s1 will be equal to s2
		s1 = "bale"; s2 = "ale";  //--One DELETE away
		oneEditAway = isOneEditAway_Sol2(s2, s1); 
		System.out.println(">>SOL_2 :: Is one DELETE away: "+ oneEditAway);
	}

	//--REPLACE :  s1 = "pale", s2 = "bale";
	//--INSERT  :  s1 = "ple", s2 = "pale";
	//--DELETE  :  s1 = "bale", s2 = "ble";
	private static boolean isOneEditAway_Sol2(String s1, String s2) {
		
		boolean isOneEditAway = false;
		int index1 = 0, index2 = 0;
		
		if(Math.abs(s1.length() - s2.length()) > 1 || s1.equals(s2)) {
			return false;
		}
		
		while (index1 < s1.length() && index2 < s2.length()) {
			
			if(s1.charAt(index1) != s2.charAt(index2)) {
				
				if(isOneEditAway) {
					return false;
				}
				isOneEditAway = true;
				
				if (s1.length() == s2.length()) { //replace
					index1++;
				}
				
			} else {
				index1++;
				/*if (s1.length()-1 == s2.length()) { //delete
					index2++;
				}*/
			}
			index2++;
		} 
		
		return true;
		
	}
	
	/**
	 * s1 = input string
	 * s2 = main string : input to be compare with
	 * 
	 * EXAMPLE
		pale, ple -> true
		pales, pale -> true
		pale, bale -> true
		pale, bae -> false
	 * 
	 * */
	private static boolean isOneEditAway (String s1, String s2) {
		
		if(s1.length() == s2.length()) {
			
			return oneEditReplace(s1, s2);
			
		} else if(s1.length()+1 == s2.length()) { 
			
			return oneEditInsert(s1, s2);
			
		} else if(s1.length()-1 == s2.length()) {
			
			return oneEditDelete(s1, s2);
		}
		
		return false;
	}

	//INSERT => s1 = ple, s2 = pale -> true
	//DELETE => s1 = pale, s2 = ple -> true ; s1=apple, s2=aple
	private static boolean oneEditInsert(String s1, String s2) {
		
		int s1_index = 0;
		int s2_index = 0;
		
		while (s1_index < s1.length() && s2_index < s2.length()) {
			
			if(s1.charAt(s1_index) != s2.charAt(s2_index)) {
				
				if(s1_index != s2_index) {
					return false;
				}
				s2_index++;
				
			} else {
				s1_index++;
				s2_index++;
			}
			
		}
		
		return true;
	}
	
	private static boolean oneEditDelete(String sl, String s2) {
		int index1 = 0;
		int index2 = 0;
		while (index2 < s2.length() && index1 < sl.length()) {
			if (sl.charAt(index1) != s2.charAt(index2)) {
				if (index1 != index2) {
					return false;
				}
				index1++;
			} else {
				index1++;
				index2++;
			}
		}
		return true;
	}
	

	//pale, bale -> true
	private static boolean oneEditReplace(String s1, String s2) {

		boolean oneReplaceAway = false;
		for (int i=0; i<s2.length(); i++) {
			
			if(s1.charAt(i) != s2.charAt(i)) {
				
				if(oneReplaceAway) {
					return false;
				}
				oneReplaceAway = true;
			}
			
		}
		
		return oneReplaceAway;
	}

	
	
}
