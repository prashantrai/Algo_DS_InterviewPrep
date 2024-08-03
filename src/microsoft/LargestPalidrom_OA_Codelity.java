package microsoft;

public class LargestPalidrom_OA_Codelity {

	public static void main(String[] args) {
		String s = "39878";
		getLargestPallindrome(s);
		
	    System.out.println(maxPalindromeNumber("112"));
	    System.out.println(maxPalindromeNumber("900"));
	    System.out.println(maxPalindromeNumber("000"));
	    System.out.println(maxPalindromeNumber("0000"));
	    System.out.println(maxPalindromeNumber("39878"));
	    System.out.println(maxPalindromeNumber("54321"));
	}

	// https://leetcode.com/discuss/interview-question/398023/Microsoft-Online-Assessment-Questions/1267059
	
	//A solution: 
	// https://leetcode.com/discuss/interview-question/398023/Microsoft-Online-Assessment-Questions/1267059
	
	// idea: https://leetcode.com/discuss/interview-question/398023/Microsoft-Online-Assessment-Questions/1267046
	
	
	// https://leetcode.com/discuss/interview-question/1770045/Microsoft-or-OA-or-MaximumSum/1396524	
	public static String maxPalindromeNumber(String number) {

	    Function < Character, Integer > charToIdx = ch -> ch - '0';
	    Function < Integer, Character > idxToChar = i -> (char)(i + '0');

	    int[] counter = new int[10];

	    for (char ch: number.toCharArray()) {
	      counter[charToIdx.apply(ch)]++;
	    }

	    int maxNumberHavingOddFrequency = -1;
	    for (int i = 0; i < counter.length; ++i) {
	      if (counter[i] % 2 == 1) maxNumberHavingOddFrequency = i;
	    }
	    StringBuilder sb = new StringBuilder();
	    if (maxNumberHavingOddFrequency != -1) sb.append(maxNumberHavingOddFrequency);

	    //Running loop till 1 only to avoid cases like 090, 000
	    for (int i = 9; i >= 1; --i) {
	      int count = counter[i];
	      if (counter[i] == 0) continue;
	      if (counter[i] % 2 == 1) count = counter[i] - 1;

	      for (int j = 0; j < count / 2; ++j) {
	        sb.append(i);
	        sb.insert(0, i);
	      }
	    }
	    return sb.toString();
	  }
	
	
	
	// https://leetcode.com/playground/K7xRiF77
	public static void getLargestPallindrome(String s) {
		int maxSingleInteger = -1;
		StringBuilder result = new StringBuilder();
		PriorityQueue<Pair<Character, Integer>> pq = new PriorityQueue<Pair<Character, Integer>>(
				new CustomComparator());
		Map<Character, Integer> map = new HashMap<Character, Integer>();
		for (char c : s.toCharArray()) {
			int freq = 0;
			if (map.containsKey(c)) {
				freq = map.get(c);
			}
			freq += 1;
			map.put(c, freq);
		}

		for (char key : map.keySet()) {
			if (map.get(key) > 1) {
				pq.add(new Pair(key, map.get(key)));
			} else {
				maxSingleInteger = Math.max(maxSingleInteger, key - '0');
			}
		}

		while (!pq.isEmpty()) {
			Pair<Character, Integer> fp = pq.remove();
			if (fp.getKey() == '0' && result.length() == 0) {
				continue;
			}
			result.insert(0, fp.getKey());
			result.append(fp.getKey());
			if (fp.getValue() - 2 < 2) {
				if (fp.getValue() == 1) {
					maxSingleInteger = Math.max(maxSingleInteger, fp.getKey() - '0');
				}
			} else {
				int val = fp.getValue() - 2;
				pq.add(new Pair(fp.getKey(), val));
			}
		}

		if (maxSingleInteger != -1) {
			result.insert(result.length() / 2, maxSingleInteger);
		}
		System.out.println(String.valueOf(result).trim());
	}

	class CustomComparator implements Comparator<Pair<Character, Integer>> {
		public int compare(Pair<Character, Integer> p1, Pair<Character, Integer> p2) {
			return (Character.compare(p1.getKey(), p2.getKey()));
		}
	}

}
