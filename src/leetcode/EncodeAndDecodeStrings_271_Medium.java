package leetcode;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EncodeAndDecodeStrings_271_Medium {

	public static void main(String[] args) {

	}
	
	/*
	 * With escaping : https://leetcode.com/problems/encode-and-decode-strings/discuss/70402/Java-with-%22escaping%22
	 * 
	 * Double any hashes inside the strings, then use standalone hashes (surrounded by spaces) 
	 * to mark string endings. 
	 * 
	 * For example:

		{"abc", "def"}    =>  "abc # def # "
		{'abc', '#def'}   =>  "abc # ##def # "
		{'abc##', 'def'}  =>  "abc#### # def # "
	 * */
	
	private static class Codec_WithEspcaping {
		public String encode(List<String> strs) {
		    StringBuffer out = new StringBuffer();
		    for (String s : strs)
		        out.append(s.replace("#", "##")).append(" # ");
		    return out.toString();
		}

		// For decoding, just do the reverse: First split at standalone hashes, then undo the doubling in each string.
		public List<String> decode(String s) {
		    List<String> strs = new ArrayList<>();
		    
		    /* here -1 is limit in split
		     * limit < 0 : In this case, the pattern will be applied as many times as possible, 
		     * and the resulting array can be of any size.
		     */
		    String[] array = s.split(" # ", -1);
		    
		    for (int i=0; i<array.length-1; ++i)
		        strs.add(array[i].replace("##", "#"));
		    
		    return strs;
		}
		
		// with Java stream
		public String encode_withStream(List<String> strs) {
		    return strs.stream()
		               .map(s -> s.replace("#", "##") + " # ")
		               .collect(Collectors.joining());
		}

		public List<String> decode_withStream(String s) {
		    List strs = Stream.of(s.split(" # ", -1))
		                      .map(t -> t.replace("##", "#"))
		                      .collect(Collectors.toList());
		    strs.remove(strs.size() - 1);
		    return strs;
		}
		
	} 
	
	
	
	
	

	/* For Chunk Encoding 
    https://leetcode.com/problems/encode-and-decode-strings/discuss/111642/Fast-Java-Solution-Beats-97-With-Detailed-Explanation
    
    Time complexity : O(N) both for encode and decode, where N is a number of strings in the input array.

    Space complexity : O(1) for encode to keep the output, since the output is one string. 
                    O(N) for decode keep the output, since the output is an array of strings.
    */
	private static class Codec_ChunkEncoding {

	    // Encodes a list of strings to a single string.
	    public String encode(List<String> strs) {
	        StringBuilder sb = new StringBuilder();
	        for (String s : strs) {
	            byte[] arr = intToByteArray(s.length());
	            sb.append((char)arr[0]).append((char)arr[1]).append((char)arr[2]).append((char)arr[3]).append(s);
	        }
	        return sb.toString();
	    }
	    
	    
	    private byte[] intToByteArray(int i) {
	        return new byte[] {
	            (byte)(i>>24),
	            (byte)(i>>16),
	            (byte)(i>>8),
	            (byte)(i)
	        };
	    }
	    
	    private int byteArrayToInt(byte[] data) {
	        // 0xff is hex decimal equivalent of 0b11111111, this is used to ensure all other bits are 0
	        return (data[0]<<24) & 0xff000000 |
	               (data[1]<<16) & 0x00ff0000 |
	               (data[2]<< 8) & 0x0000ff00 |
	               (data[3]<< 0) & 0x000000ff;
	    }

	    // Decodes a single string to a list of strings.
	    public List<String> decode(String s) {
	        List<String> res = new ArrayList<>();
	        int i = 0;
	        while (i < s.length()) {
	            int length = byteArrayToInt(new byte[]{(byte)s.charAt(i++), (byte)s.charAt(i++), (byte)s.charAt(i++), (byte)s.charAt(i++)});
	            res.add(s.substring(i, i+length));
	            i+=length;
	        }
	        return res;
	    }
	}
	
	// Below taken from LeetCode Solutions
	
	public class Codec {
		  // Encodes string length to bytes string
		  public String intToString(String s) {
		    int x = s.length();
		    char[] bytes = new char[4];
		    for(int i = 3; i > -1; --i) {
		      bytes[3 - i] = (char) (x >> (i * 8) & 0xff);
		    }
		    return new String(bytes);
		  }

		  // Encodes a list of strings to a single string.
		  public String encode(List<String> strs) {
		    StringBuilder sb = new StringBuilder();
		    for(String s: strs) {
		      sb.append(intToString(s));
		      sb.append(s);
		    }
		    return sb.toString();
		  }

		  // Decodes bytes string to integer
		  public int stringToInt(String bytesStr) {
		    int result = 0;
		    for(char b : bytesStr.toCharArray())
		      result = (result << 8) + (int)b;
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


/**

Understanding bytes = [chr(x >> (i * 8) & 0xff) for i in range(4)]

1. all you need to do is encode the length of x into 4 bytes ( why 4 bytes - integer size - 4 bytes = [8bits, 8bits, 8bits, 8bits])

2. ok so how do you get a X(length of str) total size into chunks of 8 bits ?

	2.1 >> is right shift - which means if you have 101111 >> 2 - this right shift moves 101111, 2 times to the right - which
	becomes 1011
	2.2 if you go to python terminal and type 0xff you get 8 1's which represents 11111111 = a BYTE
	2.3 if you AND(&) any number with 0xff = it gives you the right most 8 bits of the number
	
	example: 
		1. bin(291) (binary of 291) is '0b100100011'
		2. oxff binary is '0b11111111'
		3. now if you 291 & 0xff = you get last 8 bits of 291 == 00100011
	
	If you understand this - you understood the solution.

3. Now as explained in the 2 point. The python solution line 6 we take the whole length of the string (len(str)) - we have to
encode that into [8bits, 8 bits, 8bits, 8bits]
example: 1. lets say our total length is 291 ( in binary its bin(291) = '0b100100011')
2. what you have to do now ? we grab the right most 8 bits - how do you grab right most 8 bits ?
2.1 as mentioned above you do 291 & 0xff = you get last 8 bits

Now you already have right most 8 bits - in next iteration you are interested in NEXT 8 bits - how do you get
that ? we move 291 >> 8 ( which removes the last 8 bits we already computed) - which means if you do
(291 >> 8 ) & 0xff = it gives you the next right most 8 bits

4. as already mentioned we need 4 chunks of 8 bits [8bits, 8bits, 8bits, 8bits]
	4.1 [ for i in range(4)] thats why the range is 4 ( 0, 1, 2, 3)

	Once you compute all the 8bits we need to convert to char hence its [chr((x >> (i * 8)) & 0xff) 
	for i in range(4)]


Taken from: https://leetcode.com/problems/encode-and-decode-strings/solution/462088

*/