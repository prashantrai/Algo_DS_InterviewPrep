package Oracle;

import java.util.HashSet;
import java.util.Set;

public class LongestDuplicateSubstring_1044_Hard {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	/* Reference: https://leetcode.com/problems/longest-duplicate-substring/discuss/1132925/JAVA-Solution
	 * Complexity: Time:O(NlogN) and Space:O(n)
	 * 
	 * Another: https://leetcode.com/problems/longest-duplicate-substring/discuss/695419/JAVA-%3A-O(n-log-n)-Rabin-Karp-%2B-Binary-Search
	 * */
	
	public String longestDupSubstring(String s) {
        int left=1;
        int right=s.length()-1;
        
        String result="";
        while(left<=right){
            int mid=left + (right-left)/2;
             
            String str=rabinKarp(s,mid);
            if(str.length()!=0){
                result=str;
                left=mid+1;
            }else{
                right=mid-1;
            }
        }
        return result;
    }
    
    private String rabinKarp(String s,int len){
        Set<Long> set=new HashSet<>();
        long h=hash(s.substring(0,len));
        set.add(h);
        long pow=1;
        for(int l=1;l<len;l++) pow*=31;
        
        for(int i=1;i<=s.length()-len;i++){
            h=nextHash(pow,h,s.charAt(i-1),s.charAt(i+len-1));
            if(set.contains(h)){
                return s.substring(i,i+len);
            }
            set.add(h);
        }
        return "";
    }
    
   private long nextHash(long pow,long hash,char left,char right){
       return (hash - left*pow)*31 + right;
       // abcd   bcdf
   }  
    
    private long hash(String s) {
		long hash = 0;
        long mul=1;
		for (int i = s.length()-1; i >=0; i--) {
			hash += s.charAt(i)*mul;
            mul*=31;
		}
		return hash;
	}
}
