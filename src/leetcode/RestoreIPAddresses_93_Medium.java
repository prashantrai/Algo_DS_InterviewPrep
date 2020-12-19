package leetcode;

import java.util.ArrayList;
import java.util.List;

public class RestoreIPAddresses_93_Medium {

	public static void main(String[] args) {

		System.out.println(restoreIpAddresses("25525511135"));
		System.out.println(restoreIpAddresses("0000"));
		System.out.println(restoreIpAddresses("1111"));
		System.out.println(restoreIpAddresses("010010"));
		System.out.println(restoreIpAddresses("101023"));
		
		System.out.println("DFS:: "+restoreIpAddresses_DFS("25525511135"));
		System.out.println("DFS:: "+restoreIpAddresses_DFS("0000"));
		System.out.println("DFS:: "+restoreIpAddresses_DFS("1111"));
		System.out.println("DFS:: "+restoreIpAddresses_DFS("010010"));
		System.out.println("DFS:: "+restoreIpAddresses_DFS("101023"));
	}
	
	// DFS and Back Tracking solution :: https://blog.csdn.net/mine_song/article/details/70210397
	
	// https://leetcode.com/problems/restore-ip-addresses/submissions/
	// https://leetcode.com/problems/restore-ip-addresses/discuss/30972/WHO-CAN-BEAT-THIS-CODE
    
	// Time O(3^3) i.e. constant 
	// Space O(1)
    public static List<String> restoreIpAddresses(String s) {
        List<String> list = new ArrayList<>();
        int len = s.length();
        StringBuilder ip = new StringBuilder();
        
        for(int i=1; i<=3; i++) {
            for(int j=1; j<=3; j++) {
                for(int k=1; k<=3; k++) {
                    int l = len - i -j - k; // here 'l' is to calculate the rest length i.e. 4th part of the IP address. Pending length should be 1 and less than 3
                    
                    //if (l>0 && l<=3 && i+j+k+l == len) {
                    if (l>0 && l<=3) {
                    
                        int a = Integer.parseInt(s.substring(0, i)); 
                        int b = Integer.parseInt(s.substring(i, i+j));
                        int c = Integer.parseInt(s.substring(i+j, i+j+k));
                        int d = Integer.parseInt(s.substring(i+j+k));
                        
                        if(a <= 255 && b <= 255 && c <= 255 && d <= 255) {
                            ip.append(a).append(".")
                                .append(b).append(".")
                                .append(c).append(".")
                                .append(d);
                            
                            if(ip.length() == len+3) {
                                list.add(ip.toString());
                            }
                            ip.setLength(0); // clear StringBuffer
                        }
                    }
                }
            }
        }
        return list;
    
    }
    
    // DFS - https://blog.csdn.net/mine_song/article/details/70210397
    // DFS and Back Tracking solution :: https://blog.csdn.net/mine_song/article/details/70210397
    public static List<String> restoreIpAddresses_DFS (String s) {
        List<String> result = new ArrayList<>();
        if(s.length() < 4 || s.length() > 12) // invalid
            return result;
        
        dfs(result, s, "", 1);
        
        return result;
    }
    
    private static void dfs (List<String> result, String s, String tmp, int count) {
        
        if(count == 4 && isValid(s)) {
            result.add(tmp + s);
            return;
        }
        
        for(int i=1; i<Math.min(4, s.length()); i++) {
            String curr = s.substring(0, i);
            if(isValid(curr)) {
                dfs(result, s.substring(i), tmp+curr+".", count+1);
            }
        }
    }
    
    private static boolean isValid(String s) {
        if(s.charAt(0) == '0')  {
            return s.equals("0"); // if entire string is ZERO e.g. "0"
        }
        
        int num = Integer.parseInt(s);
        return num > 0 && num < 256;
    }
    

}
