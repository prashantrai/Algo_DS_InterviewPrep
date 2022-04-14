package interview;

public class Salesforce {

	public static void main(String[] args) {

		String words = "The world is place Hello to all world hello to fellow folks";
		String w1 = "hello";
		String w2 = "world";
		
		System.out.println("minDistance = "+minDistance(words, w1, w2));
		
		
	}

	//--Find the minimum distance between words
	public static int minDistance(String words, String w1, String w2) {

		// --to remove .
		// String s = new String(words.replaceAll(“.”, “”));
		String[] wArr = words.split(" ");

		return helper(wArr, w1, w2);

	}

	public static int helper(String[] wArr, String w1, String w2) {

		/*
		 * 
		 * //--The world is place Hello to all world hello to fellow folks
		 * 
		 * W1 = hello W2 = world 
		 * i=1, 2, 3, 4, 5, 6, 7, 8, 9, 10 
		 * D2 = 1 , 7 
		 * D1=4, 8 
		 * Min = 3, 3, 1
		 */
		int d1 = -1;
		int d2 = -1;

		int min = Integer.MAX_VALUE;

		for (int i = 0; i < wArr.length; i++) {

			String s = wArr[i];

			if (w1.equalsIgnoreCase(s)) {
				d1 = i;
				if (d2 != -1) {
					min = Math.min(min, d1 - d2);
				}

			} else if (w2.equalsIgnoreCase(s)) {

				d2 = i;
				if (d1 != -1) {
					min = Math.min(min, d2 - d1);
				}
			}
			if (min < 2)
				break;

		} // --for closed

		return min;
	}

}
