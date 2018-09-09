package ctci.ch1.arr.and.str;

public class CheckPermutationDemo {
	/**
		EXAMPLE
		Input: Tact Coa
		Output: True (permutations: "taco cat", "atco eta", etc.)
	*/

	public static void main(String[] args) {
			
		System.out.println("CheckPermutationDemo...");
		//isPermutation("hannahb", "hannaha");
		checkPermutaionByCharFrequency("abcbae", "abcbad");
		//checkPermutaionByCharFrequency("Tact Coa", "Taco Cat");
		isPermutation("Tact Coa", "taco cat");
		
	}
	
	/**
	 * Check frequency of each char input string and then compare array, we can also use HashMap here.
	 * 
	 * If the frequency of chars in array < 0, that means that cahr was not part of the first string and we can return 
	 * "Not Permutation"  
	 * 
	 * Because in case if Permutaion string we'll have a positive number or ZERO at array index
	 * */
	
	public static void checkPermutaionByCharFrequency(String s1, String s2) {
		
		int[] s1_arr = new int[128]; //--assuming we have no special character in case of special char we can make array size 256
		
		System.out.println("s1_arr: " + deepToStringIntArr(s1_arr));
		
		if(s1.length() != s2.length()) {
			System.out.println(">> Not Permutation");
			return;
		}
		
		for (int i=0; i<s1.length(); i++) {
			s1_arr[s1.charAt(i)]++;
		}
		
		System.out.println(">>s1_arr: " + deepToStringIntArr(s1_arr));
		
		for (int i=0; i<s2.length(); i++) {
			
			/*if(s1_arr[i] == 0) {
				System.out.println("Not Permutation");
				return;
			}*/
			s1_arr[s2.charAt(i)]--;
			
			if(s1_arr[s2.charAt(i)] == -1) {
				System.out.println("Not Permutation");
				return;
			}
		}
		
		System.out.println(">> Permutation");
	}
	
	
	/**
	 * Sort both the string and then compare, if equals then TRUE
	 * 
	 * We used merge sort here
	 * */
	
	public static void isPermutation (String s1, String s2) {
		
		//sort both the strings and check if they are equal
		
		char[] str1 = doMergeSort(s1.toCharArray());
		char[] str2 = doMergeSort(s2.toCharArray());
		
		System.out.println(" >>str1 :: "+ deepToStringCharArr(str1));
		System.out.println(" >>str2 :: "+ deepToStringCharArr(str2));
		
		s1 = new String(str1);
		s2 = new String(str2);

		System.out.println("s1= "+s1+", s2="+s2);
		
		if(s1.equals(s2)) {
			System.out.println("Permutation");
		} else {
			System.out.println("Not Permutation");
		}
	}
	
	
	private static char[] str;
	private static char[] helper;
	
	public static char[] doMergeSort(char[] str) {
		
		System.out.println("\n\n\n >>Input :: "+ deepToStringCharArr(str));
		
		CheckPermutationDemo.str = str;
		helper = new char[str.length];
		
		return mergeSort(0, str.length-1);
	}
	
	public static char[] mergeSort(int low, int high) {
		
		if(low<high) {
			int mid = (low+high)/2;
	
			System.out.println(">> low="+low+", mid="+mid);
			mergeSort(low, mid);
			
			System.out.println(">> (mid+1)="+(mid+1)+", high="+high);
			mergeSort(mid+1, high);
			
			return merge(low, mid, high);
		}
		return null;
	}
	
	public static char[] merge (int low, int mid, int high) {
		System.out.println(">> low="+low+", mid="+mid+", high="+high);
	
		System.out.println("1.0>> helper :: "+ deepToStringCharArr(helper));
		System.out.println("2.0>> str :: "+ deepToStringCharArr(str));
		
		for(int i=0; i<=high; i++) {
			helper[i] = str[i];
		}
		System.out.println("1.1>> helper :: "+ deepToStringCharArr(helper));
		System.out.println("2.1>> str :: "+ deepToStringCharArr(str));
		
		int i = low;
		int j = mid+1;
		int k = low;
		
		System.out.println(">>> low="+low+", mid="+mid+", high="+high);
		
		while(i <= mid && j<=high) {

			if(helper[i] > helper[j]) {
				str[k] = helper[j];
				j++;
			} else {
				str[k] = helper[i];
				i++;
			}
			k++;
		}
		
		System.out.println("3.0>> helper :: "+ deepToStringCharArr(helper));
		System.out.println("4.0>> str :: "+ deepToStringCharArr(str));
 		
		while(i<=mid) {
			str[k] = helper[i];
			i++;
			k++;
		}
 		
		System.out.println("=>merged = "+ deepToStringCharArr(str));
		
		return str;
	}
	
	public static String deepToStringCharArr(char[] str) {
		
		String s = "[ ";
		for (char c : str) {
			s += c+", ";
		}
		s = s.substring(0, s.length()-2);
		s+="]";
		return s;
	}
	
	public static String deepToStringIntArr(int[] arr) {
		
		String s = "[ ";
		for (int i : arr) {
			s += i+", ";
		}
		s = s.substring(0, s.length()-2);
		s+="]";
		return s;
	}
	

}
