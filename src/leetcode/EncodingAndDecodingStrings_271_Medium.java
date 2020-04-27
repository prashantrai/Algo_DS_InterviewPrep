package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EncodingAndDecodingStrings_271_Medium {

	// --Copied Solution from Premium Article

	/*
	 * Approach 1:
	 * 
	 * Complexity Analysis
	 * 
	 * Time complexity : O(N) both for encode and decode, where N is a number of
	 * strings in the input array.
	 * 
	 * Space complexity : O(1) for encode to keep the output, since the output is
	 * one string. O(N) for decode keep the output, since the output is an array of
	 * strings.
	 * 
	 */
	public class Codec_1 {
		// Encodes a list of strings to a single string.
		public String encode(List<String> strs) {
			if (strs.size() == 0)
				return Character.toString((char) 258);

			String d = Character.toString((char) 257);
			StringBuilder sb = new StringBuilder();
			for (String s : strs) {
				sb.append(s);
				sb.append(d);
			}
			sb.deleteCharAt(sb.length() - 1);
			return sb.toString();
		}

		// Decodes a single string to a list of strings.
		public List<String> decode(String s) {
			String d = Character.toString((char) 258);
			if (s.equals(d))
				return new ArrayList();

			d = Character.toString((char) 257);
			return Arrays.asList(s.split(d, -1));
		}
	}

	/*
	 * Aproach 2
	 * 
	 * Complexity Analysis
	 * 
	 * Time complexity : O(N) both for encode and decode, where N is a
	 * number of strings in the input array.
	 * 
	 * Space complexity : O(1) for encode to keep the output, since
	 * the output is one string. O(N) for decode keep the output,
	 * since the output is an array of strings.
	 */

	public class Codec_2 {
		// Encodes string length to bytes string
		public String intToString(String s) {
			int x = s.length();
			char[] bytes = new char[4];
			for (int i = 3; i > -1; --i) {
				bytes[3 - i] = (char) (x >> (i * 8) & 0xff);
			}
			return new String(bytes);
		}

		// Encodes a list of strings to a single string.
		public String encode(List<String> strs) {
			StringBuilder sb = new StringBuilder();
			for (String s : strs) {
				sb.append(intToString(s));
				sb.append(s);
			}
			return sb.toString();
		}

		// Decodes bytes string to integer
		public int stringToInt(String bytesStr) {
			int result = 0;
			for (char b : bytesStr.toCharArray())
				result = (result << 8) + (int) b;
			return result;
		}

		// Decodes a single string to a list of strings.
		public List<String> decode(String s) {
			int i = 0, n = s.length();
			List<String> output = new ArrayList();
			while (i < n) {
				int length = stringToInt(s.substring(i, i + 4));
				i += 4;
				output.add(s.substring(i, i + length));
				i += length;
			}
			return output;
		}
	}
}
