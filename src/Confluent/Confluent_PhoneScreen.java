package Confluent;

public class Confluent_PhoneScreen {

	public static void main(String[] args) {

		System.out.println("Expected: True, Actual : " + isMatch("cat", "cat"));
		System.out.println("Expected: True, Actual : " + isMatch("cat", "c*t"));
		System.out.println("Expected: False, Actual : " + isMatch("dog", "c*t"));
		System.out.println("Expected: True, Actual : " + isMatch("abc", "a**b***c"));
		System.out.println("Expected: True, Actual : " + isMatch("", "****"));

		assertEquals(true, isMatch("cat", "c*t"));
		assertEquals(true, isMatch("cat", "*t"));
		assertEquals(false, isMatch("dog", "c*t"));
		assertEquals(true, isMatch("", ""));
		assertEquals(true, isMatch("", "*"));
		assertEquals(true, isMatch("asdfkjasldkfj", "*"));
		assertEquals(true, isMatch("cat", "cat"));
		assertEquals(true, isMatch("catat", "ca*t"));
		assertEquals(false, isMatch("cata", "c*t"));
		assertEquals(false, isMatch("cat", "cat*cat"));
		assertEquals(false, isMatch("catat", "cat*tat"));
		assertEquals(true, isMatch("cattat", "cat*tat"));
		assertEquals(false, isMatch("cart", "c*at"));
		assertEquals(true, isMatch("v", "v*"));
		assertEquals(true, isMatch("v", "*v"));
		assertEquals(false, isMatch("dv", "v*"));
		assertEquals(false, isMatch("vd", "*v"));
		assertEquals(true, isMatch("aa", "a*a"));

		assert (isMatch("", "****") == true);
		assert (isMatch("cat", "c*t*t") == false);
		assert (isMatch("dogcat", "*cat*cat") == false);
		assert (isMatch("catdog", "cat*cat*") == false);
		assert (isMatch("catcatttcat", "cat*cat*cat****") == true);
		assert (isMatch("dogcatcatttcat", "cat*cat*cat****") == false);
		assert (isMatch("catfdafdacaterdfgscat", "cat***cat***cat****") == true);
		assert (isMatch("catcatcat", "cat*cat*cat***") == true);
		assert (isMatch("catcatcat", "****cat*cat*cat***") == true);
		assert (isMatch("fdajhfjdsacatcatcatlsaflk", "****cat*cat*cat***") == true);
		assert (isMatch("abbb", "cat*abc*") == false);
		assert (isMatch("catcattcat", "cat*catcat*") == false);
		assert (isMatch("tttttttttpt", "*ttp*") == true);

		System.out.println("PASSED");
	}

	// pattern: has exactly 0 or 1 '*' -- matches 0 or more any character
	// cat cat → true
	// cat a → false
	// cat c*t → true
	// cat *t → true
	// dog c*t → false

	static boolean isMatch(String s, String p) {

		if (s == null || p == null) {
			return false;
		}

		int sLen = s.length();
		int pLen = p.length();

		int sIdx = 0;
		int pIdx = 0;

		int startIdx = -1;
		int sIdxTmp = -1;

		while (sIdx < sLen) {

			// if the pattern cahr == input character
			if (pIdx < pLen && p.charAt(pIdx) == s.charAt(sIdx)) {
				++sIdx;
				++pIdx;
			}
			// if pattern char is '*'
			else if (pIdx < pLen && p.charAt(pIdx) == '*') {
				startIdx = pIdx;
				sIdxTmp = sIdx;
				++pIdx;
			} else if (startIdx == -1) {
				return false;
			} else { // backtrack
				// * matching one or more
				pIdx = startIdx + 1;
				sIdx = sIdxTmp + 1;
				sIdxTmp = sIdx;
			}
		}

		for (int i = pIdx; i < pLen; i++) {
			if (p.charAt(i) != '*') {
				return false;
			}
		}

		return true;
	}
	
	public static <T> void assertEquals(T expected, T actual) {
		if (expected == null && actual == null || actual != null && actual.equals(expected)) {
			System.out.println("PASSED:: Expected:" + expected + ", Actual: " + actual);
		} else {
			throw new AssertionError("Expected:\n  " + expected + "\nActual:\n  " + actual + "\n");
	    }
	}

}
