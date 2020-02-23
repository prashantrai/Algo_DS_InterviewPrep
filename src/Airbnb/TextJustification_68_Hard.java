package Airbnb;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class TextJustification_68_Hard {

	public static void main(String[] args) {

		String[] words = {"This", "is", "an", "example", "of", "text", "justification."};
		int maxWidth = 16;
		List<String> res = fullJustify(words, maxWidth); //--working
		System.out.println(res);
		
		res = fullJustify3(words, maxWidth); //--working
		System.out.println(res);
	}
	
	//--https://leetcode.com/problems/text-justification/
	//--https://leetcode.com/problems/text-justification/discuss/489516/Java-short-easy-to-understand.
	//--https://www.jianshu.com/p/8961b9046d80
	//--https://www.programcreek.com/2014/05/leetcode-text-justification-java/
	
	
	
	/* 1. iterate through the words and add to queue and keep track of length of words iterated 
	 * 2. if the total length + (queue_size - 1) >= maxWidth
	 * 		a. Calculate avg space i.e. spce/(queue_size - 1) where spce is remaining len i.e. maxWidth - length  
	 * 		b. Poll from queue and ADD the avg space after each word
	 * 3. If it's the last word add space  	
	 */
	
	//--https://leetcode.com/problems/text-justification/submissions/
	public static List<String> fullJustify3(String[] words, int maxWidth) {
		
		List<String> res = new ArrayList<>();
		if(words.length == 0 || maxWidth == 0) {
			return res;
		}
		
		int idx = 0; 
		int len = 0; //--track the length of current statement and on every new line reset this
		Queue<String> q = new LinkedList<>();

		while (idx < words.length) {
			int currentLen = len + words[idx].length() + q.size()-1; 
			if( currentLen < maxWidth) {
				q.offer(words[idx]);
				len += words[idx].length();
				idx++;
			} else {
				String s = createJustifiedLine(q, len, idx == words.length, maxWidth);
				res.add(s);
				len = 0; //reset len for new line
			}
		}
		String s = createJustifiedLine(q, len, true, maxWidth);
		res.add(s);
		
		return res;
	}
	
	
	
	private static String createJustifiedLine(Queue<String> q, int len, boolean isLast, int maxWidth) {
		StringBuilder buff = new StringBuilder();
		
		int remainingSpace =  maxWidth - len; //--remaining place to add space in the string/statement
		
		while(!q.isEmpty()) {
			
			//calculate avg space
			int avgSpace  = 0;
			if(isLast) {
				avgSpace = 1;
			} else if (q.size() > 1) {
				avgSpace = (int) Math.ceil( remainingSpace/(float)(q.size()-1) ); //round to the ceiling
			} else {
				avgSpace = remainingSpace;
			}
			
			//--add calculated avg space to words and generate string
			buff.append(q.poll());
			if(q.size() > 0) { //--not the last word in q or of current statement
				addSpace(buff, avgSpace);
				remainingSpace -= avgSpace; 
			} else {
				addSpace(buff, remainingSpace);
			}
		}
		
		return buff.toString();
	}
	
	public static void addSpace(StringBuilder buff, int spaceCount) {
		for(int i=0; i<spaceCount; i++) {
			buff.append(" ");
		}
	}


	//--Working 
	//--https://leetcode.com/problems/text-justification/discuss/489516/Java-short-easy-to-understand.
	public static List<String> fullJustify(String[] words, int maxWidth) {
        List<String> ans = new ArrayList<>();
        if (words.length == 0 || maxWidth == 0) {
            return ans;
        }
        int idx = 0;
        int len = 0;
        Queue<String> q = new LinkedList<>();
        
        while (idx < words.length) {
            if (len + words[idx].length() + q.size() - 1 < maxWidth) {
                q.offer(words[idx]);
                len += words[idx].length();
                idx++;
            } else {
                ans.add(createLine(q, len, maxWidth, idx == words.length));
                len = 0;
            }
        }
        ans.add(createLine(q, len, maxWidth, true));
        return ans;
    }
    
    public static String createLine(Queue<String> q, int len, int maxWidth, boolean isLast) {
        StringBuilder buf = new StringBuilder();
        int space = maxWidth - len;

        while (!q.isEmpty()) {
            int avgSpace = isLast ? 1 : (q.size() > 1 ? (int)Math.ceil(space / (float)(q.size() - 1)) : space);
            buf.append(q.poll());
            if (q.size() > 0) {
                appendSpace(buf, Math.min(avgSpace, space));
                space -= avgSpace;
            } else {
                appendSpace(buf, space);
            }
        }
        
        return buf.toString();
    }
    
    private static void appendSpace(StringBuilder buf, int count) {
        for (int i=0; i<count; i++) buf.append(" ");
    }
    
    

	public static List<String> fullJustify_2(String[] words, int maxWidth) {
	    List<String> res = new ArrayList<>();
	    if (words == null || words.length == 0) {
	        return res;
	    }

	    for (int index = 0; index < words.length; ) {
	        StringBuilder buff = new StringBuilder();
	        int count = words[index].length();
	        int last = index + 1;
	        while (last < words.length && count + 1 + words[last].length() <= maxWidth) {
	            count += 1 + words[last].length();
	            last++;
	        }

	        int diff = last - index - 1;
	        if (last == words.length || diff == 0) {
	            for (int i = index; i < last; i++) {
	                buff.append(words[i] + ' ');
	            }
	            buff.deleteCharAt(buff.length() - 1);
	            for (int i = buff.length() + 1; i <= maxWidth; i++) {
	                buff.append(' ');
	            }
	        } else {
	            int spaces = (maxWidth - count) / diff;
	            int left = (maxWidth - count) % diff;
	            for (int i = index; i < last; i++) {
	                buff.append(words[i]);
	                if (i < last - 1) {
	                    for (int j = 0; j <= spaces + (i - index < left ? 1 : 0); j++) {
	                        buff.append(' ');
	                    }
	                }
	            }
	        }

	        res.add(buff.toString());
	        index = last;
	    }

	    return res;
	}
}
