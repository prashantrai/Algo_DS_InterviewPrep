package leetcode;

public class FirstBadVersion_278_Easy {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	//https://leetcode.com/problems/first-bad-version/
	
	//--this is just dummy version to avoind compilation error in IDE
	//-- Refer leet code prob for details
	static class VersionControl {
		boolean isBadVersion(int version) {
			return true;
		}
	}
	
	static class Solution extends VersionControl {
	    public int firstBadVersion(int n) {
	        
	        int left = 1;
	        int right = n;
	        
	        while (left < right) {
	            int mid = left + (right-left)/2;
	            
	            if(isBadVersion(mid)) {
	                right = mid;
	            } else {
	                left = mid + 1;
	            }
	        }
	        
	        return left;
	    }
	}

}
