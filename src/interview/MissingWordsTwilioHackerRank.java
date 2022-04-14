package interview;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MissingWordsTwilioHackerRank {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String s = "I am using hackerrank to improve programming";
		String t = "am hackerrank to improve";
		missingwords3(s, t);

	}
	
	
	//--working - find 4ht significant bit
	public static int fourthBit(int n) {
        List<Integer> binaryNum = new ArrayList<>();

        int i = 0;
        while (n > 0) {
            int element = n % 2;
            binaryNum.add(i, element);
            n = n / 2;
            i++;
        }

        System.out.println(binaryNum);

        return binaryNum.get(binaryNum.size()-4);

    }

	
	public static List<String> missingwords3(String s, String t) {

		String[] a = s.split(" ");
		String[] b = t.split(" ");
		int sz = a.length - b.length;
		
		List<String> missing = new ArrayList<>();
		Set<String> set = new HashSet<>(Arrays.asList(b));
		
		System.out.println(set);
		
		for(String w : a) {
			if(set.contains(w)) continue;
			missing.add(w);
			
		}
		
		System.out.println(missing);
		return missing;

	}
	
	public static List<String> missingwords2(String s, String t) {

		String[] a = s.split(" ");
		String[] b = t.split(" ");
		int sz = a.length - b.length;
		
		//String[] missing = new String[sz];
		List<String> missing = new ArrayList<>();
		
		int c = 0;
		for (int i = 0; i < a.length; i++) {
			int flag = 0;
			for (int j = 0; j < b.length; j++) {
				if (a[i].equals(b[j]))
					flag = 1;
			}
			if (flag == 0) {
				missing.add(a[i]);

			}
		}
		
		System.out.println(missing);
		return missing;

	}
	
	// --https://gist.github.com/xixlolz/0ed8816e468315470189342028be15be
	public static String[] missingwords(String t, String s) {

		String[] a = t.split(" ");
		String[] b = s.split(" ");
		int sz = a.length - b.length;
		String[] missing = new String[sz];
		int c = 0;
		for (int i = 0; i < a.length; i++) {
			int flag = 0;
			for (int j = 0; j < b.length; j++) {
				if (a[i].equals(b[j]))
					flag = 1;
			}
			if (flag == 0) {
				missing[c++] = a[i];

			}
		}
		return missing;

	}

}
